package com.shahin.audiolisttask

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception
import android.media.AudioAttributes
import android.media.MediaPlayer.OnPreparedListener
import android.net.Uri
import android.os.Handler



class AudioAdapter(val requireContext: Context, val audioList:  ArrayList<String>) :
    RecyclerView.Adapter<AudioAdapter.ViewHolder>() {
    lateinit var mediaPlayer: MediaPlayer
    private lateinit var runnable:Runnable
    private var handler: Handler = Handler()
    private var pause:Boolean = false
     var  tv_pass: TextView ? = null
    var seek_bar: SeekBar ? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById(R.id.tvTitle) as TextView
        val btnplay = itemView.findViewById(R.id.btnPlay) as TextView
        val icon = itemView.findViewById(R.id.icon) as ImageView
        val seek_bar = itemView.findViewById(R.id.seek_bar) as SeekBar
        val tv_pass = itemView.findViewById(R.id.tv_pass) as TextView
        val tv_due = itemView.findViewById(R.id.tv_due) as TextView
        val playBtn = itemView.findViewById(R.id.playBtn) as Button

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(requireContext).inflate(R.layout.audio_layout, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        initializeSeekBar(holder)
        val audio = audioList[position]
        val no = position+1
        holder.title.setText("Audio " + no)

//        holder.playBtn.setOnClickListener {
//            if (pause) {
//                mediaPlayer.seekTo(mediaPlayer.currentPosition)
//                mediaPlayer.start()
//                pause = false
////                Toast.makeText(this, "media playing", Toast.LENGTH_SHORT).show()
//            } else {
//
////                mediaPlayer = MediaPlayer.create(this, audio)
//                mediaPlayer.start()
////                Toast.makeText(this, "media playing", Toast.LENGTH_SHORT).show()
//
//            }
//        }
            holder.btnplay.setOnClickListener {
            val audioUrl = audio.toString()
                //"https://songspk.blog/bhool-bulaiyaa-2-songs.html"//audio.toString()
//            val mediaPlayer: MediaPlayer = MediaPlayer.create(requireContext,audioUrl)
//            mediaPlayer.start()

            //
            if (audioUrl != null) {
                if (holder.btnplay.text.equals("Play")) {
                    playAudio(audioUrl)
                    holder.btnplay.text = "Pause"
                    holder.icon.setBackgroundResource(R.drawable.ic_launcher_background)
                } else {
                    mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.release()
                    Toast.makeText(requireContext, "Audio has been paused", Toast.LENGTH_SHORT)
                        .show()
                    holder.btnplay.text = "Play"
                    holder.icon.setBackgroundResource(R.drawable.ic_launcher_foreground)
                }
            }
        }
    }
    override fun getItemCount() = audioList.size
    private fun playAudio(audioUrl: String) {
        mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        )

        try {
            // below line is use to set our url to our media player.
            mediaPlayer.setDataSource(audioUrl)
            // below line is use to prepare
            // and start our media player.
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener(OnPreparedListener { mp ->
                mp.start()
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // below line is use to display a toast message.
        Toast.makeText(requireContext, "Audio started playing..", Toast.LENGTH_SHORT).show()
    }

    // Method to initialize seek bar and audio stats
    private fun initializeSeekBar(holder: ViewHolder) {
        mediaPlayer = MediaPlayer()

        holder. seek_bar.max = mediaPlayer.seconds

        runnable = Runnable {
           holder. seek_bar.progress = mediaPlayer.currentSeconds

            holder.tv_pass.text = "${mediaPlayer.currentSeconds} sec"
            val diff = mediaPlayer.seconds - mediaPlayer.currentSeconds
           holder. tv_due.text = "$diff sec"

            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }

    // Creating an extension property to get the media player time duration in seconds
    val MediaPlayer.seconds:Int
        get() {
            return this.duration / 1000
        }
    // Creating an extension property to get media player current position in seconds
    val MediaPlayer.currentSeconds:Int
        get() {
            return this.currentPosition/1000
        }


}


