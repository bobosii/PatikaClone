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

}
