package pl.maciejnalewajka.worktime.Serializable;

import java.io.Serializable;

public class Projects implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String client;
    private String platform;
    private String api;
    private int time;
    private String data;
    private String info;
    private String extraInfo;
    private String userMasterId;

    public Projects (String projectId, String projectName, String projectClient, String projectPlatform, String projectApi, int projectTime,
                     String projectData, String projectInfo, String projectExtraInfo, String projectUserMasterId)
    {
        id = projectId;
        name = projectName;
        client = projectClient;
        platform = projectPlatform;
        api = projectApi;
        time = projectTime;
        data = projectData;
        info = projectInfo;
        extraInfo = projectExtraInfo;
        userMasterId = projectUserMasterId;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getClient() {
        return client;
    }

    public String getPlatfom() {
        return platform;
    }

    public String getApi() {
        return api;
    }

    public int getTime() {
        return time;
    }

    public String getData() {
        return data;
    }

    public String getInfo() {
        return info;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public String getUserMasterId() {
        return userMasterId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public void setPlatfom(String platfom) {
        this.platform = platform;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public void setUserMasterId(String userMasterId) {
        this.userMasterId = userMasterId;
    }
}
