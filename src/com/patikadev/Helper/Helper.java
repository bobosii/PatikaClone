package com.patikadev.Helper;

import java.awt.*;
import javax.swing.*;
import java.awt.*;

public class Helper {
    public static int screenCenterPoint(String eksen, Dimension size){
        int point;
        switch (eksen){
            case "x":
            point = (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
            break;
            case "y":
                point = (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
                break;
            default:
                point = 0;
        }
        return point;
    }

    public static void setLayout(){
        for (UIManager.LookAndFeelInfo info: UIManager.getInstalledLookAndFeels()){
            if ("Nimbus".equals(info.getName())){
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException |
                         InstantiationException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
    }

    public static boolean isFieldEmpty(JTextField textField){
        return textField.getText().trim().isEmpty();
    }
    public static void showMessage(String message){
        optionPageTR();
        String msg;
        String title;
        switch (message){
            case "fill":
                msg = "Lütfen bütün alanları doldurun !";
                title = "Hata !";
                break;
            case "done":
                msg = "İşlem başarıyla gerçekleşti !";
                title = "Başarılı işlem !";
                break;
            case "error":
                msg = "İşlem gerçekleştirilemedi !";
                title = "Hata !";
                break;
            default:
                msg = message;
                title = "Hata!";
                break;
        }
        JOptionPane.showMessageDialog(null,msg,title,JOptionPane.INFORMATION_MESSAGE);

    }
    public static void optionPageTR(){
        UIManager.put("OptionPane.okButtonText","Tamam");
    }

}
