package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Course {
    private int id;
    private int user_id;
    private int patika_id;
    private String course_name;
    private String lang;

    private Patika patika;
    private Users educator;

    public Course(int id, int user_id, int patika_id, String course_name, String lang) {
        this.id = id;
        this.user_id = user_id;
        this.patika_id = patika_id;
        this.course_name = course_name;
        this.lang = lang;
        this.patika = Patika.getFetch(patika_id);
        this.educator = Users.getFetch(user_id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPatika_id() {
        return patika_id;
    }

    public void setPatika_id(int patika_id) {
        this.patika_id = patika_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Patika getPatika() {
        return patika;
    }

    public void setPatika(Patika patika) {
        this.patika = patika;
    }

    public Users getEducator() {
        return educator;
    }

    public void setEducator(Users educator) {
        this.educator = educator;
    }


    public static ArrayList<Course> getList(){
        ArrayList<Course> courseList = new ArrayList<>();
        Course obj;

        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM course");
            while (rs.next()){
                int id = rs.getInt("course_id");
                int user_id = rs.getInt("user_id");
                int patika_id = rs.getInt("patika_id");
                String course_name = rs.getString("course_name");
                String lang = rs.getString("lang");
                obj = new Course(id,user_id,patika_id,course_name,lang);
                courseList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courseList;

    }

    public static boolean add(int user_id, int patika_id, String course_name, String lang){
        String query = "INSERT INTO course(user_id, patika_id, course_name, lang) VALUES(?,?,?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,user_id);
            pr.setInt(2, patika_id);
            pr.setString(3,course_name);
            pr.setString(4,lang);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static ArrayList<Course> getListByUser(int user_id){
        ArrayList<Course> courseList = new ArrayList<>();
        Course obj;

        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM course WHERE user_id = " + user_id);
            while (rs.next()){
                int id = rs.getInt("course_id");
                int userId = rs.getInt("user_id");
                int patika_id = rs.getInt("patika_id");
                String course_name = rs.getString("course_name");
                String lang = rs.getString("lang");
                obj = new Course(id,userId,patika_id,course_name,lang);
                courseList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courseList;
    }

    public static boolean delete(int user_id){
        String query = "DELETE FROM course WHERE user_id = ?";
        ArrayList<Course> courseList = Course.getListByUser(user_id);
        for (Course obj: courseList){
            if (obj.getPatika().getId() == user_id){
                Course.delete(obj.getUser_id());
            }
        }
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,user_id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
