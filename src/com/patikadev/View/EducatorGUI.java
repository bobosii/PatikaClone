package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Helper.Item;
import com.patikadev.Model.Content;
import com.patikadev.Model.Course;
import com.patikadev.Model.Educator;
import com.patikadev.Model.Quiz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

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
    private JTextField fld_content_title;
    private JTextField fld_content_descrb;
    private JComboBox cmb_select_course;
    private JTextField fld_content_link;
    private JButton btn_add;
    private JTextField fld_content_id;
    private JButton btn_delete_content;
    private JTable tbl_quiz_list;
    private JScrollPane scrl_quiz_list;
    private JPanel pnl_quiz_list;
    private JTextField fld_question;
    private JComboBox cmb_select_content;
    private JButton btn_add_question;
    private JTextField fld_quiz_id;
    private JButton btn_delete_quiz;
    private JTextField fld_search_content_title;
    private JTextField fld_search_course;
    private JButton btn_search;
    private DefaultTableModel mdl_course_list;
    private Object[] row_course_list;
    private DefaultTableModel mdl_content_list;
    private Object[] row_content_list;
    private DefaultTableModel mdl_quiz_list;
    private Object[] row_quiz_list;

    public EducatorGUI(Educator educator){
        Helper.setLayout();
        this.educator = educator;
        add(wrapper);
        setSize(800,800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setTitle(Config.PROJECT_TITLE);
        setResizable(true);
        setVisible(true);

        lbl_welcome.setText("Hoş geldiniz : " + educator.getUser_name());

        //Course List
        mdl_course_list = new DefaultTableModel();
        Object[] col_patika_list = {"ID","Patika","Ders Adı","Eğitmen"};
        mdl_course_list.setColumnIdentifiers(col_patika_list);
        row_course_list = new Object[col_patika_list.length];

        tbl_course_list.setModel(mdl_course_list);
        loadCourseModel();
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
        loadCourseCombo();
        loadContentCombo();

        //##Content List

        //Quiz List

        mdl_quiz_list = new DefaultTableModel();
        Object[] col_quiz_list = {"ID","İçerik adı","Soru"};
        mdl_quiz_list.setColumnIdentifiers(col_quiz_list);
        row_quiz_list = new Object[col_quiz_list.length];
        tbl_quiz_list.setModel(mdl_quiz_list);
        tbl_quiz_list.getColumn("ID").setMaxWidth(70);

        loadQuizModel();
        //## Quiz List

        btn_logout.addActionListener(e -> {
            dispose();
            LoginGUI loginGUI = new LoginGUI();
        });
        // Content Ekle.
        btn_add.addActionListener(e -> {
            Item courseCmb = (Item) cmb_select_course.getSelectedItem();
            String title = fld_content_title.getText();
            String describe = fld_content_descrb.getText();
            String link = fld_content_link.getText();
            int user_id = this.educator.getUser_id();
            if (Helper.isFieldEmpty(fld_content_title) && Helper.isFieldEmpty(fld_content_descrb) && Helper.isFieldEmpty(fld_content_link)){
                Helper.showMessage("fill");
            }else {
                if (Content.add(courseCmb.getKey(),user_id,describe,link,title)){
                    Helper.showMessage("done");
                    loadContentModel();
                    fld_content_descrb.setText(null);
                    fld_content_link.setText(null);
                    fld_content_title.setText(null);
                    loadContentModel();
                    loadContentCombo();
                }else {
                    Helper.showMessage("error");
                }
            }
        });
        tbl_content_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selectedRow = tbl_content_list.rowAtPoint(point);
                tbl_content_list.setRowSelectionInterval(selectedRow,selectedRow);
            }
        });
        tbl_content_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String select_user_id = tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(), 0).toString();
                fld_content_id.setText(select_user_id);
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }

        });
        btn_delete_content.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_content_id)){
                Helper.showMessage("fill");
            }else {
                int selectedRow = Integer.parseInt(fld_content_id.getText());
                if (Content.delete(selectedRow)){
                    Helper.showMessage("done");
                    loadContentModel();
                    loadContentCombo();
                    fld_content_id.setText(null);
                }else {
                    Helper.showMessage("error");
                }

            }
        });
        btn_add_question.addActionListener(e -> {
            String question = fld_question.getText();
            Item content_item = (Item) cmb_select_content.getSelectedItem();
            if (Helper.isFieldEmpty(fld_question)){
                Helper.showMessage("fill");
            }else {
                if (Quiz.addQuiz(question,content_item.getKey())){
                    Helper.showMessage("done");
                    loadQuizModel();
                }else {
                    Helper.showMessage("error");
                }
            }
        });
        tbl_quiz_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = tbl_quiz_list.rowAtPoint(point);
                tbl_quiz_list.setRowSelectionInterval(selected_row,selected_row);
            }
        });
        tbl_quiz_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String select_content_id = tbl_quiz_list.getValueAt(tbl_quiz_list.getSelectedRow(),0).toString();
                fld_quiz_id.setText(select_content_id);
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }
        });
        // Quiz silmek
        btn_delete_quiz.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_quiz_id)){
                Helper.showMessage("fill");
            }else {
                int selectedRow = Integer.parseInt(fld_quiz_id.getText());
                if (Quiz.deleteQuiz(selectedRow)){
                    Helper.showMessage("Done");
                    loadQuizModel();
                }
            }
        });

        // Content Paneli Filtreleme işlemi

        btn_search.addActionListener(e -> {
            String query = Content.searchContent(fld_search_content_title.getText());
            try {
                loadContentModel(Content.searchContentList(query));
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }
        });
    }

    private void loadCourseModel(){
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
    private void loadQuizModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_quiz_list.getModel();
        clearModel.setRowCount(0);

        int i;
        for (Quiz obj: Quiz.getList()){
            i = 0;
            row_quiz_list[i++] = obj.getId();
            row_quiz_list[i++] = Content.getFetchById(obj.getContent_id()).getTitle();
            row_quiz_list[i++] = obj.getQuestion();
            mdl_quiz_list.addRow(row_quiz_list);
        }
    }
    private void loadContentModel(ArrayList<Content> contentlist){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_content_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Content obj: contentlist){
            i = 0;
            row_content_list[i++] = obj.getContent_id();
            row_content_list[i++] = obj.getTitle();
            row_content_list[i++] = obj.getDescription();
            row_content_list[i++] = Course.getFetch(obj.getCourse_id()).getCourse_name();
            row_content_list[i++] = obj.getContent_link();
            System.out.println(obj.getContent_link());
            mdl_content_list.addRow(row_content_list);
        }
    }
    private void loadContentModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_content_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Content obj: Content.getContentList()){
            i = 0;
            row_content_list[i++] = obj.getContent_id();
            row_content_list[i++] = obj.getTitle();
            row_content_list[i++] = obj.getDescription();
            row_content_list[i++] = obj.getCourse().getCourse_name();
            row_content_list[i++] = obj.getContent_link();
            mdl_content_list.addRow(row_content_list);
        }
    }
    public void loadCourseCombo(){
        cmb_select_course.removeAllItems();
        for (Course obj: Course.getListByUser(educator.getUser_id())){
            cmb_select_course.addItem(new Item(obj.getId(), obj.getCourse_name()));
        }
    }
    public void loadContentCombo(){
        cmb_select_content.removeAllItems();
        for (Content obj: Content.getListByUser(educator.getUser_id())){
            cmb_select_content.addItem(new Item(obj.getContent_id(), obj.getTitle()));
        }

    }
}
