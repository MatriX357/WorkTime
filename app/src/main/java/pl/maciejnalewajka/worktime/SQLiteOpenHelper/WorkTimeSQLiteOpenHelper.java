package pl.maciejnalewajka.worktime.SQLiteOpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class WorkTimeSQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper{

    private static final String name = "WorkTime.sqllite";
    private static final int version = 1;

    public static final String TASKS_TABLE = "tasks";
    public static final String TASK_ID = "task_id";
    public static final String TASK_NAME = "name";
    public static final String TASK = "task";
    public static final String TASK_TIME = "time";
    public static final String USED_TIME = "used_time";
    public static final String TASK_DATA = "task_data";
    public static final String PRIORITY = "priority";
    public static final String EXTRA_INFO = "extra_info";
    public static final String PROJECT_ID = "project_id";
    public static final String USER_ID = "user_id";
    public static final String PROJECTS_TABLE = "projects";
    public static final String P_NAME = "name";
    public static final String CLIENT = "client";
    public static final String PLATFORM = "platform";
    public static final String API = "api";
    public static final String TIME = "time";
    public static final String PROJECT_DATA = "project_data";
    public static final String INFO = "info";
    public static final String USER_MASTER_ID = "user_master_id";
    public static final String USERS_TABLE = "users";
    public static final String USER_NAME = "name";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_PHONE = "phone";
    public static final String USER_TYPE = "type";
    public static final String USER_COMPANY_ID = "company_id";



    public WorkTimeSQLiteOpenHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableTasks(db);
        createTableProjects(db);
        createTableUsers(db);

    }

    private void createTableTasks(SQLiteDatabase db) {
        db.execSQL("create table " + TASKS_TABLE + " ( " +
                TASK_ID + " text primary key not null, " +
                TASK_NAME + " text, " +
                TASK + " text, " +
                TASK_TIME + " integer, " +
                USED_TIME + " integer, " +
                TASK_DATA + " text, " +
                PRIORITY + " text, " +
                EXTRA_INFO + " text, " +
                PROJECT_ID + "text," +
                USER_ID + " text " +
                ");"
        );
    }

    private void createTableProjects(SQLiteDatabase db) {
        db.execSQL("create table " + PROJECTS_TABLE + " ( " +
                PROJECT_ID + " text primary key not null, " +
                P_NAME + " text, " +
                CLIENT + " text, " +
                PLATFORM + " text, " +
                API + " text, " +
                TIME + " integer, " +
                PROJECT_DATA + " text, " +
                INFO + " text, " +
                EXTRA_INFO + " text, " +
                USER_MASTER_ID + " text " +
                ");"
        );
    }

    private void createTableUsers(SQLiteDatabase db) {
        db.execSQL("create table " + USERS_TABLE + " ( " +
                USER_ID + " text primary key not null, " +
                USER_NAME + " text, " +
                USER_EMAIL + " text, " +
                USER_PASSWORD + " text, " +
                USER_PHONE + " text, " +
                USER_TYPE + " text," +
                USER_COMPANY_ID + " text" +
                ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}