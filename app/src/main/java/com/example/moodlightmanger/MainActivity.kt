package com.example.moodlightmanger

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var db:FirebaseFirestore
    private lateinit var pw : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = FirebaseFirestore.getInstance()
        loadpw()
        findViewById<Button>(R.id.button).setOnClickListener {
            if(findViewById<EditText>(R.id.editText).text.toString() == pw){
                startActivity(Intent(this, TodayTopicActivity::class.java))

            }
            else{
                Toast.makeText(this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadpw() {
        db.collection("users").document("admin").get()
            .addOnCompleteListener {
                pw = it.result!!.get("adminKey").toString()
                Log.d(TAG, "loadpw: $pw")
            }
    }

}