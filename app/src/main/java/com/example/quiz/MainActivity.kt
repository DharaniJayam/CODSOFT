package com.example.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quiz.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var quizlist: MutableList<QuizModel>
    lateinit var adapter: QuizListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        quizlist= mutableListOf()
        getdatafirebase()
    }

    private fun setrecyclerview(){
        binding.progressbar.visibility= View.GONE
        adapter= QuizListAdapter(quizlist)
        binding.recyclerView.layoutManager=LinearLayoutManager(this)
        binding.recyclerView.adapter=adapter

    }


    private fun getdatafirebase() {
        binding.progressbar.visibility= View.VISIBLE
        FirebaseDatabase.getInstance().reference
            .get()
            .addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot.exists()) {
                    for (snapshot in dataSnapshot.children) {
                        val quizmodel = snapshot.getValue(QuizModel::class.java)
                        if (quizmodel != null) {
                            quizlist.add(quizmodel)
                        }

                    }
                }
                setrecyclerview()
            }
    }

}