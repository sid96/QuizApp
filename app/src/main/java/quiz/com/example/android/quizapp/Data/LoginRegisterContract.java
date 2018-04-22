package quiz.com.example.android.quizapp.Data;

import android.provider.BaseColumns;

public final class LoginRegisterContract {

    public static final class Login_Register implements BaseColumns {

        public static final String TABLE_NAME = "Login_Register";
        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_AVATAR = "avatar";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PASS = "password";

    }
}
