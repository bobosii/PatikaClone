package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Operator;
import com.patikadev.Model.Users;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame{
    private JPanel wrapper;
    private JPanel wrapper_top;
    private JPanel wrapper_bottom;
    private JTextField fld_user_name;
    private JPasswordField fld_password;
    private JButton btn_login;

    public LoginGUI(){
        add(wrapper);
        setSize(600,600);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);
        btn_login.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_name) || Helper.isFieldEmpty(fld_password)){
                Helper.showMessage("fill");
            }else {
                Users u = Users.getFetch(fld_user_name.getText(),fld_password.getText());
                if (u == null){
                    Helper.showMessage("Kullanıcı bulunamadı !");
                }else{
                    switch (u.getUser_type()){
                        case "operator":
                            OperatorGUI opGUI = new OperatorGUI((Operator) u);
                            break;
                        case "educator":
                            EducatorGUI educatorGUI = new EducatorGUI();
                            break;
                        case "student":
                            StudentGUI studentGUI = new StudentGUI();
                            break;
                    }
                    dispose();
                }
            }

        });
    }

    public static void main(String[] args) {
        Helper.setLayout();
        LoginGUI loginGUI = new LoginGUI();
    }
}
