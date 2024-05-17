package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Operator;
import com.patikadev.Model.Users;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class OperatorGUI extends JFrame {

    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JPanel pnl_user_list;
    private JScrollPane scrl_user_list;
    private JTable tbl_user_list;
    private final Operator operator;
    private DefaultTableModel mdl_user_list;
    private Object[] row_user_list;

    OperatorGUI(Operator operator){
        this.operator = operator;
        add(wrapper);
        setSize(1000,500);

        int x = Helper.screenCenterPoint("x",getSize());
        int y = Helper.screenCenterPoint("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_welcome.setText("Hoşgeldin : " + operator.getUser_name());

        // ModelUserList

        mdl_user_list = new DefaultTableModel();
        Object[] col_user_list = {"ID","Ad Soyad","Kullanıcı Adı","Şifre","Üyelik Tipi"};
        mdl_user_list.setColumnIdentifiers(col_user_list);
//        Object[] firstRow = {"1","Emirhan DAY","Emirhan","123","operatör"};
//        mdl_user_list.addRow(firstRow);

        for (Users obj : Users.getList()){
            Object[] row = new Object[col_user_list.length];
            row[0] = obj.getUser_id();
            row[1] = obj.getUser_name();
            row[2] = obj.getUser_uname();
            row[3] = obj.getUser_password();
            row[4] = obj.getUser_type();
            mdl_user_list.addRow(row);
        }

        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false);

    }

    public static void main(String[] args) {
        Helper.setLayout();
        Operator op = new Operator();
        op.setUser_id(1);
        op.setUser_name("Emir");
        op.setUser_password("1234");
        op.setUser_type("operator");
        op.setUser_uname("Emirhan");

        DBConnector.getInstance();

        OperatorGUI opGUI = new OperatorGUI(op);
    }
}
