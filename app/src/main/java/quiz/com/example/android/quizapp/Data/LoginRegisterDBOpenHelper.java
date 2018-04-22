package quiz.com.example.android.quizapp.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static quiz.com.example.android.quizapp.Data.LoginRegisterContract.Login_Register;
import static quiz.com.example.android.quizapp.Data.LoginRegisterContract.Login_Register.COLUMN_AVATAR;
import static quiz.com.example.android.quizapp.Data.LoginRegisterContract.Login_Register.COLUMN_ID;
import static quiz.com.example.android.quizapp.Data.LoginRegisterContract.Login_Register.COLUMN_NAME;
import static quiz.com.example.android.quizapp.Data.LoginRegisterContract.Login_Register.COLUMN_PASS;
import static quiz.com.example.android.quizapp.Data.LoginRegisterContract.Login_Register.TABLE_NAME;

public class LoginRegisterDBOpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "quiz.db";
    public static final String TEXT_TYPE = " TEXT";
    public static final String COMMA = " ,";
    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA + COLUMN_AVATAR + TEXT_TYPE + " UNIQUE" +
            " NOT NULL" + COMMA + COLUMN_NAME + TEXT_TYPE + COMMA + COLUMN_PASS + TEXT_TYPE + " NOT NULL);";
    public static final String COMPUTER_CREATE_ENTRIES = "CREATE TABLE " + ComputerCategoryContract.Computer.TABLE_NAME + "(" +
            ComputerCategoryContract.Computer.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA +
            ComputerCategoryContract.Computer.COLUMN_QUESTION + TEXT_TYPE + COMMA +
            ComputerCategoryContract.Computer.COLUMN_OPTION1 + TEXT_TYPE + COMMA +
            ComputerCategoryContract.Computer.COLUMN_OPTION2 + TEXT_TYPE + COMMA +
            ComputerCategoryContract.Computer.COLUMN_OPTION3 + TEXT_TYPE + COMMA +
            ComputerCategoryContract.Computer.COLUMN_OPTION4 + TEXT_TYPE + COMMA +
            ComputerCategoryContract.Computer.COLUMN_ANSWER + " INTEGER )";
    public static final String GENERAL_CREATE_ENTRIES = "CREATE TABLE " + GeneralCategoryContract.General.TABLE_NAME + "(" +
            GeneralCategoryContract.General.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA +
            GeneralCategoryContract.General.COLUMN_QUESTION + TEXT_TYPE + COMMA +
            GeneralCategoryContract.General.COLUMN_OPTION1 + TEXT_TYPE + COMMA +
            GeneralCategoryContract.General.COLUMN_OPTION2 + TEXT_TYPE + COMMA +
            GeneralCategoryContract.General.COLUMN_OPTION3 + TEXT_TYPE + COMMA +
            GeneralCategoryContract.General.COLUMN_OPTION4 + TEXT_TYPE + COMMA +
            GeneralCategoryContract.General.COLUMN_ANSWER + " INTEGER )";
    public static final String INVENTION_CREATE_ENTRIES = "CREATE TABLE " + InventionCategoryContract.Invention.TABLE_NAME + "(" +
            InventionCategoryContract.Invention.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA +
            InventionCategoryContract.Invention.COLUMN_QUESTION + TEXT_TYPE + COMMA +
            InventionCategoryContract.Invention.COLUMN_OPTION1 + TEXT_TYPE + COMMA +
            InventionCategoryContract.Invention.COLUMN_OPTION2 + TEXT_TYPE + COMMA +
            InventionCategoryContract.Invention.COLUMN_OPTION3 + TEXT_TYPE + COMMA +
            InventionCategoryContract.Invention.COLUMN_OPTION4 + TEXT_TYPE + COMMA +
            InventionCategoryContract.Invention.COLUMN_ANSWER + " INTEGER )";
    private static final String TAG = LoginRegisterDBOpenHelper.class.getName().toString();
    Context context;
    private SQLiteDatabase db;

    public LoginRegisterDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(COMPUTER_CREATE_ENTRIES);
        db.execSQL(GENERAL_CREATE_ENTRIES);
        db.execSQL(INVENTION_CREATE_ENTRIES);
        computerquestions();
        generalquestions();
        inventionquestions();
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "onUpgrade: ");
        db.execSQL("DROP TABLE IF EXISTS " + Login_Register.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ComputerCategoryContract.Computer.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GeneralCategoryContract.General.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + InventionCategoryContract.Invention.TABLE_NAME);
        onCreate(db);
    }


    public void inventionquestions() {
        BufferedReader questionreader = null;
        BufferedReader option1reader = null;
        BufferedReader option2reader = null;
        BufferedReader option3reader = null;
        BufferedReader option4reader = null;
        BufferedReader answerreader = null;
        try {
            questionreader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("inquestion.txt")));


            option1reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("inoption1.txt")));

            option2reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("inoption2.txt")));

            option3reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("inoption3.txt")));

            option4reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("inoption4.txt")));

            answerreader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("inanswer.txt")));
            String currentquestion;
            String currentoption1;
            String currentoption2;
            String currentoption3;
            String currentoption4;
            String currentanswer;
            while ((currentquestion = questionreader.readLine()) != null) {
                currentoption1 = option1reader.readLine();
                currentoption2 = option2reader.readLine();
                currentoption3 = option3reader.readLine();
                currentoption4 = option4reader.readLine();
                currentanswer = answerreader.readLine();
                Questions q = new Questions(currentquestion, currentoption1, currentoption2, currentoption3, currentoption4, Integer.parseInt(currentanswer));
                addInventionQuestion(q);
                Log.i(TAG, "inventionquestions: " + q.getQuestion());
            }
        } catch (IOException e) {
        } finally {
            if (questionreader != null) {
                try {
                    questionreader.close();
                    option1reader.close();
                    option2reader.close();
                    option3reader.close();
                    option4reader.close();
                    answerreader.close();
                } catch (IOException e) {
                    //log the exception
                    Log.i(TAG, "could not read file ");
                }
            }
        }
    }

    public void generalquestions() {
        BufferedReader questionreader = null;
        BufferedReader option1reader = null;
        BufferedReader option2reader = null;
        BufferedReader option3reader = null;
        BufferedReader option4reader = null;
        BufferedReader answerreader = null;
        try {
            questionreader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("genquestion.txt")));


            option1reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("genoption1.txt")));

            option2reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("genoption2.txt")));

            option3reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("genoption3.txt")));

            option4reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("genoption4.txt")));

            answerreader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("genanswer.txt")));
            String currentquestion;
            String currentoption1;
            String currentoption2;
            String currentoption3;
            String currentoption4;
            String currentanswer;
            while ((currentquestion = questionreader.readLine()) != null) {
                currentoption1 = option1reader.readLine();
                currentoption2 = option2reader.readLine();
                currentoption3 = option3reader.readLine();
                currentoption4 = option4reader.readLine();
                currentanswer = answerreader.readLine();
                Questions q = new Questions(currentquestion, currentoption1, currentoption2, currentoption3, currentoption4, Integer.parseInt(currentanswer));
                addGeneralQuestion(q);
                Log.i(TAG, "generalquestions: " + q.getQuestion());
            }
        } catch (IOException e) {
        } finally {
            if (questionreader != null) {
                try {
                    questionreader.close();
                    option1reader.close();
                    option2reader.close();
                    option3reader.close();
                    option4reader.close();
                    answerreader.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public void computerquestions() {
        BufferedReader questionreader = null;
        BufferedReader option1reader = null;
        BufferedReader option2reader = null;
        BufferedReader option3reader = null;
        BufferedReader option4reader = null;
        BufferedReader answerreader = null;
        try {
            questionreader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("question.txt")));


            option1reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("option1.txt")));

            option2reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("option2.txt")));

            option3reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("option3.txt")));

            option4reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("option4.txt")));

            answerreader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("answer.txt")));
            String currentquestion;
            String currentoption1;
            String currentoption2;
            String currentoption3;
            String currentoption4;
            String currentanswer;
            while ((currentquestion = questionreader.readLine()) != null) {
                currentoption1 = option1reader.readLine();
                currentoption2 = option2reader.readLine();
                currentoption3 = option3reader.readLine();
                currentoption4 = option4reader.readLine();
                currentanswer = answerreader.readLine();
                Questions q = new Questions(currentquestion, currentoption1, currentoption2, currentoption3, currentoption4, Integer.parseInt(currentanswer));
                addComputerQuestion(q);
                Log.i(TAG, "computerquestions: " + q.getQuestion());
            }
        } catch (IOException e) {
        } finally {
            if (questionreader != null) {
                try {
                    questionreader.close();
                    option1reader.close();
                    option2reader.close();
                    option3reader.close();
                    option4reader.close();
                    answerreader.close();
                } catch (IOException e) {
                }
            }
        }
    }


    public void addInventionQuestion(Questions q) {
        ContentValues cv = new ContentValues();
        cv.put(GeneralCategoryContract.General.COLUMN_QUESTION, q.getQuestion());
        cv.put(GeneralCategoryContract.General.COLUMN_OPTION1, q.getOption1());
        cv.put(GeneralCategoryContract.General.COLUMN_OPTION2, q.getOption2());
        cv.put(GeneralCategoryContract.General.COLUMN_OPTION3, q.getOption3());
        cv.put(GeneralCategoryContract.General.COLUMN_OPTION4, q.getOption4());
        cv.put(GeneralCategoryContract.General.COLUMN_ANSWER, q.getAnswer());
        db.insert(InventionCategoryContract.Invention.TABLE_NAME, null, cv);
    }


    public void addGeneralQuestion(Questions q) {
        ContentValues cv = new ContentValues();
        cv.put(GeneralCategoryContract.General.COLUMN_QUESTION, q.getQuestion());
        cv.put(GeneralCategoryContract.General.COLUMN_OPTION1, q.getOption1());
        cv.put(GeneralCategoryContract.General.COLUMN_OPTION2, q.getOption2());
        cv.put(GeneralCategoryContract.General.COLUMN_OPTION3, q.getOption3());
        cv.put(GeneralCategoryContract.General.COLUMN_OPTION4, q.getOption4());
        cv.put(GeneralCategoryContract.General.COLUMN_ANSWER, q.getAnswer());
        db.insert(GeneralCategoryContract.General.TABLE_NAME, null, cv);
    }

    public void addComputerQuestion(Questions q) {
        ContentValues cv = new ContentValues();
        cv.put(ComputerCategoryContract.Computer.COLUMN_QUESTION, q.getQuestion());
        cv.put(ComputerCategoryContract.Computer.COLUMN_OPTION1, q.getOption1());
        cv.put(ComputerCategoryContract.Computer.COLUMN_OPTION2, q.getOption2());
        cv.put(ComputerCategoryContract.Computer.COLUMN_OPTION3, q.getOption3());
        cv.put(ComputerCategoryContract.Computer.COLUMN_OPTION4, q.getOption4());
        cv.put(ComputerCategoryContract.Computer.COLUMN_ANSWER, q.getAnswer());
        db.insert(ComputerCategoryContract.Computer.TABLE_NAME, null, cv);
    }

    public ArrayList<Questions> getAllQuestions(String table) {
        ArrayList<Questions> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + table, null);

        if (c.moveToFirst()) {
            do {
                Questions question = new Questions();
                question.setQuestion(c.getString(c.getColumnIndex(ComputerCategoryContract.Computer.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(ComputerCategoryContract.Computer.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(ComputerCategoryContract.Computer.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(ComputerCategoryContract.Computer.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(ComputerCategoryContract.Computer.COLUMN_OPTION4)));
                question.setAnswer(c.getInt(c.getColumnIndex(ComputerCategoryContract.Computer.COLUMN_ANSWER)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }

}
