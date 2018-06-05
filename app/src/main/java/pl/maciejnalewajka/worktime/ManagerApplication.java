package pl.maciejnalewajka.worktime;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper;

import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.API;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.CLIENT;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.EXTRA_INFO;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.INFO;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.P_NAME;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.PLATFORM;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.PRIORITY;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.PROJECTS_TABLE;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.PROJECT_DATA;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.PROJECT_ID;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.TASK;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.TASKS_TABLE;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.TASK_DATA;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.TASK_ID;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.TASK_NAME;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.TASK_TIME;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.TIME;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.USED_TIME;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.USERS_TABLE;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.USER_COMPANY_ID;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.USER_EMAIL;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.USER_ID;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.USER_MASTER_ID;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.USER_NAME;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.USER_PASSWORD;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.USER_PHONE;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.USER_TYPE;

public class ManagerApplication extends Application {

    static final Data data = new Data();
    static boolean ULD = false;
    static boolean TLD = false;
    static boolean PLD = false;
    static String user_name = "";
    static String user_uuid = "";
    static String project_uuid = "";
    static String task_uuid = "";
    static String user_type = "";
    static String password = "";
    static Boolean ES = true;
    static ArrayList<String> project_list = new ArrayList<>();
    static String data_S = "";
    static JSONArray JAr;
    public static long startTime;
    public static String startedTask;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String NAME = "user_name";
    private static final String LOGIN = "login";
    private static final String STARTTIME = "start";
    private static final String PASSWORD = "password";
    private static final String STARTEDTASK = "task";

    private SQLiteDatabase database;

    private final ArrayList<HashMap<String, Object>> users_list_local = new ArrayList<>(7);
    private final ArrayList<HashMap<String, Object>> projects_list_local = new ArrayList<>(10);
    private final ArrayList<HashMap<String, Object>> tasks_list_local = new ArrayList<>(10);

    private final List<Object> users_id = new ArrayList<>();
    private final List<Object> users_name = new ArrayList<>();
    private final List<Object> users_email = new ArrayList<>();
    private final List<Object> users_password = new ArrayList<>();
    private final List<Object> users_phone = new ArrayList<>();
    private final List<Object> users_type = new ArrayList<>();
    private final List<Object> users_company_id = new ArrayList<>();

    private final List<Object> project_id = new ArrayList<>();
    private final List<Object> project_name = new ArrayList<>();
    private final List<Object> client = new ArrayList<>();
    private final List<Object> platform = new ArrayList<>();
    private final List<Object> api = new ArrayList<>();
    private final List<Object> time = new ArrayList<>();
    private final List<Object> project_data = new ArrayList<>();
    private final List<Object> info = new ArrayList<>();
    private final List<Object> extra_info = new ArrayList<>();
    private final List<Object> user_master_id = new ArrayList<>();

    private final List<Object> task_id = new ArrayList<>();
    private final List<Object> task_name = new ArrayList<>();
    private final List<Object> task = new ArrayList<>();
    private final List<Object> task_time = new ArrayList<>();
    private final List<Object> used_time = new ArrayList<>();
    private final List<Object> task_data = new ArrayList<>();
    private final List<Object> priority = new ArrayList<>();
    private final List<Object> extra_info2 = new ArrayList<>();
    private final List<Object> project_id2 = new ArrayList<>();
    private final List<Object> user_id = new ArrayList<>();



