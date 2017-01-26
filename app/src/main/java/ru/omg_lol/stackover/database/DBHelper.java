package ru.omg_lol.stackover.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;


import java.util.ArrayList;

import ru.omg_lol.stackover.App;
import ru.omg_lol.stackover.model.OwnerModel;
import ru.omg_lol.stackover.model.QuestionModel;


public class DBHelper extends SQLiteOpenHelper implements BaseColumns {

    public static final String TITLE_COLUMN = "title";
    public static final String ANSWERS_COLUMN = "answers";
    public static final String SCORE_COLUMN = "score";
    public static final String DATE_COLUMN = "date";
    public static final String OWNER_ID_COLUMN = "owner_id";

    public static final String NAME_COLUMN = "name";

    private static final String DATABASE_TABLE_USERS = "users";
    private static final String DATABASE_TABLE_QUESTIONS = "questions";

    private static final String DATABASE_CREATE_QUESTIONS_TABLE_SCRIPT = "create table "
            + DATABASE_TABLE_QUESTIONS + " (" + BaseColumns._ID
            + " integer primary key, " + TITLE_COLUMN
            + " TEXT not null, " + OWNER_ID_COLUMN + " integer, " + ANSWERS_COLUMN
            + " integer, " + DATE_COLUMN
            + " integer, "+ SCORE_COLUMN + " integer);";

    private static final String DATABASE_CREATE_USERS_TABLE_SCRIPT = "create table "
            + DATABASE_TABLE_USERS + " (" + BaseColumns._ID
            + " integer primary key, " + NAME_COLUMN
            + " text);";

    private static final String DATABASE_NAME = "stackover.db";

    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                    int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_QUESTIONS_TABLE_SCRIPT);
        db.execSQL(DATABASE_CREATE_USERS_TABLE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("SQLite", "Обновляемся с версии " + oldVersion + " на версию " + newVersion);

        db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE_USERS);
        onCreate(db);
    }

    public static void insertIntoQuestionsAndUsers(QuestionModel question) {
        ContentValues values = new ContentValues();
        SQLiteDatabase database = App.getDatabase();

        values.put(BaseColumns._ID, question.getId());
        values.put(TITLE_COLUMN, question.getTitle());
        values.put(ANSWERS_COLUMN, question.getAnswerCount());
        values.put(SCORE_COLUMN, question.getScore());
        values.put(DATE_COLUMN, question.getCreationTimestamp());
        values.put(OWNER_ID_COLUMN, question.getOwner().getUserId());

        database.insert(DATABASE_TABLE_QUESTIONS, null, values);

        if (getItemFromUsers(question.getOwner().getUserId()) == null) {
            insertIntoUsers(question.getOwner());
        }
    }

    private static void insertIntoUsers(OwnerModel user) {
        ContentValues values = new ContentValues();
        SQLiteDatabase database = App.getDatabase();

            values.put(BaseColumns._ID, user.getUserId());
            values.put(NAME_COLUMN, user.getUserName());

            database.insert(DATABASE_TABLE_USERS, null, values);
    }
    public static ArrayList<QuestionModel> getItemsFromQuestions() {
        ArrayList<QuestionModel> questions = new ArrayList<>();

        Cursor cursor = App.getDatabase().query(DATABASE_TABLE_QUESTIONS, new String[]{BaseColumns._ID, TITLE_COLUMN,
                        DATE_COLUMN, ANSWERS_COLUMN, SCORE_COLUMN, OWNER_ID_COLUMN},
                null, null,
                null, null, null);

        if (cursor.getCount() == 0) {
            return questions;
        }

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
            int answersCount = cursor.getInt(cursor.getColumnIndex(ANSWERS_COLUMN));
            int ownerId = cursor.getInt(cursor.getColumnIndex(OWNER_ID_COLUMN));
            int score = cursor.getInt(cursor.getColumnIndex(SCORE_COLUMN));
            int date = cursor.getInt(cursor.getColumnIndex(DATE_COLUMN));
            String title = cursor.getString(cursor.getColumnIndex(TITLE_COLUMN));

            QuestionModel question = new QuestionModel();
            question.setId(id);
            question.setAnswerCount(answersCount);
            question.setTitle(title);
            question.setCreationTimestamp(date);
            question.setScore(score);
            question.setOwner(getItemFromUsers(ownerId));
            questions.add(question);
        }

        cursor.close();

        return questions;
    }

    private static OwnerModel getItemFromUsers(int userId) {
        Cursor cursor = App.getDatabase().query(DATABASE_TABLE_USERS, new String[]{BaseColumns._ID, NAME_COLUMN},
                null, null,
                null, null, null);

        if (cursor.getCount() == 0) {
            return null;
        }

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));

            if (id == userId) {
                String name = cursor.getString(cursor.getColumnIndex(NAME_COLUMN));
                cursor.close();
                return new OwnerModel(id, "", name, 0, 0, "");
            }
        }
        cursor.close();
        return null;
    }

    public static int getQuestionsCount() {
        return getItemsFromQuestions().size();
    }
}
