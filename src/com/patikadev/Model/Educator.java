package com.patikadev.Model;

public class Educator extends Users{
    public Educator() {
    }

    public Educator(int user_id, String user_name, String user_uname, String user_password, String user_type) {
        super(user_id, user_name, user_uname, user_password, user_type);
    }
}
