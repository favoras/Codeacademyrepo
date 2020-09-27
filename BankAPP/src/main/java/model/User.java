package model;

import java.sql.Date;

public class User {

    private String user_login;
    private String user_password;
    private String first_name;
    private String last_name;
    private Date birth_date;
    private Long personal_code;

    public User(String user_login, String user_password, String first_name, String last_name, Date birth_date, Long personal_code) {
        this.user_login = user_login;
        this.user_password = user_password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.birth_date = birth_date;
        this.personal_code = personal_code;
    }

    public String getUser_login() {
        return user_login;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public Long getPersonal_code() {
        return personal_code;
    }

    public void setPersonal_code(Long personal_code) {
        this.personal_code = personal_code;
    }
}
