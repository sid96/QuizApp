package quiz.com.example.android.quizapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import quiz.com.example.android.quizapp.Adapters.CustomQuizAdapter;
import quiz.com.example.android.quizapp.Data.CustomCategoryContract;
import quiz.com.example.android.quizapp.Data.CustomCategoryDatum;
import quiz.com.example.android.quizapp.Data.CustomCategoryOpenDBHelper;

public class CreateQuiz extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton fab;
    private CustomQuizAdapter customQuizAdapter;
    private ArrayList<CustomCategoryDatum> arrayList;
    private ListView createQuiz;
    private SQLiteDatabase db;
    private CustomCategoryOpenDBHelper mDbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createquiz_activity);
        arrayList = new ArrayList<>();
        mDbHelper = new CustomCategoryOpenDBHelper(CreateQuiz.this);
        db = mDbHelper.getReadableDatabase();
        FloatingActionButton fab = findViewById(R.id.fab);
        createQuiz = findViewById(R.id.createquizlistview);
        createQuiz.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           int pos, long id) {
                TextView t = v.findViewById(R.id.customcategoryname);
                String tablename = t.getText().toString().trim();
                showAddDeleteQuestionsDialog(pos, tablename);
                return true;
            }
        });
        createQuiz.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView t = view.findViewById(R.id.customcategoryname);
                Intent i = new Intent(CreateQuiz.this, CustomQuiz.class);
                i.putExtra("table_name", t.getText().toString().trim());
                startActivity(i);
            }
        });
        customQuizAdapter = new CustomQuizAdapter(this, arrayList);
        createQuiz.setAdapter(customQuizAdapter);
        fab.setOnClickListener(this);
        getTablesFromDBForListview();
    }

    public void showAddDeleteQuestionsDialog(final int pos, final String tablename) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Create Quiz or Remove Category");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        alertDialog.setPositiveButton("Add Question",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(CreateQuiz.this, AddQuestion.class);
                        i.putExtra("table_name", tablename);
                        dialog.cancel();
                        startActivity(i);
                    }
                });

        alertDialog.setNegativeButton("Remove",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        removetable(tablename);
                        arrayList.remove(pos);
                        refreshAdapter();
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }


    public void getTablesFromDBForListview() {
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name!='android_metadata' AND name!='sqlite_sequence' order by name", null);
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                addCategoryToAdapter(c.getString(c.getColumnIndex("name")));
                c.moveToNext();
            }
            refreshAdapter();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                showDialog();
                break;
        }
    }

    public void showDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("New Category");
        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.setSingleLine();
        input.setHint(R.string.categoryNameHint);
        alertDialog.setView(input);
        alertDialog.setPositiveButton("Create",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String categoryName = input.getText().toString().trim();
                        if (categoryName.compareTo("") != 0) {
                            setCustomCategory(categoryName);
                            try {
                                createTable();
                            } catch (Exception e) {
                                Toast.makeText(CreateQuiz.this, "Spaces and Special Characters are not allowed for category names", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            addCategoryToAdapter(categoryName);
                            refreshAdapter();
                            Toast.makeText(getApplicationContext(), "Created New Category", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        } else {
                            Toast.makeText(getApplicationContext(), "Enter a Category Name", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    public void setCustomCategory(String categoryName) {
        CustomCategoryContract.CustomCategory.TABLE_NAME = categoryName;
    }

    public void addCategoryToAdapter(String categoryName) {
        CustomCategoryDatum customCategoryDatum = new CustomCategoryDatum(categoryName);
        arrayList.add(customCategoryDatum);
    }

    public void refreshAdapter() {
        customQuizAdapter.notifyDataSetChanged();

    }

    public void createTable() {
        String TEXT_TYPE = " TEXT";
        String COMMA = " ,";
        String SQL_CREATE_QUERY = "CREATE TABLE " + CustomCategoryContract.CustomCategory.TABLE_NAME + "(" +
                CustomCategoryContract.CustomCategory.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA +
                CustomCategoryContract.CustomCategory.COLUMN_QUESTION + TEXT_TYPE + COMMA +
                CustomCategoryContract.CustomCategory.COLUMN_OPTION1 + TEXT_TYPE + COMMA +
                CustomCategoryContract.CustomCategory.COLUMN_OPTION2 + TEXT_TYPE + COMMA +
                CustomCategoryContract.CustomCategory.COLUMN_OPTION3 + TEXT_TYPE + COMMA +
                CustomCategoryContract.CustomCategory.COLUMN_OPTION4 + TEXT_TYPE + COMMA +
                CustomCategoryContract.CustomCategory.COLUMN_ANSWER + " INTEGER)";
        db.execSQL(SQL_CREATE_QUERY);


    }

    public void removetable(String tablename) {
        db.execSQL("DROP TABLE IF EXISTS " + tablename);
    }
}

