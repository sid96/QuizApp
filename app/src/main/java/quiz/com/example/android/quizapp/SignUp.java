package quiz.com.example.android.quizapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import quiz.com.example.android.quizapp.Data.LoginRegisterContract;
import quiz.com.example.android.quizapp.Data.LoginRegisterDBOpenHelper;


public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = SignUp.class.getName().toString();
    private EditText name, avatar, password;
    private String sname, savatar, spassword;
    private Button signup;
    private CheckBox mCbShowPwd;
    private LoginRegisterDBOpenHelper mDBHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        name = (EditText) findViewById(R.id.name);
        avatar = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        signup = findViewById(R.id.getStarted);
        mCbShowPwd = findViewById(R.id.cbShowPwd);
        signup.setOnClickListener(this);
        mDBHelper = new LoginRegisterDBOpenHelper(this);
        mCbShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        sname = name.getText().toString().trim();
        Log.i(TAG, "onCreate: " + sname);
        savatar = avatar.getText().toString().trim();
        Log.i(TAG, "onCreate: " + savatar);
        spassword = password.getText().toString().trim();
        Log.i(TAG, "onCreate: " + spassword);
        int id = v.getId();
        switch (id) {
            case R.id.getStarted:
                if (sname.compareTo("") == 0 || savatar.compareTo("") == 0 || spassword.compareTo("") == 0) {
                    Toast.makeText(getApplicationContext(), "Some Fields are Empty", Toast.LENGTH_SHORT).show();
                } else {
                    long insert_row_id = insert();
                    if (insert_row_id == -1) {
                        Toast.makeText(getApplicationContext(), "This avatar name is already used!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                break;
        }

    }

    private long insert() {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LoginRegisterContract.Login_Register.COLUMN_AVATAR, savatar);
        contentValues.put(LoginRegisterContract.Login_Register.COLUMN_NAME, sname);
        contentValues.put(LoginRegisterContract.Login_Register.COLUMN_PASS, spassword);
        long id = db.insert(LoginRegisterContract.Login_Register.TABLE_NAME, null, contentValues);
        return id;
    }
}