    public void onCreate(){
        super.onCreate();
        WorkTimeSQLiteOpenHelper helper = new WorkTimeSQLiteOpenHelper(this);
        sharedPreferences = getSharedPreferences(NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
        restoreData();
        database = helper.getWritableDatabase();
    }


    public void checkData(){
        checkUserData();
        checkProjectData();
        checkDataTask();
    }

    private void checkUserData(){
        if(users_list_local.size() < data.users_list.size()){
            users_list_local.clear();
            database.execSQL("delete from " + USERS_TABLE + ";");
            assignUser();
        }
        else if(users_list_local.size() > data.users_list.size()){
            users_list_local.clear();
            database.execSQL("delete from " + USERS_TABLE + ";");
            assignUser();
        }
        else {
            users_list_local.clear();
            database.execSQL("delete from " + USERS_TABLE + ";");
            assignUser();
        }
    }

    private void assignUser(){
        users_list_local.addAll(data.users_list);
        insertUser();
    }

    private void checkProjectData(){
        if(projects_list_local.size() < data.projects_list.size()){
            projects_list_local.clear();
            database.execSQL("delete from " + PROJECTS_TABLE + ";");
            assignProject();
        }
        else if(projects_list_local.size() > data.projects_list.size()){
            projects_list_local.clear();
            database.execSQL("delete from " + PROJECTS_TABLE + ";");
            assignProject();
        }
    }

    private void assignProject(){
        projects_list_local.addAll(data.projects_list);
        insertProject();
    }

    private void checkDataTask(){
        if(tasks_list_local.size() < data.tasks_list.size()){
            tasks_list_local.clear();
            database.execSQL("delete from " + TASKS_TABLE + ";");
            assignTask();
        }
        else if(tasks_list_local.size() > data.tasks_list.size()){
            tasks_list_local.clear();
            database.execSQL("delete from " + TASKS_TABLE + ";");
            assignTask();
        }
    }

    private void assignTask(){
        tasks_list_local.addAll(data.tasks_list);
        insertTask();
    }
    private void insertUser() {

        for(int i = 0; i < users_list_local.size(); ++i){
            users_id.add(users_list_local.get(i).get("user_id"));
            users_name.add(users_list_local.get(i).get("name"));
            users_email.add(users_list_local.get(i).get("email"));
            users_password.add(users_list_local.get(i).get("password"));
            users_phone.add(users_list_local.get(i).get("phone"));
            users_type.add(users_list_local.get(i).get("type"));
            users_company_id.add(users_list_local.get(i).get("company_id"));
        }


        for(int i = 0; i < users_list_local.size(); ++i){
            database.execSQL("insert into " + USERS_TABLE + " values ( '" + users_id.get(i) + "','" + users_name.get(i) + "','" + users_email.get(i)
                    + "','" + users_password.get(i) + "','" + users_phone.get(i) + "','" + users_type.get(i) + "','" +  users_company_id.get(i) + "');");
        }
    }


    private void insertProject() {

        for(int i = 0; i < projects_list_local.size(); ++i){
            project_id.add(projects_list_local.get(i).get("project_id"));
            project_name.add(projects_list_local.get(i).get("name"));
            client.add(projects_list_local.get(i).get("client"));
            platform.add(projects_list_local.get(i).get("platform"));
            api.add(projects_list_local.get(i).get("api"));
            time.add(projects_list_local.get(i).get("time"));
            project_data.add(projects_list_local.get(i).get("project_data"));
            info.add(projects_list_local.get(i).get("info"));
            extra_info.add(projects_list_local.get(i).get("extra_info"));
            user_master_id.add(projects_list_local.get(i).get("user_master_id"));
        }


        for(int i = 0; i < projects_list_local.size(); ++i){
            database.execSQL("insert into " + PROJECTS_TABLE + " values ( '" + project_id.get(i) + "','" + project_name.get(i) + "','" + client.get(i)
                    + "','" + platform.get(i) + "','" + api.get(i) + "','" + time.get(i) + "','" +  project_data.get(i) + "','" +  info.get(i)
                    + "','" +  extra_info.get(i) + "','" +  user_master_id.get(i) + "');");
        }
    }


    private void insertTask() {

        for(int i = 0; i < tasks_list_local.size(); ++i){
            task_id.add(tasks_list_local.get(i).get("task_id"));
            task_name.add(tasks_list_local.get(i).get("name"));
            task.add(tasks_list_local.get(i).get("task"));
            task_time.add(tasks_list_local.get(i).get("task_time"));
            used_time.add(tasks_list_local.get(i).get("used_time"));
            task_data.add(tasks_list_local.get(i).get("task_data"));
            priority.add(tasks_list_local.get(i).get("priority"));
            extra_info2.add(tasks_list_local.get(i).get("extra_info"));
            project_id2.add(tasks_list_local.get(i).get("project_id"));
            user_id.add(tasks_list_local.get(i).get("user_id"));
        }


        for(int i = 0; i < tasks_list_local.size(); ++i){
            database.execSQL("insert into " + TASKS_TABLE + " values ( '" + task_id.get(i) + "','" + task_name.get(i) + "','" + task.get(i)
                    + "','" + task_time.get(i) + "','" + used_time.get(i) + "','" + task_data.get(i) + "','" +  priority.get(i) + "','" +  extra_info2.get(i)
                    + "','" +  project_id2.get(i) + "','" +  user_id.get(i) + "');");
        }
    }

    public void addUser(String id, String name, String email, String password,
                        String phone, String type, String company_id)
    {
        database.execSQL("insert into " + USERS_TABLE + " values ( " + id + "','" + name + "','" + email
                + "','" + password + "','" + phone + "','" + type + "','" +  company_id + ");");

        HashMap<String, Object> user_map = new HashMap<>();
        user_map.put("user_id", id);
        user_map.put("name", name);
        user_map.put("email", email);
        user_map.put("password", password);
        user_map.put("phone", phone);
        user_map.put("type", type);
        user_map.put("company_id", company_id);
        users_list_local.add(user_map);

    }

    public void addProject(String id, String name, String client, String platform,
                           String api, int time, String project_data, String info, String extra_info, String user_master_id)
    {

        database.execSQL("insert into " + PROJECTS_TABLE + " values ( '" + id + "','" + name + "','" + client
                + "','" + platform + "','" + api + "','" + time + "','" +  project_data + "','" +  info
                + "','" +  extra_info + "','" +  user_master_id + "');");

        HashMap<String, Object> project_map = new HashMap<>();
        project_map.put("project_id", id);
        project_map.put("name", name);
        project_map.put("client", client);
        project_map.put("platform", platform);
        project_map.put("api", api);
        project_map.put("time", time);
        project_map.put("project_date", project_data);
        project_map.put("info", info);
        project_map.put("extraInfo", extra_info);
        project_map.put("user_master_id", user_master_id);
        projects_list_local.add(project_map);
    }

    public void addTask(String id, String name, String task, int task_time,
                        int used_time, String task_data, String priority, String extra_info, String project_id, String user_id)
    {
        database.execSQL("insert into " + TASKS_TABLE + " values ( '" + id + "','" + name + "','" + task
                + "','" + task_time + "','" + used_time + "','" + task_data + "','" +  priority + "','" +  extra_info
                + "','" +  project_id + "','" +  user_id + ")';");

        HashMap<String, Object> task_map = new HashMap<>();
        task_map.put("task_id", id);
        task_map.put("name", name);
        task_map.put("task", task);
        task_map.put("time", task_time);
        task_map.put("used_time", used_time);
        task_map.put("task_date", task_data);
        task_map.put("priority", priority);
        task_map.put("extraInfo", extra_info);
        task_map.put("project_id", project_id);
        task_map.put("user_id", user_id);
        tasks_list_local.add(task_map);
    }

    public void deleteUser(String id){
        database.execSQL("delete from " + USERS_TABLE + " where " + USER_ID + " = '" + id + "';");

    }

    public void deleteProject(String id){
        database.execSQL("delete from " + PROJECTS_TABLE + " where " + PROJECT_ID + " = '" + id + "';");
    }

    public void deleteTask(String id){
        database.execSQL("delete from " + TASKS_TABLE + " where " + TASK_ID + " = '" + id + "';");
    }

    public void updateUser(String id, String name, String email, String password,
                           String phone, String type, String company_id, String idd)
    {
        database.execSQL("update " + USERS_TABLE + " set " + USER_ID + " = '" + id + "', "
                + USER_NAME + " = '" + name + "', " + USER_EMAIL + " = '" + email + "', "
                + USER_PASSWORD + " = '" + password + "', " + USER_PHONE + " = '" + phone + "', "
                + USER_TYPE + " = '" + type + "', " + USER_COMPANY_ID + " = '" + company_id
                + "' where " + USER_ID + " = '" + idd + "';");
    }

    public void updateProject(String id, String name, String client, String platform,
                              String api, int time, String project_data, String info,
                              String extra_info, String user_master_id, String idd)
    {
        database.execSQL("update " + PROJECTS_TABLE + " set " + PROJECT_ID + " = '" + id + "', "
                + P_NAME + " = '" + name + "', " + CLIENT + " = '" + client + "', "
                + PLATFORM + " = '" + platform + "', " + API + " = '" + api + "', "
                + TIME + " = " + time + ", " + PROJECT_DATA + " = '" + project_data + "', "
                + INFO + " = '" + info + "', " + EXTRA_INFO + " = '" + extra_info + "', "
                + USER_MASTER_ID + " = '" + user_master_id + "' where " + PROJECT_ID + " = '" + idd + "';");
    }

    public void updateTask(String id, String name, String task, int task_time,
                           int used_time, String task_data, String priority, String extra_info,
                           String project_id, String user_id, String idd)
    {
        database.execSQL("update " + TASKS_TABLE + " set " + TASK_ID + " = '" + id + "', "
                + TASK_NAME + " = '" + name + "', " + TASK + " = '" + task + "', "
                + TASK_TIME + " = " + task_time + ", " + USED_TIME + " = " + used_time + ", "
                + TASK_DATA + " = '" + task_data + "', " + PRIORITY + " = '" + priority + "', "
                + EXTRA_INFO + " = '" + extra_info + "', " + PROJECT_ID + " = '" + project_id + "' , "
                + USER_ID + " = '" + user_id + "' where " + TASK_ID + " = '" + idd + "';");
    }


    private ArrayList<String> userLoad(String user){
        ArrayList<String> listU;
        try (Cursor cursor = database.rawQuery("select * from " + USERS_TABLE + " where user_id = '" + user + "'", null)) {
            cursor.moveToFirst();
            listU = new ArrayList<>();
            if (!cursor.isAfterLast()) {
                do {
                    listU.add(cursor.getString(0));
                    listU.add(cursor.getString(1));
                    listU.add(cursor.getString(2));
                    listU.add(cursor.getString(3));
                    listU.add(cursor.getString(4));
                    listU.add(cursor.getString(5));
                    listU.add(cursor.getString(6));
                } while (cursor.moveToNext());
            }
        }
        return listU;

    }

    private ArrayList<String> projectsLoad(String project){
        ArrayList<String> listP;
        try (Cursor cursor = database.rawQuery("select * from " + PROJECTS_TABLE + " where project_id = '" + project + "'", null)) {
            cursor.moveToFirst();
            listP = new ArrayList<>();
            if (!cursor.isAfterLast()) {
                do {
                    listP.add(cursor.getString(0));
                    listP.add(cursor.getString(1));
                    listP.add(cursor.getString(2));
                    listP.add(cursor.getString(3));
                    listP.add(cursor.getString(4));
                    listP.add(cursor.getString(5));
                    listP.add(cursor.getString(6));
                    listP.add(cursor.getString(7));
                    listP.add(cursor.getString(8));
                    listP.add(cursor.getString(9));
                } while (cursor.moveToNext());
            }
        }
        return listP;

    }

    private ArrayList<String> tasksLoad(String task){
        ArrayList<String> listT;
        try (Cursor cursor = database.rawQuery("select * from " + TASKS_TABLE + " where task_id = '" + task + "'", null)) {
            cursor.moveToFirst();
            listT = new ArrayList<>();
            if (!cursor.isAfterLast()) {
                do {
                    listT.add(cursor.getString(0));
                    listT.add(cursor.getString(1));
                    listT.add(cursor.getString(2));
                    listT.add(cursor.getString(3));
                    listT.add(cursor.getString(4));
                    listT.add(cursor.getString(5));
                    listT.add(cursor.getString(6));
                    listT.add(cursor.getString(7));
                    listT.add(cursor.getString(8));
                    listT.add(cursor.getString(9));
                } while (cursor.moveToNext());
            }
        }
        return listT;

    }

    public void sendToServerUser() throws IOException {

        ArrayList<String> UsersLocal = new ArrayList<>();
        Iterator USI;
        String user;
        Iterator ULI;
        try (Cursor cursor = database.rawQuery("select user_id from " + USERS_TABLE, null)) {
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                do {
                    String id = cursor.getString(0);
                    UsersLocal.add(id);
                } while (cursor.moveToNext());
            }
        }
        USI = user_id.iterator();
        while (USI.hasNext()){
            user = (String) USI.next();
            if (UsersLocal.contains(user)){
                ArrayList<String> user_IC = userLoad(user);
                URL url = new URL("http://155.158.135.197/worktime/edit.php?Users&user_id="+user_IC.get(0)+"&name="+user_IC.get(1)
                        +"&email="+user_IC.get(2)+"&password="+user_IC.get(3)+"&phone="+user_IC.get(4)+"&type="+user_IC.get(5)
                        +"&company_id="+user_IC.get(6));
                url.openConnection();
                UsersLocal.remove(user);

            }
            else if(!UsersLocal.contains(user)){
                URL url = new URL("http://155.158.135.197/worktime/delete.php?Users&user_id="+user);
                url.openConnection();
            }
        }
        ULI = UsersLocal.iterator();
        while(ULI.hasNext()) {
            user = (String) ULI.next();
            ArrayList<String> user_IC = userLoad(user);
            URL url = new URL("http://155.158.135.197/worktime/add.php?Users&user_id="+user_IC.get(0)+"&name="+user_IC.get(1)
                    +"&email="+user_IC.get(2)+"&password="+user_IC.get(3)+"&phone="+user_IC.get(4)+"&type="+user_IC.get(5)
                    +"&company_id="+user_IC.get(6));
            url.openConnection();
        }
    }

