package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Users {
    private int user_id;
    private String user_name;
    private String user_uname;
    private String user_password;
    private String user_type;

    public Users(){

    }

    public Users(int user_id, String user_name, String user_uname, String user_password, String user_type) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_uname = user_uname;
        this.user_password = user_password;
        this.user_type = user_type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_uname() {
        return user_uname;
    }

    public void setUser_uname(String user_uname) {
        this.user_uname = user_uname;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public static ArrayList<Users> getList(){
        ArrayList<Users> userList = new ArrayList<>();
        String query = "SELECT * FROM users";
        Users obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                obj = new Users();
                obj.setUser_id(rs.getInt("user_id"));
                obj.setUser_name(rs.getString("user_name"));
                obj.setUser_uname(rs.getString("user_uname"));
                obj.setUser_password(rs.getString("user_password"));
                obj.setUser_type(rs.getString("user_type"));
                userList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }


}
