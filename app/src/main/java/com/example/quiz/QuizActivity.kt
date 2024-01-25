package com.example.quiz

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.quiz.QuizActivity.Companion.qstnmodellist
import com.example.quiz.databinding.ActivityQuizBinding
import com.example.quiz.databinding.QuizRecyclerBinding
import com.example.quiz.databinding.ScoredialogBinding

class QuizActivity : AppCompatActivity(),View.OnClickListener {

    companion object{
        var qstnmodellist : List<QuestionModel> = listOf()
        var time : String = ""
    }

    lateinit var binding: ActivityQuizBinding
    var curqstnindex=0;
    var ansselect=""
    var score=0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            btn0.setOnClickListener(this@QuizActivity)
            btn1.setOnClickListener(this@QuizActivity)
            btn2.setOnClickListener(this@QuizActivity)
            btn3.setOnClickListener(this@QuizActivity)
            nxtBtn.setOnClickListener(this@QuizActivity)
        }
        loadqstns()
        starttimer()
    }

    private fun starttimer(){
        val totaltime = time.toInt() * 60 * 1000L
        object : CountDownTimer(totaltime,1000L){
            override fun onTick(millisUntilFinished: Long) {
                val sec = millisUntilFinished/1000
                val mins = sec/60
                val remsec = sec % 60
                binding.timerIndicator.text=String.format("%02d:%02d",mins,remsec)

            }
            override fun onFinish() {

            }

        }.start()
    }


        private fun loadqstns(){
            ansselect=""

            if(curqstnindex== qstnmodellist.size){
                completequiz()
                return
            }
            binding.apply {

            qstnIndicator.text="Question ${curqstnindex+1}/ ${qstnmodellist.size} "
                qstnProgressIndicator.progress=
                    (curqstnindex.toFloat() / qstnmodellist.size.toFloat() * 100 ).toInt()
                qstnTv.text = qstnmodellist[curqstnindex].question
                btn0.text= qstnmodellist[curqstnindex].options[0]
                btn1.text= qstnmodellist[curqstnindex].options[1]
                btn2.text= qstnmodellist[curqstnindex].options[2]
                btn3.text= qstnmodellist[curqstnindex].options[3]



        }
    }

    override fun onClick(v: View?) {

        binding.apply {
            btn0.setBackgroundColor(getColor(R.color.grey))
            btn1.setBackgroundColor(getColor(R.color.grey))
            btn2.setBackgroundColor(getColor(R.color.grey))
            btn3.setBackgroundColor(getColor(R.color.grey))
        }

        val clickedbtn = v as Button
        if(clickedbtn.id==R.id.nxt_btn){

            if(ansselect.isEmpty()){
                Toast.makeText(applicationContext,"Select an Option to Continue",Toast.LENGTH_SHORT).show()
                return;
            }
            if(ansselect== qstnmodellist[curqstnindex].correct){
                score++
                Log.i("Score of Quiz",score.toString())
            }
            curqstnindex++
            loadqstns()
        }
        else{
            ansselect=clickedbtn.text.toString()
            clickedbtn.setBackgroundColor(getColor(R.color.white))
        }
    }

    private fun completequiz(){
        val totalqstns = qstnmodellist.size
        val percentage =((score.toFloat()/totalqstns.toFloat())*100).toInt()

        val dialogbind=ScoredialogBinding.inflate(layoutInflater)
        dialogbind.apply {
            scoreprogress.progress=percentage
            scoretext.text="$percentage %"
            if(percentage>60){
                scorests.text="Congrats! You have Passed"
                scorests.setTextColor(Color.parseColor("#FFFFFF"))

            }
            else{
                scorests.text="OOPs! You have Failed"
                scorests.setTextColor(Color.RED)
            }
            scoresubtitle.text = Html.fromHtml("<b>$score</b> out of <b>$totalqstns</b> are Correct", Html.FROM_HTML_MODE_LEGACY)
            scoresubtitle.setTypeface(null, Typeface.BOLD)

            finishbtn.setOnClickListener {
                finish()
            }

        }


        AlertDialog.Builder(this)
            .setView(dialogbind.root)
            .setCancelable(false)
            .show()
    }
}