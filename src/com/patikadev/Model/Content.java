package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import javax.swing.plaf.nimbus.State;
import java.lang.reflect.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Content {
    private int content_id;
    private int course_id;
    private int user_id;
    private String description;
    private String content_link;
    private String title;
    private Users educator;
    private Course course;

    public Content(int content_id, int course_id, int user_id, String description, String content_link, String title) {
        this.content_id = content_id;
        this.course_id = course_id;
        this.user_id = user_id;
        this.description = description;
        this.content_link = content_link;
        this.title = title;
        this.course = Course.getFetch(course_id);
        this.educator = Users.getFetch(user_id);
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getContent_id() {
        return content_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent_link() {
        return content_link;
    }

    public void setContent_link(String content_link) {
        this.content_link = content_link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Users getEducator() {
        return educator;
    }

    public void setEducator(Users educator) {
        this.educator = educator;
    }

    public static ArrayList<Content> getContentList(){
        ArrayList<Content> contents = new ArrayList<>();
        Content obj;

        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM contents");
            while (rs.next()){
                int id = rs.getInt("content_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String contentLink = rs.getString("content_link");
                int user_id = rs.getInt("user_id");
                int course_id = rs.getInt("course_id");
                obj = new Content(id,course_id,user_id,description,contentLink,title);
                contents.add(obj);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contents;
    }

    public static boolean add(int content_id, int course_id, int user_id, String description, String content_link, String title){
        return true;
    }
}
