package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;

import java.sql.PreparedStatement;
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

    public static boolean add(String user_name,String user_uname,String user_password, String user_type){
        String query = "INSERT INTO users(user_name, user_uname, user_password, user_type) VALUES (?,?,?,?)";

        Users findUser = getFetch(user_uname);
        if (findUser != null){
            Helper.showMessage("Bu kullanıcı adı daha öncek eklenmiş. Lütfen farklı kullanıcı adıyla kayıt olunuz !");
            return false;
        }
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,user_name);
            pr.setString(2,user_uname);
            pr.setString(3,user_password);
            pr.setString(4,user_type);
            int response = pr.executeUpdate();
            if (response == -1){
                Helper.showMessage("error");
            }
            return response != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public static boolean delete(int user_id){
        String query = "DELETE FROM users WHERE user_id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,user_id);
           return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Users getFetch(String user_uname){
        Users obj = null;
        String sql = "SELECT * FROM users WHERE user_uname = ?";
        PreparedStatement pr = null;
        try {
            pr = DBConnector.getInstance().prepareStatement(sql);
            pr.setString(1,user_uname);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = new Users();
                obj.setUser_id(rs.getInt("user_id"));
                obj.setUser_name(rs.getString("user_name"));
                obj.setUser_uname(rs.getString("user_uname"));
                obj.setUser_password(rs.getString("user_password"));
                obj.setUser_type(rs.getString("user_type"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return obj;
    }

    public static Users getFetch(int id){
        Users obj = null;
        String sql = "SELECT * FROM users WHERE user_id = ?";
        PreparedStatement pr = null;
        try {
            pr = DBConnector.getInstance().prepareStatement(sql);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = new Users();
                obj.setUser_id(rs.getInt("user_id"));
                obj.setUser_name(rs.getString("user_name"));
                obj.setUser_uname(rs.getString("user_uname"));
                obj.setUser_password(rs.getString("user_password"));
                obj.setUser_type(rs.getString("user_type"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return obj;
    }

    public static boolean update(int id,String name, String uname, String pass, String type){
        String query = "UPDATE users SET user_name=?, user_uname = ?, user_password=?,user_type = ? WHERE user_id = ?";
        Users findUser = getFetch(uname);
        if (findUser != null && findUser.getUser_id() != id){
            Helper.showMessage("Bu kullanıcı adı daha öncek eklenmiş. Lütfen farklı kullanıcı adıyla kayıt olunuz !");
            return false;
        }
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setString(2,uname);
            pr.setString(3,pass);
            pr.setString(4,type);
            pr.setInt(5,id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static ArrayList<Users> searchUserList(String query){
        ArrayList<Users> userList = new ArrayList<>();
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

    public static String searchQuery(String name, String uname, String type){
        String query = "SELECT * FROM users where user_uname LIKE '%{{user_uname}}%' AND user_name LIKE '%{{user_name}}%' AND user_type LIKE '%{{user_type}}%'";
        query = query.replace("{{user_uname}}",uname);
        query =query.replace("{{user_name}}",name);
        query = query.replace("{{user_type}}",type);
        return query;
    }

}
























