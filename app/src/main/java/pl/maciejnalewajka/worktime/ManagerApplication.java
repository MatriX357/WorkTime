package pl.maciejnalewajka.worktime;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import pl.maciejnalewajka.worktime.SQLiteOpenHelper.ProjectsTable;
import pl.maciejnalewajka.worktime.SQLiteOpenHelper.TasksTable;
import pl.maciejnalewajka.worktime.SQLiteOpenHelper.UsersTable;

import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.ProjectsTable.PROJECTS_TABLE;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.TasksTable.TASKS_TABLE;
import static pl.maciejnalewajka.worktime.SQLiteOpenHelper.UsersTable.USERS_TABLE;


public class ManagerApplication extends Application {

    private SQLiteDatabase database_users;
    private SQLiteDatabase database_projects;
    private SQLiteDatabase database_tasks;

    private final ArrayList<HashMap<String, Object>> users_list_local = new ArrayList<>();
    private final ArrayList<HashMap<String, Object>> projects_list_local = new ArrayList<>();
    private final ArrayList<HashMap<String, Object>> tasks_list_local = new ArrayList<>();

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
        UsersTable users = new UsersTable(this);
        ProjectsTable projects = new ProjectsTable(this);
        TasksTable tasks = new TasksTable(this);
        database_users = users.getWritableDatabase();
        database_projects = projects.getWritableDatabase();
        database_tasks = tasks.getWritableDatabase();
        checkData();
    }


    private void checkData(){
        checkUserData();
        checkProjectData();
        checkDataTask();
    }

    private void checkUserData(){
        if(users_list_local.size() < Data.users_list.size()){
            users_list_local.clear();
            database_users.execSQL("delete from " + USERS_TABLE + ";");
            assignUser();
        }
        else if(users_list_local.size() > Data.users_list.size()){
            users_list_local.clear();
            database_users.execSQL("delete from " + USERS_TABLE + ";");
            assignUser();
        }
        else {
            users_list_local.clear();
            database_users.execSQL("delete from " + USERS_TABLE + ";");
            assignUser();
        }
    }

    private void assignUser(){
        for(int i = 0; i < Data.users_list.size(); ++i){
            users_list_local.set(i, Data.users_list.get(i));
        }
        insertUser();
    }

    private void checkProjectData(){
        if(projects_list_local.size() < Data.projects_list.size()){
            projects_list_local.clear();
            database_projects.execSQL("delete from " + PROJECTS_TABLE + ";");
            assignProject();
        }
        else if(projects_list_local.size() > Data.projects_list.size()){
            projects_list_local.clear();
            database_projects.execSQL("delete from " + PROJECTS_TABLE + ";");
            assignProject();
        }
    }

    private void assignProject(){
        for(int i = 0; i < Data.projects_list.size(); ++i){
            projects_list_local.set(i, Data.projects_list.get(i));
        }
        insertProject();
    }

    private void checkDataTask(){
        if(tasks_list_local.size() < Data.tasks_list.size()){
            tasks_list_local.clear();
            database_tasks.execSQL("delete from " + TASKS_TABLE + ";");
            assignTask();
        }
        else if(tasks_list_local.size() > Data.tasks_list.size()){
            tasks_list_local.clear();
            database_tasks.execSQL("delete from " + TASKS_TABLE + ";");
            assignTask();
        }
    }

    private void assignTask(){
        for(int i = 0; i < Data.tasks_list.size(); ++i){
            tasks_list_local.set(i, Data.tasks_list.get(i));
        }
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
            database_users.execSQL("insert into " + USERS_TABLE + " values ( '" + users_id.get(i) + "','" + users_name.get(i) + "','" + users_email.get(i)
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
            database_projects.execSQL("insert into " + PROJECTS_TABLE + " values ( '" + project_id.get(i) + "','" + project_name.get(i) + "','" + client.get(i)
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
            database_users.execSQL("insert into " + TASKS_TABLE + " values ( '" + task_id.get(i) + "','" + task_name.get(i) + "','" + task.get(i)
                    + "','" + task_time.get(i) + "','" + used_time.get(i) + "','" + task_data.get(i) + "','" +  priority.get(i) + "','" +  extra_info2.get(i)
                    + "','" +  project_id2.get(i) + "','" +  user_id.get(i) + "');");
        }
    }

    public void addUser(String id, String name, String email, String password,
                               String phone, String type, String company_id)
    {
        database_users.execSQL("insert into " + USERS_TABLE + " values ( " + id + "','" + name + "','" + email
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

        database_projects.execSQL("insert into " + PROJECTS_TABLE + " values ( '" + id + "','" + name + "','" + client
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
        database_tasks.execSQL("insert into " + TASKS_TABLE + " values ( '" + id + "','" + name + "','" + task
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
        database_users.execSQL("delete from " + USERS_TABLE + " where " + UsersTable.USER_ID + " = '" + id + "';");

    }

    public void deleteProject(String id){
        database_projects.execSQL("delete from " + PROJECTS_TABLE + " where " + ProjectsTable.PROJECT_ID + " = '" + id + "';");
    }

    public void deleteTask(String id){
        database_tasks.execSQL("delete from " + TASKS_TABLE + " where " + TasksTable.TASK_ID + " = '" + id + "';");
    }

    public void updateUser(String id, String name, String email, String password,
                           String phone, String type, String company_id, String idd)
    {
        database_users.execSQL("update " + USERS_TABLE + " set " + UsersTable.USER_ID + " = '" + id + "', "
                + UsersTable.USER_NAME + " = '" + name + "', " + UsersTable.USER_EMAIL + " = '" + email + "', "
                + UsersTable.USER_PASSWORD + " = '" + password + "', " + UsersTable.USER_PHONE + " = '" + phone + "', "
                + UsersTable.USER_TYPE + " = '" + type + "', " + UsersTable.USER_COMPANY_ID + " = '" + company_id
                + "' where " + UsersTable.USER_ID + " = '" + idd + "';");
    }

    public void updateProject(String id, String name, String client, String platform,
                                     String api, int time, String project_data, String info,
                                     String extra_info, String user_master_id, String idd)
    {
        database_projects.execSQL("update " + PROJECTS_TABLE + " set " + ProjectsTable.PROJECT_ID + " = '" + id + "', "
                + ProjectsTable.NAME + " = '" + name + "', " + ProjectsTable.CLIENT + " = '" + client + "', "
                + ProjectsTable.PLATFORM + " = '" + platform + "', " + ProjectsTable.API + " = '" + api + "', "
                + ProjectsTable.TIME + " = " + time + ", " + ProjectsTable.PROJECT_DATA + " = '" + project_data + "', "
                + ProjectsTable.INFO + " = '" + info + "', " + ProjectsTable.EXTRA_INFO + " = '" + extra_info + "', "
                + ProjectsTable.USER_MASTER_ID + " = '" + user_master_id + "' where " + ProjectsTable.PROJECT_ID + " = '" + idd + "';");
    }

    public void updateTask(String id, String name, String task, int task_time,
                                  int used_time, String task_data, String priority, String extra_info,
                                  String project_id, String user_id, String idd)
    {
        database_tasks.execSQL("update " + TASKS_TABLE + " set " + TasksTable.TASK_ID + " = '" + id + "', "
                + TasksTable.TASK_NAME + " = '" + name + "', " + TasksTable.TASK + " = '" + task + "', "
                + TasksTable.TASK_TIME + " = " + task_time + ", " + TasksTable.USED_TIME + " = " + used_time + ", "
                + TasksTable.TASK_DATA + " = '" + task_data + "', " + TasksTable.PRIORITY + " = '" + priority + "', "
                + TasksTable.EXTRA_INFO + " = '" + extra_info + "', " + TasksTable.PROJECT_ID + " = '" + project_id + "' , "
                + TasksTable.USER_ID + " = '" + user_id + "' where " + TasksTable.TASK_ID + " = '" + idd + "';");
    }


    private ArrayList<String> userLoad(String user){
        ArrayList<String> listU;
        try (Cursor cursor = database_users.rawQuery("select * from " + USERS_TABLE + " where user_id = '" + user + "'", null)) {
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

    private ArrayList<String> ProjectsLoad(String project){
        ArrayList<String> listP;
        try (Cursor cursor = database_projects.rawQuery("select * from " + PROJECTS_TABLE + " where project_id = '" + project + "'", null)) {
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

    private ArrayList<String> TasksLoad(String task){
        ArrayList<String> listT;
        try (Cursor cursor = database_tasks.rawQuery("select * from " + TASKS_TABLE + " where task_id = '" + task + "'", null)) {
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
        try (Cursor cursor = database_users.rawQuery("select user_id from " + USERS_TABLE, null)) {
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
        try (Cursor cursor = database_projects.rawQuery("select project_id from " + PROJECTS_TABLE, null)) {
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
                ArrayList<String> project_IC = ProjectsLoad(project);
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
            ArrayList<String>  project_IC = ProjectsLoad(project);
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
        try (Cursor cursor = database_tasks.rawQuery("select task_id from " + TASKS_TABLE, null)) {
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
                ArrayList<String> tasks = TasksLoad(task);
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
            ArrayList<String>  tasks_IC = ProjectsLoad(task);
            URL url = new URL("http://155.158.135.197/worktime/add.php?Tasks&task_id=" + tasks_IC.get(0) + "&name=" + tasks_IC.get(1)
                    + "&task=" + tasks_IC.get(2) + "&time=" + tasks_IC.get(3) + "&used_time=" + tasks_IC.get(4) + "&task_data=" + tasks_IC.get(5)
                    + "&priority=" + tasks_IC.get(6) + "&extra_info=" + tasks_IC.get(7) + "&project_id=" + tasks_IC.get(8) + "user_id=" + tasks_IC.get(9));
            url.openConnection();
        }
    }

}
