package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;
import com.patikadev.Helper.Item;
import com.patikadev.Model.Course;
import com.patikadev.Model.Operator;
import com.patikadev.Model.Patika;
import com.patikadev.Model.Users;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

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
    private JTextField fld_sh_user_name;
    private JTextField fld_sh_user_uname;
    private JComboBox cmb_sh_user_type;
    private JButton btn_user_sh;
    private JPanel pnl_patika_list;
    private JScrollPane scrl_patika_list;
    private JTable tbl_patika_list;
    private JPanel pnl_patika_add;
    private JTextField fld_patika_name;
    private JButton btn_patika_add;
    private JPanel pnl_course_list;
    private JScrollPane scrl_course_list;
    private JTable tbl_course_list;
    private JPanel pnl_course_add;
    private JTextField fld_course_name;
    private JTextField fld_course_lang;
    private JComboBox cmb_course_patika;
    private JComboBox cmb_course_user;
    private JButton btn_course_add;
    private final Operator operator;
    private DefaultTableModel mdl_user_list;
    private Object[] row_user_list;
    private DefaultTableModel mdl_patika_list;
    private Object[] row_patika_list;
    private JPopupMenu patikaMenu;
    private DefaultTableModel mdl_course_list;
    private Object[] row_course_list;


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

        tbl_user_list.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE){
                int user_id = Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString());
                String user_name = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 1).toString();
                String user_uname = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 2).toString();
                String user_password = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 3).toString();
                String user_type = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 4).toString();
                if (Users.update(user_id,user_name,user_uname,user_password,user_type)){
                    Helper.showMessage("done");
                }else {
                    Helper.showMessage("error");
                }
                loadCourseList();
                loadUserModel();
                loadEducatorCombo();
            }
        });
        // PatikaList

        patikaMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Güncelle");
        JMenuItem deleteMenu = new JMenuItem("Sil");
        patikaMenu.add(updateMenu);
        patikaMenu.add(deleteMenu);

        updateMenu.addActionListener(e -> {
            int select_id = Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(),0).toString());
            UpdatePatikaGUI updateGUI = new UpdatePatikaGUI(Patika.getFetch(select_id));
            updateGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadPatikaModel();
                    loadPatikaCombo();
                    loadCourseList();
                }
            });
        });

        deleteMenu.addActionListener(e -> {
            if (Helper.confirm("sure")){
                int select_id = Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(),0).toString());
                if (Patika.delete(select_id)){
                    Helper.showMessage("done");
                    loadPatikaModel();
                    loadPatikaCombo();
                    loadCourseList();
                }else {
                    Helper.showMessage("error");
                }
            }
        });

        mdl_patika_list = new DefaultTableModel();
        Object[] col_patika_list = {"ID","Patika adı"};
        mdl_patika_list.setColumnIdentifiers(col_patika_list);
        row_patika_list = new Object[col_patika_list.length];


        loadPatikaModel();

        tbl_patika_list.setModel(mdl_patika_list);
        tbl_patika_list.setComponentPopupMenu(patikaMenu);
        tbl_patika_list.getTableHeader().setReorderingAllowed(false);
        tbl_patika_list.getColumnModel().getColumn(0).setMaxWidth(100);

        tbl_patika_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selectedRow = tbl_patika_list.rowAtPoint(point);
                tbl_patika_list.setRowSelectionInterval(selectedRow,selectedRow);
            }
        });


        //## PatikaList

        // CourseList

        mdl_course_list = new DefaultTableModel();
        Object[] col_courseList ={"ID","Ders Adı","Programala dili","Patika","Eğitmen"};
        mdl_course_list.setColumnIdentifiers(col_courseList);
        row_course_list = new Object[col_courseList.length];


        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);
        loadCourseList();
        loadPatikaCombo();
        loadEducatorCombo();
        // ##CourseList


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
                   loadEducatorCombo();
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
                if (Helper.confirm("sure")){
                    int user_id = Integer.parseInt(fld_user_id.getText());
                    if (Users.delete(user_id)){
                        Helper.showMessage("done");
                        loadUserModel();
                        loadEducatorCombo();
                        loadCourseList();
                        fld_user_id.setText(null);
                    }else{
                        Helper.showMessage("error");
                    }
                }
            }
        });
        btn_user_sh.addActionListener(e -> {
            String name = fld_sh_user_name.getText();
            String uname = fld_sh_user_uname.getText();
            String type = cmb_sh_user_type.getSelectedItem().toString();
            String query = Users.searchQuery(name,uname,type);
            loadUserModel(Users.searchUserList(query));
        });
        btn_patika_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_patika_name)){
                Helper.showMessage("fill");
            }else {

                if (Patika.addPatika(fld_patika_name.getText())){
                    Helper.showMessage("done");
                    fld_patika_name.setText(null);
                    loadPatikaModel();
                    loadPatikaCombo();
                }else {
                    Helper.showMessage("error");
                }
            }
        });

        btn_course_add.addActionListener(e -> {
            Item patikaItem = (Item) cmb_course_patika.getSelectedItem();
            Item userItem = (Item) cmb_course_user.getSelectedItem();
            if (Helper.isFieldEmpty(fld_course_name) || Helper.isFieldEmpty(fld_course_lang)){
                Helper.showMessage("fill");
            }else{
                if (Course.add(userItem.getKey(), patikaItem.getKey(),fld_course_name.getText(),fld_course_lang.getText())){
                    Helper.showMessage("done");
                    loadCourseList();
                    fld_course_name.setText(null);
                    fld_course_lang.setText(null);
                }else {
                    Helper.showMessage("error");
                }
            }
        });
        btn_logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginGUI loginGUI = new LoginGUI();
            }
        });
    }

    private void loadCourseList() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_course_list.getModel();
        clearModel.setRowCount(0);
        int i = 0;
        for (Course obj : Course.getList()) {
            i = 0;
            row_course_list[i++] = obj.getId();
            row_course_list[i++] = obj.getCourse_name();
            row_course_list[i++] = obj.getLang();
            row_course_list[i++] = obj.getPatika().getName();
            row_course_list[i++] = obj.getEducator().getUser_name();
            mdl_course_list.addRow(row_course_list);
        }
    }


    private void loadPatikaModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_patika_list.getModel();
        clearModel.setRowCount(0);
        int i = 0;
        for (Patika obj : Patika.getList()){
            i = 0;
            row_patika_list[i++] = obj.getId();
            row_patika_list[i++] = obj.getName();
            mdl_patika_list.addRow(row_patika_list);
        }
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

    public void loadUserModel(ArrayList<Users> list){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);

        for (Users obj : list){
            int i = 0;
            row_user_list[i++] = obj.getUser_id();
            row_user_list[i++] = obj.getUser_name();
            row_user_list[i++] = obj.getUser_uname();
            row_user_list[i++] = obj.getUser_password();
            row_user_list[i++] = obj.getUser_type();
            mdl_user_list.addRow(row_user_list);
        }
    }


    public void loadPatikaCombo(){
        cmb_course_patika.removeAllItems();
        for (Patika obj: Patika.getList()){
            cmb_course_patika.addItem(new Item(obj.getId(), obj.getName()));
        }
    }

    public void loadEducatorCombo(){
        cmb_course_user.removeAllItems();
        for (Users obj: Users.getListOnlyEducator()){
            cmb_course_user.addItem(new Item(obj.getUser_id(), obj.getUser_name()));
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
