package quiz.com.example.android.quizapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import quiz.com.example.android.quizapp.Data.CustomCategoryContract;
import quiz.com.example.android.quizapp.Data.CustomCategoryOpenDBHelper;
import quiz.com.example.android.quizapp.Data.Questions;

public class AddQuestion extends AppCompatActivity implements View.OnClickListener {
    private Button add;
    private EditText addques, addoption1, addoption2, addoption3, addoption4, addanswer;
    private String table;
    private SQLiteDatabase database;
    private CustomCategoryOpenDBHelper dbOpenHelperer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addquestion_activity);
        Bundle b = getIntent().getExtras();
        table = b.getString("table_name");
        add = findViewById(R.id.button_add);
        add.setOnClickListener(this);
        addques = findViewById(R.id.edit_question);
        addoption1 = findViewById(R.id.edit_button1);
        addoption2 = findViewById(R.id.edit_button2);
        addoption3 = findViewById(R.id.edit_button3);
        addoption4 = findViewById(R.id.edit_button4);
        addanswer = findViewById(R.id.edit_answer);
         dbOpenHelperer = new CustomCategoryOpenDBHelper(this);
        database = dbOpenHelperer.getReadableDatabase();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add:
                Questions q = new Questions();
                String ques = addques.getText().toString().trim();
                q.setQuestion(ques);
                String op1 = addoption1.getText().toString().trim();
                q.setOption1(op1);
                String op2 = addoption2.getText().toString().trim();
                q.setOption2(op2);
                String op3 = addoption3.getText().toString().trim();
                q.setOption3(op3);
                String op4 = addoption4.getText().toString().trim();
                q.setOption4(op4);
                String ansnum = addanswer.getText().toString();
                if (ques.compareTo("") == 0 || op1.compareTo("") == 0 || op2.compareTo("") == 0 ||
                        op3.compareTo("") == 0 || op4.compareTo("") == 0 || ansnum.compareTo("") == 0) {
                    Toast.makeText(this, "Some Fields are Empty", Toast.LENGTH_SHORT).show();
                } else if (ansnum.compareTo("1") == 0 || ansnum.compareTo("2") == 0 || ansnum.compareTo("3") == 0 ||
                        ansnum.compareTo("4") == 0) {
                    q.setAnswer(Integer.parseInt(addanswer.getText().toString().trim()));
                    addQuestion(q);
                    Toast.makeText(this, "Question Added Successfully", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "Enter a Valid Question Number", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    void addQuestion(Questions q) {
        ContentValues cv = new ContentValues();
        cv.put(CustomCategoryContract.CustomCategory.COLUMN_QUESTION, q.getQuestion());
        cv.put(CustomCategoryContract.CustomCategory.COLUMN_OPTION1, q.getOption1());
        cv.put(CustomCategoryContract.CustomCategory.COLUMN_OPTION2, q.getOption2());
        cv.put(CustomCategoryContract.CustomCategory.COLUMN_OPTION3, q.getOption3());
        cv.put(CustomCategoryContract.CustomCategory.COLUMN_OPTION4, q.getOption4());
        cv.put(CustomCategoryContract.CustomCategory.COLUMN_ANSWER, q.getAnswer());
        database.insert(table, null, cv);
        finish();
    }
}
