package quiz.com.example.android.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import quiz.com.example.android.quizapp.Data.CustomCategoryOpenDBHelper;
import quiz.com.example.android.quizapp.Data.Questions;

public class CustomQuiz extends AppCompatActivity implements View.OnClickListener {

    private TextView score, questionnum, questionTextview, countdown;
    private RadioGroup options;
    private RadioButton option1, option2, option3, option4;
    private Button confirm, skip;
    private ArrayList<Questions> questions = new ArrayList<>();
    private int totalquestions, questioncounter, points, correct, attempted;
    private Questions currentQuestion;
    private long backtime;
    private CountDownTimer countDownTimer;
    // private boolean answered;

    @Override
    public void onBackPressed() {
        if ((backtime + 2000) > System.currentTimeMillis()) {
            endquiz();
        } else {
            Toast.makeText(this, "Press Back again to finish quiz now", Toast.LENGTH_SHORT).show();
        }
        backtime = System.currentTimeMillis();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.computerquestions_activity);
        score = findViewById(R.id.text_view_score);
        Bundle b = getIntent().getExtras();
        questionnum = findViewById(R.id.text_view_question_count);
        questionTextview = findViewById(R.id.text_view_question);
        countdown = findViewById(R.id.text_view_countdown);
        options = findViewById(R.id.radio_group);
        option1 = findViewById(R.id.radio_button1);
        option2 = findViewById(R.id.radio_button2);
        option3 = findViewById(R.id.radio_button3);
        option4 = findViewById(R.id.radio_button4);
        confirm = findViewById(R.id.button_confirm_next);
        skip = findViewById(R.id.button_skip);
        confirm.setOnClickListener(this);
        skip.setOnClickListener(this);
        CustomCategoryOpenDBHelper dbOpenHelperer = new CustomCategoryOpenDBHelper(this);
        questions = dbOpenHelperer.getCustomQuestions(b.getString("table_name"));
        totalquestions = questions.size();
        Collections.shuffle(questions);
        nextQuestion();
        countDownTimer = new CountDownTimer(totalquestions * 3000, 1000) {
            public void onTick(long millisUntilFinished) {
                countdown.setText("Time: " + Integer.toString((int) (millisUntilFinished / 1000)));
            }

            public void onFinish() {
                endquiz();
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

    }

    public void nextQuestion() {
        options.clearCheck();
        if (questioncounter < totalquestions) {
            currentQuestion = questions.get(questioncounter);
            questionTextview.setText(currentQuestion.getQuestion());
            option1.setText(currentQuestion.getOption1());
            option2.setText(currentQuestion.getOption2());
            option3.setText(currentQuestion.getOption3());
            option4.setText(currentQuestion.getOption4());
            questioncounter++;
            questionnum.setText("Question : " + questioncounter + "/" + totalquestions);
            confirm.setText("Confirm");
        } else {
            endquiz();
        }
    }

    public void endquiz() {
        if (totalquestions == 0) {
            finish();
            Toast.makeText(this, "Add Questions to play", Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent(CustomQuiz.this, Result.class);
            i.putExtra("total", totalquestions);
            i.putExtra("attempted", attempted);
            i.putExtra("correct", correct);
            finish();
            startActivity(i);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.button_confirm_next:
                if (option1.isChecked() || option2.isChecked() || option3.isChecked() || option4.isChecked()) {
                    attempted++;
                    verifyans();
                } else {
                    Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show();
                    return;
                }
                nextQuestion();
                break;
            case R.id.button_skip:
                nextQuestion();
                break;
        }
    }

    public void verifyans() {
        RadioButton r = findViewById(options.getCheckedRadioButtonId());
        int ans = options.indexOfChild(r) + 1;
        if (ans == currentQuestion.getAnswer()) {
            points += 10;
            correct++;
            score.setText("Score :" + points);
            Toast.makeText(this, "That's a Correct Answer", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "That's a Wrong Answer", Toast.LENGTH_SHORT).show();
        }
    }
}
