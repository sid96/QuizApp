package quiz.com.example.android.quizapp.Data;

import android.provider.BaseColumns;


public class CustomCategoryContract {

    public static final class CustomCategory implements BaseColumns {

        public static String TABLE_NAME;
        public static String COLUMN_ID = BaseColumns._ID;
        public static String COLUMN_QUESTION = "question";
        public static String COLUMN_OPTION1 = "option1";
        public static String COLUMN_OPTION2 = "option2";
        public static String COLUMN_OPTION3 = "option3";
        public static String COLUMN_OPTION4 = "option4";
        public static String COLUMN_ANSWER = "answer";


    }


}
