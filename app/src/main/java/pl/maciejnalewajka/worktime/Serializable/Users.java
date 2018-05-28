package pl.maciejnalewajka.worktime.Serializable;

import java.io.Serializable;

public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String type;
    private String companyId;

    public Users(String userId, String userName, String userEmail, String userPassword, String userPhone, String userType, String userCompanyId ){
        id = userId;
        name = userName;
        email = userEmail;
        password = userPassword;
        phone = userPhone;
        type = userType;
        companyId = userCompanyId;

    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getType() {
        return type;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + email + " " + password + " " + phone + " " + type + " " +  companyId;
    }
}
