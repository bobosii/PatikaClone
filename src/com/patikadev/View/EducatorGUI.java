package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Content;
import com.patikadev.Model.Course;
import com.patikadev.Model.Educator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EducatorGUI extends JFrame {
    private Educator educator;
    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JPanel pnl_top;
    private JPanel pnl_course_list;
    private JLabel lbl_welcome;
    private JButton btn_logout;
    private JScrollPane scrl_course_panel;
    private JTable tbl_course_list;
    private JPanel pnl_contents;
    private JTable tbl_content_list;
    private JScrollPane scrl_contents;
    private DefaultTableModel mdl_course_list;
    private Object[] row_course_list;
    private DefaultTableModel mdl_content_list;
    private Object[] row_content_list;

    public EducatorGUI(Educator educator){
        Helper.setLayout();
        this.educator = educator;
        add(wrapper);
        setSize(600,600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        //Course List
        mdl_course_list = new DefaultTableModel();
        Object[] col_patika_list = {"ID","Patika","Ders Adı","Eğitmen"};
        mdl_course_list.setColumnIdentifiers(col_patika_list);
        row_course_list = new Object[col_patika_list.length];

        tbl_course_list.setModel(mdl_course_list);
        loadPatikaModel();
        tbl_course_list.getColumn("ID").setMaxWidth(70);

        //## Course List

        //Content List

        mdl_content_list = new DefaultTableModel();
        Object[] col_content_list = {"ID","Başlık","Açıklama","Ait olduğu ders","Link"};
        mdl_content_list.setColumnIdentifiers(col_content_list);
        row_content_list = new Object[col_content_list.length];

        tbl_content_list.setModel(mdl_content_list);
        tbl_content_list.getColumn("ID").setMaxWidth(70);
        loadContentModel();


        //##Content List

        btn_logout.addActionListener(e -> {
            dispose();
            LoginGUI loginGUI = new LoginGUI();
        });
    }
    private void loadPatikaModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_course_list.getModel();
        clearModel.setRowCount(0);

        for (Course obj: Course.getList()){
            int i = 0;
            if (educator.getUser_name().equals(obj.getEducator().getUser_name())){
                row_course_list[i++] = obj.getId();
                row_course_list[i++] = obj.getPatika().getName();
                row_course_list[i++] = obj.getCourse_name();
                row_course_list[i++] = obj.getEducator().getUser_name();
                mdl_course_list.addRow(row_course_list);
            }
        }
    }
    private void loadContentModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_content_list.getModel();
        clearModel.setRowCount(0);

        for (Content obj: Content.getContentList()){
            int i = 0;
            row_content_list[i++] = obj.getContent_id();
            row_content_list[i++] = obj.getTitle();
            row_content_list[i++] = obj.getDescription();
            row_content_list[i++] = Course.getFetch(obj.getCourse_id()).getCourse_name();
            row_content_list[i++] = obj.getContent_link();
            mdl_content_list.addRow(row_content_list);
        }
    }
}
