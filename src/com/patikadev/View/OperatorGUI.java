package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Operator;
import com.patikadev.Model.Users;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OperatorGUI extends JFrame {

    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JPanel pnl_user_list;
    private JScrollPane scrl_user_list;
    private JTable tbl_user_list;
    private JPanel pnl_user_form;
    private JTextField fld_user_name;
    private JTextField fld_user_uname;
    private JTextField fld_password;
    private JComboBox cmb_user_type;
    private JButton btn_user_add;
    private JTextField fld_user_id;
    private JButton btn_user_delete;
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

        mdl_user_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_user_list = {"ID","Ad Soyad","Kullanıcı Adı","Şifre","Üyelik Tipi"};
        mdl_user_list.setColumnIdentifiers(col_user_list);
        row_user_list = new Object[col_user_list.length];

        loadUserModel();

        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false);

        tbl_user_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String select_user_id = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString();
                fld_user_id.setText(select_user_id);
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }
        });

        btn_user_add.addActionListener(e -> {
           if (Helper.isFieldEmpty(fld_user_name) || Helper.isFieldEmpty(fld_user_uname) || Helper.isFieldEmpty(fld_password)){
               Helper.showMessage("fill");
           }else{
               String name = fld_user_name.getText();
               String uname = fld_user_uname.getText();
               String password = fld_password.getText();
               String type = cmb_user_type.getSelectedItem().toString();

               if (Users.add(name,uname,password,type)){
                   Helper.showMessage("done");
                   loadUserModel();
                   fld_user_name.setText(null);
                   fld_user_uname.setText(null);
                   fld_password.setText(null);
               }
           }
        });
        btn_user_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_id)){
                Helper.showMessage("fill");
            }else {
                int user_id = Integer.parseInt(fld_user_id.getText());
                if (Users.delete(user_id)){
                    Helper.showMessage("done");
                    loadUserModel();
                }else{
                    Helper.showMessage("error");
                }
            }
        });
    }

    public void loadUserModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);

        for (Users obj : Users.getList()){
            int i = 0;
            row_user_list[i++] = obj.getUser_id();
            row_user_list[i++] = obj.getUser_name();
            row_user_list[i++] = obj.getUser_uname();
            row_user_list[i++] = obj.getUser_password();
            row_user_list[i++] = obj.getUser_type();
            mdl_user_list.addRow(row_user_list);
        }
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