    public void sendToServerProject() throws IOException {

        ArrayList<String> ProjectsLocal = new ArrayList<>();
        Iterator PSI;
        String project;
        Iterator PLI;
        try (Cursor cursor = database.rawQuery("select project_id from " + PROJECTS_TABLE, null)) {
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                do {
                    String id = cursor.getString(0);
                    ProjectsLocal.add(id);
                } while (cursor.moveToNext());
            }
        }
        PSI = project_id.iterator();
        while (PSI.hasNext()){
            project = (String) PSI.next();
            if (ProjectsLocal.contains(project)){
                ArrayList<String> project_IC = projectsLoad(project);
                URL url = new URL("http://155.158.135.197/worktime/edit.php?Projects&project_id="+project_IC.get(0)+"&name="+project_IC.get(1)
                        +"&client="+project_IC.get(2)+"&platform="+project_IC.get(3)+"&api="+project_IC.get(4)+"&time="+project_IC.get(5)
                        +"&project_data="+project_IC.get(6)+"&info="+project_IC.get(7)+"&extra_info="+project_IC.get(8)+"user_master_id="+project_IC.get(9));
                url.openConnection();
                ProjectsLocal.remove(project);

            }
            else if(!ProjectsLocal.contains(project)) {
                URL url = new URL("http://155.158.135.197/worktime/delete.php?Projects&project_id=" + project);
                url.openConnection();
            }
        }
        PLI = ProjectsLocal.iterator();
        while(PLI.hasNext()) {
            project = (String) PLI.next();
            ArrayList<String>  project_IC = projectsLoad(project);
            URL url = new URL("http://155.158.135.197/worktime/add.php?Projects&project_id="+project_IC.get(0)+"&name="+project_IC.get(1)
                    +"&client="+project_IC.get(2)+"&platform="+project_IC.get(3)+"&api="+project_IC.get(4)+"&time="+project_IC.get(5)
                    +"&project_data="+project_IC.get(6)+"&info="+project_IC.get(7)+"&extra_info="+project_IC.get(8)+"user_master_id="+project_IC.get(9));
            url.openConnection();
        }
    }

    public void sendToServerTask() throws IOException {

        ArrayList<String> TasksLocal = new ArrayList<>();
        Iterator TSI;
        String task;
        Iterator TLI;
        try (Cursor cursor = database.rawQuery("select task_id from " + TASKS_TABLE, null)) {
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                do {
                    String id = cursor.getString(0);
                    TasksLocal.add(id);
                } while (cursor.moveToNext());
            }
        }
        TSI = task_id.iterator();
        while (TSI.hasNext()){
            task = (String) TSI.next();
            if (TasksLocal.contains(task)){
                ArrayList<String> tasks = tasksLoad(task);
                URL url = new URL("http://155.158.135.197/worktime/edit.php?Tasks&task_id="+tasks.get(0)+"&name="+tasks.get(1)
                        +"&task="+tasks.get(2)+"&time="+tasks.get(3)+"&used_time="+tasks.get(4)+"&task_data="+tasks.get(5)
                        +"&priority="+tasks.get(6)+"&extra_info="+tasks.get(7)+"&project_id="+tasks.get(8)+"user_id="+tasks.get(9));
                url.openConnection();
                TasksLocal.remove(task);

            }
            else if(!TasksLocal.contains(task)){
                URL url = new URL("http://155.158.135.197/worktime/delete.php?Tasks&task_id="+task);
                url.openConnection();
            }
        }
        TLI = TasksLocal.iterator();
        while(TLI.hasNext()) {
            task = (String) TLI.next();
            ArrayList<String>  tasks_IC = projectsLoad(task);
            URL url = new URL("http://155.158.135.197/worktime/add.php?Tasks&task_id=" + tasks_IC.get(0) + "&name=" + tasks_IC.get(1)
                    + "&task=" + tasks_IC.get(2) + "&time=" + tasks_IC.get(3) + "&used_time=" + tasks_IC.get(4) + "&task_data=" + tasks_IC.get(5)
                    + "&priority=" + tasks_IC.get(6) + "&extra_info=" + tasks_IC.get(7) + "&project_id=" + tasks_IC.get(8) + "user_id=" + tasks_IC.get(9));
            url.openConnection();
        }
    }
    public void save_data(){
        editor.putString(LOGIN, user_name);
        editor.putString(PASSWORD, password);
        editor.putLong(STARTTIME, startTime);
        editor.putString(STARTEDTASK,startedTask);
        editor.commit();
    }

    public void restoreData() {
        ManagerApplication.startTime = sharedPreferences.getLong(STARTTIME,0);
        ManagerApplication.user_name = sharedPreferences.getString(LOGIN, "");
        ManagerApplication.password = sharedPreferences.getString(PASSWORD, "");
        ManagerApplication.startedTask = sharedPreferences.getString(STARTEDTASK, "");
    }

    public void downloadData(){
        new DownloadUser().execute();
    }

    private static void generateJSONArray(String url) throws IOException, JSONException {
        data_S = "";
        BufferedReader buffRead = new BufferedReader(new InputStreamReader(new URL(url).openConnection().getInputStream()));
        String line = "";
        while(line != null){
            line = buffRead.readLine();
            data_S = String.format("%s%s", data_S, line);
        }
        if (data_S.equals("nullnull")) {
            JAr = new JSONArray("[]");
        }
        else {
            JAr = new JSONArray(data_S);
        }
        buffRead.close();
    }

    static class DownloadUser extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                generateJSONArray("http://155.158.135.197/WorkTime/JSON.php?Users&name="+user_name);
                userJAR();
                switch (user_type) {
                    case "User":
                        new TasksDownload().execute();
                        break;
                    case "Master":
                        new ProjectsDownload().execute();
                        break;
                    default:
                        break;
                }
            }
            catch (JSONException | IOException e) {ManagerApplication.ES = false;}
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            ULD = true;

        }

        private void userJAR() throws JSONException {
            JSONObject JOb = JAr.getJSONObject(0);
            String user_id = JOb.getString("user_id");
            String name = JOb.getString("name");
            String email = JOb.getString("email");
            String password = JOb.getString("password");
            String phone = JOb.getString("phone");
            String type = JOb.getString("type");
            String company_id = JOb.getString("company_id");
            HashMap<String, Object> user_map = new HashMap<>();
            user_map.put("user_id", user_id);
            user_map.put("name", name);
            user_map.put("email", email);
            user_map.put("password", password);
            user_map.put("phone", phone);
            user_map.put("type", type);
            user_map.put("company_id", company_id);
            data.users_list.add(user_map);
            ManagerApplication.user_uuid = user_id;
            ManagerApplication.user_type = type;
            ManagerApplication.password = password;
        }
    }

    static class TasksDownload extends AsyncTask<Void, Void, Void> {
        // Klasa pobierania tasków

        @Override
        protected Void doInBackground(Void... voids) {
            // Wykonaj w tle
            try {
                switch (user_type) {
                    case "User":
                        generateJSONArray("http://155.158.135.197/WorkTime/JSON.php?Tasks&user_id=" + user_uuid);
                        data.tasks_list.clear();
                        for(int i=0; i<JAr.length(); i++) {
                            String pid = tasksJAR(i);
                            if (!project_list.contains(pid)) {
                                project_list.add(pid);
                            }
                        }
                        new ProjectsDownload().execute();
                        break;
                    case "Master":
                        Iterator TLI = project_list.iterator();
                        data.projects_list.clear();
                        while (TLI.hasNext()) {
                            String project = (String) TLI.next();
                            generateJSONArray("http://155.158.135.197/WorkTime/JSON.php?Tasks&project_id=" + project);
                            if (!JAr.equals(new JSONArray("[]"))) {
                                tasksJAR(0);
                            }
                        }

                        break;
                    default:
                        break;
                }
            }
            catch (JSONException | IOException e) {e.printStackTrace();}
            return null;
        }

        private String tasksJAR(int i) throws JSONException {
            JSONObject JOb = JAr.getJSONObject(i);
            String task_id = JOb.getString("task_id");
            String name = JOb.getString("name");
            String task = JOb.getString("task");
            String time = JOb.getString("time");
            String used_time = JOb.getString("used_time");
            String task_date = JOb.getString("task_date");
            String priority = JOb.getString("priority");
            String extraInfo = JOb.getString("extra_info");
            String project_id = JOb.getString("project_id");
            String user_id = JOb.getString("user_id");
            HashMap<String, Object> task_map = new HashMap<>();
            task_map.put("task_id", task_id);
            task_map.put("name", name);
            task_map.put("task", task);
            task_map.put("time", time);
            task_map.put("used_time", used_time);
            task_map.put("task_date", task_date);
            task_map.put("priority", priority);
            task_map.put("extra_info", extraInfo);
            task_map.put("project_id", project_id);
            task_map.put("user_id", user_id);
            data.tasks_list.add(task_map);
            return project_id;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            TLD = true;
        }
    }

    static class ProjectsDownload extends AsyncTask<Void, Void, Void> {
        // Klasa pobierania projektów

        @Override
        protected Void doInBackground(Void... voids) {
            // Wykonaj w tle
            try {
                switch (user_type) {
                    case "Master":
                        generateJSONArray("http://155.158.135.197/WorkTime/JSON.php?Projects&user_master_id=" + user_uuid);
                        data.projects_list.clear();
                        for(int i=0; i<JAr.length(); i++){
                            String pid = projectsJAR(i);
                            if (!project_list.contains(pid)) {
                                project_list.add(pid);
                            }
                        }
                        new TasksDownload().execute();
                        break;
                    case "User":
                        Iterator PLI = project_list.iterator();
                        data.projects_list.clear();
                        while (PLI.hasNext()){
                            String project = (String) PLI.next();
                            generateJSONArray("http://155.158.135.197/WorkTime/JSON.php?Projects&project_id=" + project);
                            projectsJAR(0);
                        }
                        break;
                    default:
                        break;
                }
            }
            catch (JSONException | IOException e) {e.printStackTrace();}
            return null;
        }

        private String projectsJAR(int i) throws JSONException {
            JSONObject JOb = JAr.getJSONObject(i);
            String project_id = JOb.getString("project_id");
            String name = JOb.getString("name");
            String client = JOb.getString("client");
            String platform = JOb.getString("platform");
            String api = JOb.getString("api");
            String time = JOb.getString("time");
            String project_date = JOb.getString("project_date");
            String info = JOb.getString("info");
            String extraInfo = JOb.getString("extra_info");
            String user_master_id = JOb.getString("user_master_id");
            HashMap<String, Object> project_map = new HashMap<>();
            project_map.put("project_id", project_id);
            project_map.put("name", name);
            project_map.put("client", client);
            project_map.put("platform", platform);
            project_map.put("api", api);
            project_map.put("time", time);
            project_map.put("project_date", project_date);
            project_map.put("info", info);
            project_map.put("extra_info", extraInfo);
            project_map.put("user_master_id", user_master_id);
            data.projects_list.add(project_map);
            return project_id;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            PLD = true;
        }
    }
}

