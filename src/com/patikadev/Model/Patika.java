package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Patika {
    private int id;
    private String name;

    public Patika(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ArrayList<Patika> getList(){
        ArrayList<Patika> patikaList = new ArrayList<>();
        Patika obj;

        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs =st.executeQuery("SELECT * FROM patika");
            while (rs.next()){
                obj = new Patika(rs.getInt("patika_id"),rs.getString("patika_name"));
                patikaList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return patikaList;
    }

    public static boolean addPatika(String patikaName){
        String query = "INSERT INTO patika(patika_name) VALUES (?)";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,patikaName);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean update(int id,String name){
        String query = "UPDATE patika SET patika_name = ? WHERE patika_id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(2,id);
            pr.setString(1,name);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static Patika getFetch(int id){
        Patika obj = null;
        String sql = "SELECT * FROM patika WHERE patika_id = ?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(sql);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = new Patika(rs.getInt("patika_id"),rs.getString("patika_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static boolean delete(int id){
        String query = "DELETE FROM patika WHERE patika_id = ?";
        String query2 = "DELETE FROM course where patika_id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            PreparedStatement pr2 = DBConnector.getInstance().prepareStatement(query2);
            pr2.setInt(1,id);
            pr.setInt(1,id);
            return pr.executeUpdate() != -1 && pr2.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
