package quiz.com.example.android.quizapp.Data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class CustomCategoryOpenDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "customCategory.db";
    private static final String TAG = CustomCategoryOpenDBHelper.class.getName().toString();
    Context context;
    private SQLiteDatabase customCategoryDB;


    public CustomCategoryOpenDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.customCategoryDB = db;
        Log.i(TAG, "onCreate: created db");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<Questions> getCustomQuestions(String tableName) {
        ArrayList<Questions> questionList = new ArrayList<>();
        customCategoryDB = getReadableDatabase();
        Cursor c = customCategoryDB.rawQuery("SELECT * FROM " + tableName, null);

        if (c.moveToFirst()) {
            do {
                Questions question = new Questions();
                question.setQuestion(c.getString(c.getColumnIndex(CustomCategoryContract.CustomCategory.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(CustomCategoryContract.CustomCategory.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(CustomCategoryContract.CustomCategory.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(CustomCategoryContract.CustomCategory.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(CustomCategoryContract.CustomCategory.COLUMN_OPTION4)));
                question.setAnswer(c.getInt(c.getColumnIndex(CustomCategoryContract.CustomCategory.COLUMN_ANSWER)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}
