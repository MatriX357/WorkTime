package pl.maciejnalewajka.worktime.SQLiteOpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class TasksTable extends android.database.sqlite.SQLiteOpenHelper{

    private static final String name = "my.db";
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



    public TasksTable(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);

    }

    private void createTable(SQLiteDatabase db) {
        db.execSQL("create table " + TASKS_TABLE + " ( " +
                TASK_ID + " text primary key not null, " +
                TASK_NAME + " text, " +
                TASK + " text, " +
                TASK_TIME + " integer, " +
                USED_TIME + " integer, " +
                TASK_DATA + " text, " +
                PRIORITY + " text, " +
                EXTRA_INFO + " text, " +
                PROJECT_ID + "text" +
                USER_ID + " text " +
                ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}