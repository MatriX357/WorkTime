package pl.maciejnalewajka.worktime.SQLiteOpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ProjectsTable extends android.database.sqlite.SQLiteOpenHelper {

    private static final String name = "my.db";
    private static final int version = 1;

    public static final String PROJECTS_TABLE = "projects";
    public static final String PROJECT_ID = "project_id";
    public static final String NAME = "name";
    public static final String CLIENT = "client";
    public static final String PLATFORM = "platform";
    public static final String API = "api";
    public static final String TIME = "time";
    public static final String PROJECT_DATA = "project_data";
    public static final String INFO = "info";
    public static final String EXTRAINFO = "extra_info";
    public static final String USER_MASTER_ID = "user_master_id";

    public ProjectsTable(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);

    }

    public void createTable(SQLiteDatabase db) {
        db.execSQL("create table " + PROJECTS_TABLE + " ( " +
                PROJECT_ID + " text primary key not null, " +
                NAME + " text, " +
                CLIENT + " text, " +
                PLATFORM + " text, " +
                API + " text, " +
                TIME + " integer, " +
                PROJECT_DATA + " text, " +
                INFO + " text, " +
                EXTRAINFO + " text, " +
                USER_MASTER_ID + " text " +
                ");"
        );
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
