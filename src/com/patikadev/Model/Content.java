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
    public Content(){

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
        return this.content_link;
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

    public static boolean add(int course_id, int user_id, String description, String content_link, String title){
        String query = "INSERT INTO contents(title,description,content_link,user_id,course_id) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,title);
            pr.setString(2,description);
            pr.setString(3,content_link);
            pr.setInt(4,user_id);
            pr.setInt(5,course_id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean delete(int content_id){
        String query = "DELETE FROM contents WHERE content_id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,content_id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Content getFetchById(int content_id){
        Content obj = null;
        String query = "SELECT * FROM contents where content_id = ?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,content_id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = new Content();
                obj.setTitle(rs.getString("title"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
    public static ArrayList<Content> getListByUser(int user_id){
        ArrayList<Content> contentList = new ArrayList<>();
        Content obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM contents WHERE user_id = " + user_id);
            while (rs.next()){
                int id = rs.getInt("content_id");
                int course_id = rs.getInt("course_id");
                int userId = rs.getInt("user_id");
                String description = rs.getString("description");
                String content_link = rs.getString("content_link");
                String title = rs.getString("title");
                obj = new Content(id,course_id,userId,description,content_link,title);
                contentList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contentList;
    }
    public static String searchContent(String title){
        String query = "SELECT * FROM contents WHERE title LIKE '%{{title}}%'";
        query = query.replace("{{title}}",title);
        return query;
    }
    public static ArrayList<Content> searchContentList(String query){
        ArrayList<Content> contentList = new ArrayList<>();
        Content obj;

        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                obj = new Content();
                obj.setContent_id(rs.getInt("content_id"));
                obj.setTitle(rs.getString("title"));
                obj.setContent_link(rs.getString("content_link"));
                obj.setDescription(rs.getString("description"));
                obj.setCourse_id(rs.getInt("course_id"));
                obj.setUser_id(rs.getInt("user_id"));
                contentList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contentList;
    }
}
