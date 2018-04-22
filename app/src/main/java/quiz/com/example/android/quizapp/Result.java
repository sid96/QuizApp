package quiz.com.example.android.quizapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Result extends AppCompatActivity implements View.OnClickListener {

    private TextView attempted, correct, incorrect, skipped, score;
    private int attempts, right, total;
    private Button returncat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);
        attempted = findViewById(R.id.attempted);
        correct = findViewById(R.id.correct);
        incorrect = findViewById(R.id.incorrect);
        skipped = findViewById(R.id.skipped);
        score = findViewById(R.id.score);
        returncat = findViewById(R.id.returntocategories);
        returncat.setOnClickListener(this);
        Bundle b = getIntent().getExtras();
        total = b.getInt("total");
        attempts = b.getInt("attempted");
        right = b.getInt("correct");
        attempted.setText(Integer.toString(attempts));
        correct.setText(Integer.toString(right));
        incorrect.setText(Integer.toString(attempts - right));
        skipped.setText(Integer.toString(total - attempts));
        score.setText("Your Score is : " + right * 10);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.returntocategories:
                finish();
                break;
        }
    }
}
