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
import java.util.ConcurrentModificationException;
import java.util.HashMap;

import pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper;

import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.API;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.CLIENT;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.EXTRA_INFO;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.INFO;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.PLATFORM;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.PRIORITY;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.PROJECTS_TABLE;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.PROJECT_DATE;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.PROJECT_ID;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.P_NAME;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.TASK;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.TASKS_TABLE;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.WorkTimeSQLiteOpenHelper.TASK_DATE;
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

    static String user_name = "";
    static String user_uuid = "";
    static String user_type = "";
    static String project_uuid = "";
    static String task_uuid = "";
    static String password = "";
    static Boolean ES = true;
    static String select_user_uuid = "";
    static HashMap<String, HashMap<String, String>> users_list = new HashMap<>();
    static HashMap<String, HashMap<String, String>> projects_list = new HashMap<>();
    static HashMap<String, HashMap<String, String>> tasks_list = new HashMap<>();
    static ArrayList<String> id_projects_list = new ArrayList<>();
    static String data_S = "";
    static JSONArray JAr;
    public static long startTime;
    public static String startedTask;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String NAME = "user_name";
    private static final String LOGIN = "login";
    private static final String START_TIME = "start";
    private static final String PASSWORD = "password";
    private static final String STARTED_TASK = "task";

    private SQLiteDatabase database;

    static HashMap<String, HashMap<String, String>> users_list_local = new HashMap<>();
    static HashMap<String, HashMap<String, String>> projects_list_local = new HashMap<>();
    static HashMap<String, HashMap<String, String>> tasks_list_local = new HashMap<>();

    public void onCreate(){
        super.onCreate();
        WorkTimeSQLiteOpenHelper helper = new WorkTimeSQLiteOpenHelper(this);
        sharedPreferences = getSharedPreferences(NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
        restoreData();
        database = helper.getWritableDatabase();
    }
    public void loadCheckData(){
        userLoad();
        projectsLoad();
        tasksLoad();
        checkData();
    }
    public void checkData(){
        try {
            checkUserData();
            checkProjectData();
            checkDataTask();
        }catch (ConcurrentModificationException e){
            checkData();
        }
    }

    private void checkUserData(){
        for(String user_id :users_list_local.keySet()){
            if (!users_list.containsKey(user_id)){
                users_list_local.remove(user_id);
            }
        }
        for(String user_id :users_list.keySet()){
            if (users_list_local.containsKey(user_id)){
                users_list_local.remove(user_id);
                users_list_local.put(user_id, users_list.get(user_id));
            }else{
                users_list_local.put(user_id, users_list.get(user_id));
            }
        }
    }

    private void checkProjectData(){
        for(String project_id :projects_list_local.keySet()){
            if (!projects_list.containsKey(project_id)){
                projects_list_local.remove(project_id);
            }
        }
        for(String project_id :projects_list.keySet()){
            if (projects_list_local.containsKey(project_id)){
                projects_list_local.remove(project_id);
                projects_list_local.put(project_id, projects_list.get(project_id));
            }else{
                projects_list_local.put(project_id, projects_list.get(project_id));
            }
        }
    }

    private void checkDataTask(){
        for(String task_id :tasks_list_local.keySet()){
            if (!tasks_list.containsKey(task_id)){
                tasks_list_local.remove(task_id);
            }
        }
        for(String task_id :tasks_list.keySet()){
            if (tasks_list_local.containsKey(task_id)){
                tasks_list_local.remove(task_id);
                tasks_list_local.put(task_id, tasks_list.get(task_id));
            }else{
                tasks_list_local.put(task_id, tasks_list.get(task_id));
            }
        }
    }

    private void insertUser(String user_id){
        database.execSQL("insert into " + USERS_TABLE + " values ( '" + user_id + "','" + users_list.get(user_id).get("name") + "','" + users_list.get(user_id).get("email")
                    + "','" + users_list.get(user_id).get("password") + "','" + users_list.get(user_id).get("phone") + "','" + users_list.get(user_id).get("type") + "','" +  users_list.get(user_id).get("company_id") + "');");
    }


    private void insertProject(String project_id) {
        database.execSQL("insert into " + PROJECTS_TABLE + " values ( '" + project_id + "','" + projects_list.get(project_id).get("name") + "','" + projects_list.get(project_id).get("client")
                    + "','" + projects_list.get(project_id).get("platform") + "','" + projects_list.get(project_id).get("api") + "'," + projects_list.get(project_id).get("time") + ",'" +  projects_list.get(project_id).get("project_date") + "','" +  projects_list.get(project_id).get("info")
                    + "','" + projects_list.get(project_id).get("extra_info") + "','" + projects_list.get(project_id).get("user_master_id") + "');");
    }


    private void insertTask(String task_id) {
        database.execSQL("insert into " + TASKS_TABLE + " values ( '" + task_id + "','" + tasks_list.get(task_id).get("name") + "','" + tasks_list.get(task_id).get("task")
                    + "'," + tasks_list.get(task_id).get("time") + "," + tasks_list.get(task_id).get("used_time") + ",'" + tasks_list.get(task_id).get("task_date") + "','" +  tasks_list.get(task_id).get("priority") + "','" +  tasks_list.get(task_id).get("extra_info")
                    + "','" +  tasks_list.get(task_id).get("project_id") + "','" +  tasks_list.get(task_id).get("user_id") + "');");
    }

    public void deleteUser(String user_id){
        database.execSQL("delete from " + USERS_TABLE + " where " + USER_ID + " = '" + user_id + "';");

    }

    public void deleteProject(String project_id){
        database.execSQL("delete from " + PROJECTS_TABLE + " where " + PROJECT_ID + " = '" + project_id + "';");
    }

    public void deleteTask(String task_id){
        database.execSQL("delete from " + TASKS_TABLE + " where " + TASK_ID + " = '" + task_id + "';");
    }

    public void updateUser(String user_id)
    {
        database.execSQL("update " + USERS_TABLE + " set " + USER_NAME + " = '" + users_list.get(user_id).get("name") + "'," + USER_EMAIL + " = '" + users_list.get(user_id).get("email") + "',"
                + USER_PASSWORD + " = '" + users_list.get(user_id).get("password") + "'," + USER_PHONE + " = '" + users_list.get(user_id).get("phone") + "',"
                + USER_TYPE + " = '" + users_list.get(user_id).get("type") + "', " + USER_COMPANY_ID + " = '" + users_list.get(user_id).get("company_id")
                + "' where " + USER_ID + " = '" + user_id + "';");
    }

    public void updateProject(String project_id)
    {
        database.execSQL("update " + PROJECTS_TABLE + " set "
                + P_NAME + " = '" + projects_list.get(project_id).get("name") + "', " + CLIENT + " = '" + projects_list.get(project_id).get("client") + "', "
                + PLATFORM + " = '" + projects_list.get(project_id).get("platform") + "', " + API + " = '" + projects_list.get(project_id).get("api") + "', "
                + TIME + " = " + projects_list.get(project_id).get("time") + ", " + PROJECT_DATE + " = '" + projects_list.get(project_id).get("project_date") + "', "
                + INFO + " = '" + projects_list.get(project_id).get("info") + "', " + EXTRA_INFO + " = '" + projects_list.get(project_id).get("extra_info") + "', "
                + USER_MASTER_ID + " = '" + projects_list.get(project_id).get("user_master_id") + "' where " + PROJECT_ID + " = '" + project_id + "';");
    }

    public void updateTask(String task_id)
    {
        database.execSQL("update " + TASKS_TABLE + " set " + TASK_NAME + " = '" + tasks_list.get(task_id).get("name") + "', " + TASK + " = '" + tasks_list.get(task_id).get("task") + "', "
                + TASK_TIME + " = " + tasks_list.get(task_id).get("time") + ", " + USED_TIME + " = " + tasks_list.get(task_id).get("used_time") + ", "
                + TASK_DATE + " = '" + tasks_list.get(task_id).get("task_date") + "', " + PRIORITY + " = '" + tasks_list.get(task_id).get("priority") + "', "
                + EXTRA_INFO + " = '" + tasks_list.get(task_id).get("extra_info") + "', " + PROJECT_ID + " = '" + tasks_list.get(task_id).get("project_id") + "' , "
                + USER_ID + " = '" + tasks_list.get(task_id).get("user_id") + "' where " + TASK_ID + " = '" + task_id + "';");
    }

    public void addUser(String id, String name, String email, String password,
                        String phone, String type, String company_id)
    {
        HashMap<String, String> user_map = new HashMap<>();
        user_map.put("name", name);
        user_map.put("email", email);
        user_map.put("password", password);
        user_map.put("phone", phone);
        user_map.put("type", type);
        user_map.put("company_id", company_id);
        users_list.put(id, user_map);
        insertUser(id);
    }

    public void addProject(String id, String name, String client, String platform,
                           String api, int time, String project_date, String info, String extra_info, String user_master_id)
    {
        HashMap<String, String> project_map = new HashMap<>();
        project_map.put("name", name);
        project_map.put("client", client);
        project_map.put("platform", platform);
        project_map.put("api", api);
        project_map.put("time", String.valueOf(time));
        project_map.put("project_date", project_date);
        project_map.put("info", info);
        project_map.put("extraInfo", extra_info);
        project_map.put("user_master_id", user_master_id);
        projects_list.put(id, project_map);
        insertProject(id);

    }

    public void addTask(String id, String name, String task, int task_time,
                        int used_time, String task_date, String priority, String extra_info, String project_id, String user_id)
    {
        HashMap<String, String> task_map = new HashMap<>();
        task_map.put("name", name);
        task_map.put("task", task);
        task_map.put("time", String.valueOf(task_time));
        task_map.put("used_time", String.valueOf(used_time));
        task_map.put("task_date", task_date);
        task_map.put("priority", priority);
        task_map.put("extraInfo", extra_info);
        task_map.put("project_id", project_id);
        task_map.put("user_id", user_id);
        tasks_list.put(id, task_map);
        insertTask(id);
    }

    public void removeUser(String id){
        deleteUser(id);
        users_list_local.remove(id);
    }

    public void removeProject(String id){
        deleteProject(id);
        projects_list_local.remove(id);
    }

    public void removeTask(String id){
        deleteTask(id);
        tasks_list_local.remove(id);
    }

    public void editUser(String id, String name, String email, String password,
                           String phone, String type, String company_id, String idd)
    {
        database.execSQL("update " + USERS_TABLE + " set " + USER_ID + " = '" + id + "', "
                + USER_NAME + " = '" + name + "', " + USER_EMAIL + " = '" + email + "', "
                + USER_PASSWORD + " = '" + password + "', " + USER_PHONE + " = '" + phone + "', "
                + USER_TYPE + " = '" + type + "', " + USER_COMPANY_ID + " = '" + company_id
                + "' where " + USER_ID + " = '" + idd + "';");
    }

    public void editProject(String id, String name, String client, String platform,
                              String api, int time, String project_date, String info,
                              String extra_info, String user_master_id, String idd)
    {
        database.execSQL("update " + PROJECTS_TABLE + " set " + PROJECT_ID + " = '" + id + "', "
                + P_NAME + " = '" + name + "', " + CLIENT + " = '" + client + "', "
                + PLATFORM + " = '" + platform + "', " + API + " = '" + api + "', "
                + TIME + " = " + time + ", " + PROJECT_DATE + " = '" + project_date + "', "
                + INFO + " = '" + info + "', " + EXTRA_INFO + " = '" + extra_info + "', "
                + USER_MASTER_ID + " = '" + user_master_id + "' where " + PROJECT_ID + " = '" + idd + "';");
    }

    public void editTask(String id, String name, String task, int task_time,
                           int used_time, String task_date, String priority, String extra_info,
                           String project_id, String user_id, String idd)
    {
        database.execSQL("update " + TASKS_TABLE + " set " + TASK_ID + " = '" + id + "', "
                + TASK_NAME + " = '" + name + "', " + TASK + " = '" + task + "', "
                + TASK_TIME + " = " + task_time + ", " + USED_TIME + " = " + used_time + ", "
                + TASK_DATE + " = '" + task_date + "', " + PRIORITY + " = '" + priority + "', "
                + EXTRA_INFO + " = '" + extra_info + "', " + PROJECT_ID + " = '" + project_id + "' , "
                + USER_ID + " = '" + user_id + "' where " + TASK_ID + " = '" + idd + "';");
    }


    private   void userLoad(){
        users_list_local.clear();
        try (Cursor cursor = database.rawQuery("select * from " + USERS_TABLE, null)) {
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                do {
                    HashMap<String, String> user_map = new HashMap<>();
                    user_map.put("name", cursor.getString(1));
                    user_map.put("email", cursor.getString(2));
                    user_map.put("password", cursor.getString(3));
                    user_map.put("phone", cursor.getString(4));
                    user_map.put("type", cursor.getString(5));
                    user_map.put("company_id", cursor.getString(6));
                    users_list_local.put(cursor.getString(0), user_map);
                } while (cursor.moveToNext());
            }
        }

    }

    public void projectsLoad(){
        projects_list_local.clear();
        try (Cursor cursor = database.rawQuery("select * from " + PROJECTS_TABLE, null)) {
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                do {
                    HashMap<String, String> project_map = new HashMap<>();
                    project_map.put("name", cursor.getString(1));
                    project_map.put("client", cursor.getString(2));
                    project_map.put("platform", cursor.getString(3));
                    project_map.put("api", cursor.getString(4));
                    project_map.put("time", cursor.getString(5));
                    project_map.put("project_date", cursor.getString(6));
                    project_map.put("info", cursor.getString(7));
                    project_map.put("extra_info", cursor.getString(8));
                    project_map.put("user_master_id", cursor.getString(9));
                    projects_list_local.put(cursor.getString(0), project_map);
                } while (cursor.moveToNext());
            }
        }

    }

    public void tasksLoad(){
        tasks_list_local.clear();
        try (Cursor cursor = database.rawQuery("select * from " + TASKS_TABLE, null)) {
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                do {
                    HashMap<String, String> task_map = new HashMap<>();
                    task_map.put("name", cursor.getString(1));
                    task_map.put("task", cursor.getString(2));
                    task_map.put("time", cursor.getString(3));
                    task_map.put("used_time", cursor.getString(4));
                    task_map.put("task_date", cursor.getString(5));
                    task_map.put("priority", cursor.getString(6));
                    task_map.put("extra_info", cursor.getString(7));
                    task_map.put("project_id", cursor.getString(8));
                    task_map.put("user_id", cursor.getString(9));
                    tasks_list_local.put(cursor.getString(0), task_map);
                } while (cursor.moveToNext());
            }
        }
    }

    public void sendToServerUser() throws IOException {
        for (String user_id : users_list.keySet()) {
            if (!users_list_local.containsKey(user_id)) {
                users_list.remove(user_id);
                URL url = new URL("http://155.158.135.197/worktime/delete.php?Users&user_id=" + user_id);
                url.openConnection();
            }
        }
        for (String user_id : users_list_local.keySet()) {
            if (users_list.containsKey(user_id)) {
                users_list.remove(user_id);
                users_list.put(user_id, users_list_local.get(user_id));
                URL url = new URL("http://155.158.135.197/worktime/edit.php?Users&user_id=" + user_id + "&name=" + users_list.get(user_id).get("name")
                        + "&email=" + users_list.get(user_id).get("email") + "&password=" + users_list.get(user_id).get("password") + "&phone=" + users_list.get(user_id).get("phone") + "&type=" + users_list.get(user_id).get("type")
                        + "&company_id=" + users_list.get(user_id).get("company_id"));
                url.openConnection();
            } else {
                users_list.put(user_id, users_list_local.get(user_id));
                URL url = new URL("http://155.158.135.197/worktime/add.php?Users&user_id=" + user_id + "&name=" + users_list.get(user_id).get("name")
                        + "&email=" + users_list.get(user_id).get("email") + "&password=" + users_list.get(user_id).get("password") + "&phone=" + users_list.get(user_id).get("phone") + "&type=" + users_list.get(user_id).get("type")
                        + "&company_id=" + users_list.get(user_id).get("company_id"));
                url.openConnection();
            }
        }
    }

    public void sendToServerProject() throws IOException {
        for(String project_id :projects_list.keySet()){
            if (!projects_list_local.containsKey(project_id)){
                projects_list.remove(project_id);
                URL url = new URL("http://155.158.135.197/worktime/delete.php?Projects&project_id=" + project_id);
                url.openConnection();
            }
        }
        for(String project_id :projects_list_local.keySet()){
            if (projects_list.containsKey(project_id)){
                projects_list.remove(project_id);
                projects_list.put(project_id, projects_list_local.get(project_id));
                updateProject(project_id);
                URL url = new URL("http://155.158.135.197/worktime/edit.php?Projects&project_id=" + project_id + "&name=" + projects_list.get(project_id).get("name")
                        + "&client=" + projects_list.get(project_id).get("client") + "&platform=" + projects_list.get(project_id).get("platform") + "&api=" + projects_list.get(project_id).get("api") + "&time=" + projects_list.get(project_id).get("time")
                        + "&project_date=" + projects_list.get(project_id).get("project_date") + "&info=" + projects_list.get(project_id).get("info") + "&extra_info=" + projects_list.get(project_id).get("extra_info") + "user_master_id=" + projects_list.get(project_id).get("user_master_id"));
                url.openConnection();
            }else{
                projects_list.put(project_id, projects_list_local.get(project_id));
                URL url = new URL("http://155.158.135.197/worktime/add.php?Projects&project_id=" + project_id + "&name=" + projects_list.get(project_id).get("name")
                        + "&client=" + projects_list.get(project_id).get("client") + "&platform=" + projects_list.get(project_id).get("platform") + "&api=" + projects_list.get(project_id).get("api") + "&time=" + projects_list.get(project_id).get("time")
                        + "&project_date=" + projects_list.get(project_id).get("project_date") + "&info=" + projects_list.get(project_id).get("info") + "&extra_info=" + projects_list.get(project_id).get("extra_info") + "user_master_id=" + projects_list.get(project_id).get("user_master_id"));
                url.openConnection();
            }
        }
    }

    public void sendToServerTask() throws IOException {
        for(String task_id :tasks_list.keySet()){
            if (!tasks_list_local.containsKey(task_id)){
                tasks_list.remove(task_id);
                URL url = new URL("http://155.158.135.197/worktime/delete.php?Tasks&task_id=" + task_id);
                url.openConnection();
            }
        }
        for(String task_id :tasks_list_local.keySet()){
            if (tasks_list.containsKey(task_id)){
                tasks_list.remove(task_id);
                tasks_list.put(task_id, tasks_list_local.get(task_id));
                URL url = new URL("http://155.158.135.197/worktime/edit.php?Tasks&task_id=" + task_id + "&name=" + tasks_list.get(task_id).get("name")
                        + "&task=" + tasks_list.get(task_id).get("task") + "&time=" + tasks_list.get(task_id).get("time") + "&used_time=" + tasks_list.get(task_id).get("used_time") + "&task_date=" + tasks_list.get(task_id).get("task_date")
                        + "&priority=" + tasks_list.get(task_id).get("priority") + "&extra_info=" + tasks_list.get(task_id).get("extra_info") + "&project_id=" + tasks_list.get(task_id).get("project_id") + "user_id=" + tasks_list.get(task_id).get("user_id"));
                url.openConnection();
            }else{
                tasks_list.put(task_id, tasks_list_local.get(task_id));
                URL url = new URL("http://155.158.135.197/worktime/add.php?Tasks&task_id=" + task_id + "&name=" + tasks_list.get(task_id).get("name")
                        + "&task=" + tasks_list.get(task_id).get("task") + "&time=" + tasks_list.get(task_id).get("time") + "&used_time=" + tasks_list.get(task_id).get("used_time") + "&task_date=" + tasks_list.get(task_id).get("task_date")
                        + "&priority=" + tasks_list.get(task_id).get("priority") + "&extra_info=" + tasks_list.get(task_id).get("extra_info") + "&project_id=" + tasks_list.get(task_id).get("project_id") + "user_id=" + tasks_list.get(task_id).get("user_id"));
                url.openConnection();
            }
        }
    }
    public void save_data(){
        editor.putString(LOGIN, user_name);
        editor.putString(PASSWORD, password);
        editor.putLong(START_TIME, startTime);
        editor.putString(STARTED_TASK,startedTask);
        editor.commit();
    }

    public void restoreData() {
        ManagerApplication.startTime = sharedPreferences.getLong(START_TIME,0);
        ManagerApplication.user_name = sharedPreferences.getString(LOGIN, "");
        ManagerApplication.password = sharedPreferences.getString(PASSWORD, "");
        ManagerApplication.startedTask = sharedPreferences.getString(STARTED_TASK, "");
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
                userJAR(0,"USER");
                generateJSONArray("http://155.158.135.197/WorkTime/JSON.php?Users");
                for (int i = 0; i < JAr.length(); i++) {
                    userJAR(i);
                }
                ManagerApplication.ES = true;
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

        private void userJAR(int i) throws JSONException {
            userJAR(i, "ALL");
        }
        private void userJAR(int i, String Mode) throws JSONException {
            JSONObject JOb = JAr.getJSONObject(i);
            String user_id = JOb.getString("user_id");
            String name = JOb.getString("name");
            String email = JOb.getString("email");
            String password = JOb.getString("password");
            String phone = JOb.getString("phone");
            String type = JOb.getString("type");
            String company_id = JOb.getString("company_id");
            HashMap<String, String> user_map = new HashMap<>();
            user_map.put("name", name);
            user_map.put("email", email);
            user_map.put("password", password);
            user_map.put("phone", phone);
            user_map.put("type", type);
            user_map.put("company_id", company_id);
            users_list.put(user_id, user_map);
            if (Mode.equals("USER")){
                ManagerApplication.user_uuid = user_id;
                ManagerApplication.user_type = type;
                ManagerApplication.password = password;
            }

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
                        tasks_list.clear();
                        generateJSONArray("http://155.158.135.197/WorkTime/JSON.php?Tasks&user_id=" + user_uuid);
                        for (int i = 0; i < JAr.length(); i++) {
                            tasksJAR(i);
                        }
                        new ProjectsDownload().execute();
                        break;
                    case "Master":
                        tasks_list.clear();
                        for (Object project : projects_list.keySet()) {
                            generateJSONArray("http://155.158.135.197/WorkTime/JSON.php?Tasks&project_id=" + project);
                            for (int i = 0; i < JAr.length(); i++) {
                                if (!JAr.equals(new JSONArray("[]"))) {
                                    tasksJAR(i);
                                }
                            }
                        }
                        break;
                    default:
                        break;
                }
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private void tasksJAR(int i) throws JSONException {
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
            HashMap<String, String> task_map = new HashMap<>();
            task_map.put("name", name);
            task_map.put("task", task);
            task_map.put("time", time);
            task_map.put("used_time", used_time);
            task_map.put("task_date", task_date);
            task_map.put("priority", priority);
            task_map.put("extra_info", extraInfo);
            task_map.put("project_id", project_id);
            task_map.put("user_id", user_id);
            System.out.println(task_id + " tJAR");
            tasks_list.put(task_id, task_map);
            if (!id_projects_list.contains(project_id)) {
                id_projects_list.add(project_id);
            }
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
                        projects_list.clear();
                        generateJSONArray("http://155.158.135.197/WorkTime/JSON.php?Projects&user_master_id=" + user_uuid);
                        for(int i=0; i<JAr.length(); i++){
                            projectsJAR(i);
                        }
                        new TasksDownload().execute();
                        break;
                    case "User":
                        projects_list.clear();
                        for (String project : id_projects_list) {
                            System.out.println(project);
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

        private void projectsJAR(int i) throws JSONException {
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
            HashMap<String, String> project_map = new HashMap<>();
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
            System.out.println(project_id+" pJAR");
            projects_list.put(project_id, project_map);
        }
    }
}

