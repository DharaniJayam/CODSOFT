package com.example.quiz

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz.databinding.QuizRecyclerBinding

class QuizListAdapter(private val quizmodellist : List<QuizModel>) :
    RecyclerView.Adapter<QuizListAdapter.myviewholder>() {
    class myviewholder(private val binding: QuizRecyclerBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(model: QuizModel){
            binding.apply {
                quiztitle.text=model.title
                quizsubtitle.text=model.subtitle
                quiztime.text=model.time + "min"
                root.setOnClickListener{
                    val intent = Intent(root.context,QuizActivity::class.java)
                    QuizActivity.qstnmodellist = model.questionlist
                    QuizActivity.time=model.time
                    root.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuizListAdapter.myviewholder {
      val binding = QuizRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return myviewholder(binding)
    }

    override fun onBindViewHolder(holder: QuizListAdapter.myviewholder, position: Int) {
        return holder.bind(quizmodellist[position])
    }

    override fun getItemCount(): Int {
        return quizmodellist.size
    }
}