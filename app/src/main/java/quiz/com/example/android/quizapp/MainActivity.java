package quiz.com.example.android.quizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import quiz.com.example.android.quizapp.Data.LoginRegisterContract;
import quiz.com.example.android.quizapp.Data.LoginRegisterDBOpenHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getName().toString();
    private Button signup, login;
    private EditText avatar, password;
    private SharedPreferences sp;

    @Override
    protected void onResume() {
        super.onResume();
        checklogin();
    }

    public void checklogin() {
        sp = getSharedPreferences("prefs", MODE_PRIVATE);
        if (sp.getBoolean("logged", false)) {
            startActivity(new Intent(MainActivity.this, CategoryInList.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        checklogin();
        signup = (Button) findViewById(R.id.signup);
        login = (Button) findViewById(R.id.buttonforlogin);
        avatar = findViewById(R.id.loginemail);
        password = findViewById(R.id.loginpassword);
        signup.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.signup:
                Log.i(TAG, "onClick: ");
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
                break;
            case R.id.buttonforlogin:
                queryDBforlogin();
                break;
        }
    }

    private void queryDBforlogin() {
        String savatar = avatar.getText().toString().trim();
        String spassword = password.getText().toString().trim();
        if (savatar.compareTo("") == 0 || spassword.compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), "One or more Fields are empty", Toast.LENGTH_SHORT).show();
            return;
        }
        LoginRegisterDBOpenHelper mDbHelper = new LoginRegisterDBOpenHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {LoginRegisterContract.Login_Register.COLUMN_AVATAR, LoginRegisterContract.Login_Register.COLUMN_PASS, LoginRegisterContract.Login_Register.COLUMN_NAME};
        Cursor cursor = db.query(LoginRegisterContract.Login_Register.TABLE_NAME, projection,
                null, null,
                null, null, null);
        try {
            int nameColumnIndex = cursor.getColumnIndex(LoginRegisterContract.Login_Register.COLUMN_AVATAR);
            int passColumnIndex = cursor.getColumnIndex(LoginRegisterContract.Login_Register.COLUMN_PASS);
            int usernameColumnIndex = cursor.getColumnIndex(LoginRegisterContract.Login_Register.COLUMN_NAME);

            while (cursor.moveToNext()) {
                String currentavatar = cursor.getString(nameColumnIndex);
                String currentpassword = cursor.getString(passColumnIndex);
                if (currentavatar.compareTo(savatar) == 0 && currentpassword.compareTo(spassword) == 0) {
                    Intent intent1 = new Intent(MainActivity.this, CategoryInList.class);
                    finish();
                    startActivity(intent1);
                    Toast.makeText(getApplicationContext(), "Logged in successfully", Toast.LENGTH_SHORT).show();
                    sp = getSharedPreferences("prefs", 0);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("logged", true);
                    editor.commit();
                    break;
                } else if (currentavatar.compareTo(savatar) == 0 && currentpassword.compareTo(spassword) != 0) {
                    Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
            if (cursor.getPosition() == cursor.getCount()) {
                Toast.makeText(getApplicationContext(), "Sign Up to Login", Toast.LENGTH_SHORT).show();
            }
        } finally {
            cursor.close();
            db.close();
            mDbHelper.close();
        }
    }
}
