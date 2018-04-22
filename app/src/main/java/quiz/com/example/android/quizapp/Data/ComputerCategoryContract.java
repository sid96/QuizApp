package quiz.com.example.android.quizapp.Data;

import android.provider.BaseColumns;


public class ComputerCategoryContract {

    public static final class Computer implements BaseColumns {

        public static final String TABLE_NAME = "Computer";
        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_OPTION4 = "option4";
        public static final String COLUMN_ANSWER = "answer";

    }


}
