package pl.maciejnalewajka.worktime.Serializable;

import java.io.Serializable;

public class Tasks implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String task;
    private int time;
    private int usedTime;
    private String data;
    private String priority;
    private String extraInfo;
    private String projectId;
    private String userId;

    public Tasks (String taskId, String taskName, String taskTask, int taskTime, int taskUsedTime, String taskData, String taskPriority,
                     String taskExtraInfo, String taskProjectId, String taskUserId)
    {
        id = taskId;
        name = taskName;
        task = taskTask;
        time = taskTime;
        usedTime = taskUsedTime;
        data = taskData;
        priority = taskPriority;
        extraInfo = taskExtraInfo;
        projectId = taskProjectId;
        userId = taskUserId;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTask() {
        return task;
    }

    public int getTime() {
        return time;
    }

    public int getUsedTime() {
        return usedTime;
    }

    public String getData() {
        return data;
    }

    public String getPriority() {
        return priority;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getUserId() {
        return userId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setUsedTime(int usedTime) {
        this.usedTime = usedTime;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
