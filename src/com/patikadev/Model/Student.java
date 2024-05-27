package com.patikadev.Model;

public class Student extends Users{
    public Student() {

    }

    public Student(int user_id, String user_name, String user_uname, String user_password, String user_type) {
        super(user_id, user_name, user_uname, user_password, user_type);
    }
}
