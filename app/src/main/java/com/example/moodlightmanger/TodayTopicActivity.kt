package com.example.moodlightmanger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class TodayTopicActivity : AppCompatActivity() {
    private lateinit var db : FirebaseFirestore
    var todayCount : ArrayList<Int> = ArrayList()
    var todayQuestion : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today_topic)

        db = FirebaseFirestore.getInstance()
        val editText = findViewById<EditText>(R.id.editText)

        loadTodayInformation()

        findViewById<Button>(R.id.button).setOnClickListener {
            db.collection("post").document("information").update("todayQuestion", editText.text.toString()
            , "todayHappyCount", 0
            , "todayMadCount", 0
            , "todaySadCount", 0, "todayPostNumber", 0
            , "lastQuestion", todayQuestion
            , "lastHappyCount", todayCount[0]
            , "lastMadCount", todayCount[1]
            , "lastSadCount", todayCount[2]
            , "lastPostNumber", todayCount[3])
                .addOnCompleteListener {
                    Toast.makeText(this, "오늘의 주제와 다른 정보들이 업데이트 되었습니다.", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun loadTodayInformation() {
        db.collection("post").document("information").get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    todayQuestion = it.result?.get("todayQuestion").toString()
                    todayCount.add(it.result?.get("todayHappyCount").toString().toInt())
                    todayCount.add(it.result?.get("todayMadCount").toString().toInt())
                    todayCount.add(it.result?.get("todaySadCount").toString().toInt())
                    todayCount.add(it.result?.get("todayPostNumber").toString().toInt())
                }
            }
    }
}