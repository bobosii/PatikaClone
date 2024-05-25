package com.patikadev.Model;

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
}
