package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Quiz {
    private int id;
    private int content_id;
    private String question;

    public Quiz(int id, int content_id, String question) {
        this.id = id;
        this.content_id = content_id;
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContent_id() {
        return content_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public static ArrayList<Quiz> getList(){
        ArrayList<Quiz> quizzes = new ArrayList<>();
        Quiz obj;

        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM quiz");
            while (rs.next()){
                int id = rs.getInt("quiz_id");
                int content_id = rs.getInt("content_id");
                String questions = rs.getString("questions");
                obj = new Quiz(id,content_id,questions);
                quizzes.add(obj);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return quizzes;
    }
    public static boolean addQuiz(String question,int content_id){
        String query = "INSERT INTO quiz(questions,content_id) VALUES(?,?)";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,question);
            pr.setInt(2,content_id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean deleteQuiz(int quiz_id){
        String query = "DELETE FROM quiz WHERE quiz_id = ?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,quiz_id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
