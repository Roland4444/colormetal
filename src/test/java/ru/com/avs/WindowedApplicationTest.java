package ru.com.avs;


import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.Test;

import javax.swing.*;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class WindowedApplicationTest  {

    @Test
    public void testMain() throws Exception {
        assertEquals(4,4);
        app app = new app();
        app.start();
        Thread.sleep(1);
    }

    class app {
        public void start() {
            final JFrame window = new JFrame("Caption");

            //Подключаем иконку из корня папки проекта
            ImageIcon img = new ImageIcon("java.png");
            window.setIconImage(img.getImage());

            //Событие "закрыть" при нажатии по крестику окна
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //Текстовое поле
            JTextField textField = new JTextField();
            textField.setBackground(Color.WHITE);
            textField.setColumns(14);


            //Создадим панель
            JPanel panel = new JPanel();

            //Создадим кнопки
            JButton minButton = new JButton("Свернуть");
            JButton maxButton = new JButton("Растянуть");
            JButton normalButton = new JButton("Оригинал");
            JButton exitButton = new JButton("Выход");
            JButton helloButton = new JButton("Hello");

            panel.add(minButton);
            panel.add(maxButton);
            panel.add(normalButton);
            panel.add(exitButton);
            panel.add(textField);
            panel.add(helloButton);


            //Добавим панель в окно
            window.getContentPane().add(panel);

            window.pack();

            //Разместим программу по центру
            window.setLocationRelativeTo(null);
            window.setVisible(true);

        }
    }
}