package pl.maciejnalewajka.worktime.SQLiteOpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class UsersTable extends android.database.sqlite.SQLiteOpenHelper {

    private static final String name = "my.db";
    private static final int version = 1;

    public static final String USERS_TABLE = "users";
    public static final String USER_ID = "id";
    public static final String USER_NAME = "name";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_PHONE = "phone";
    public static final String USER_TYPE = "type";
    public static final String USER_COMPANY_ID = "company_id";



    public UsersTable(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    private void createTable(SQLiteDatabase db) {
        db.execSQL("create table " + USERS_TABLE + " ( " +
                USER_ID + " text primary key not null, " +
                USER_NAME + " text, " +
                USER_EMAIL + " text, " +
                USER_PASSWORD + " text, " +
                USER_PHONE + " text, " +
                USER_TYPE + "text," +
                USER_COMPANY_ID + "text" +
                ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
