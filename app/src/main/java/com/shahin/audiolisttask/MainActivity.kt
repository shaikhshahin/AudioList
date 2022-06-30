package com.shahin.audiolisttask

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    private val PICK_AUDIO = 1
    var AudioUri: Uri? = null
    var select_Audio: TextView? = null

    lateinit var rv: RecyclerView
    lateinit var adapter: AudioAdapter
    val list = ArrayList<String>()//Creating an empty arraylist

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        select_Audio = findViewById(R.id.Number) as TextView
        rv = findViewById(R.id.rv)


        val chooseButton: Button = findViewById(R.id.ChooseButton) as Button
        rv.layoutManager = LinearLayoutManager(this)


        // operations to be performed
        // when user tap on the button
        chooseButton?.setOnClickListener()
        {
            val audio = Intent()
            audio.type = "audio/*"
            audio.action = Intent.ACTION_OPEN_DOCUMENT
            startActivityForResult(Intent.createChooser(audio, "Select Audio"), PICK_AUDIO)

            // displaying a toast message
//         Toast.makeText(this@MainActivity,audio.toString(), Toast.LENGTH_LONG).show()
        }

    }
    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_AUDIO && resultCode == RESULT_OK) {
            // Audio is Picked in format of URI
            AudioUri = data?.data
//            select_Audio!!.text = "Audio Selected"
            list.add(AudioModel(AudioUri.toString()).toString())

            adapter = AudioAdapter(this, list)
    rv.adapter = adapter


        }
        else{
            list.clear()

        }
    }



}