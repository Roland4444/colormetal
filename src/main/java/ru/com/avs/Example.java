package ru.com.avs;


import Message.abstractions.BinaryMessage;
import abstractions.Cypher;
import abstractions.RequestMessage;
import ch.roland.ModuleGUI;
import ru.com.avs.controller.WaybillJournalController;
import ru.com.avs.model.WeighingView;
import ru.com.avs.util.JSONizer;
import ru.com.avs.util.ServerAktor;
import ru.com.avs.util.WayBillUtil;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.CompletionException;

import javax.swing.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class Example extends ModuleGUI {
    public ServerAktor akt;
    public String urlServer = "http://127.0.0.1:12121/";
    AbstractAction createandsendFatbundle;
    public String createandsendfatbundle = "createfatbundle";
    public String createfatbundle_shortcut = "control S";
    public JPanel DescriptionPanel;
    public JTextArea DescriptionText;
    public JButton RequestHelp;
    public JButton Cancel;
    public JPanel MainPanel;
    public JPanel ButtonPanel;
    public JLabel lPosition;
    public JLabel lDescription;
    public Box contents;
    public JTable PositionTable;
    public JScrollPane pane;
    public JSONizer jsonizer;
    FlowLayout experimentLayout;
    public Object[] columnsHeaderAVS = new String[] {"Дата","Время", "Накладная №", "Комментарий", "Металл", "Брутто", "Тара", "Засор" , "Примеси", "Нетто", "Режим" , "Завершено" , "Состояние" };
    public Example(){
        jsonizer =  new JSONizer();

        frame = new JFrame();
        WeighingView restored = null;
        try {
            restored = WayBillUtil.restoreBytesToWayBill(WaybillJournalController.FileNameDump);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PositionTable = new JTable( WayBillUtil.dataFromObject(restored), columnsHeaderAVS){
            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };
        contents = new Box(BoxLayout.Y_AXIS);
        lPosition = new JLabel("Позиция:");
        lDescription = new JLabel("Опишите проблему");
        DescriptionText = new JTextArea();
        pane = new JScrollPane(PositionTable);
        ButtonPanel = new JPanel(new BorderLayout());
        RequestHelp =  new JButton("Запросить изменения");
        Cancel = new JButton("Отмена");
        DescriptionPanel = new JPanel();
        experimentLayout = new FlowLayout();

    }

    public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        contents.add(lPosition);

        PositionTable.setRowHeight(40);

        pane.setMaximumSize(new Dimension(1300,100));
        pane.setPreferredSize(new Dimension(1300,100));
        pane.setMinimumSize(new Dimension(1300,100));

        ButtonPanel.setLayout(experimentLayout);
        ButtonPanel.add(RequestHelp);
        ButtonPanel.add(Cancel);

        DescriptionText.setRows(20);
        DescriptionText.setColumns(10);

        contents.add(pane);
        contents.add(lDescription);
        contents.add(DescriptionText);
        contents.add(ButtonPanel);

        frame.setContentPane(contents);
        frame.setSize(1200, 500);
        frame.setVisible(true);
    }

    public void initListeners() {
        RequestHelp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(createfatbundle_shortcut), createandsendfatbundle);
        RequestHelp.getActionMap().put(createandsendfatbundle, createandsendFatbundle);
        RequestHelp.addActionListener(createandsendFatbundle);
    }

    public void initActions() {
        createandsendFatbundle = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent f) {
                WeighingView restored = null;
                try {
                    restored = WayBillUtil.restoreBytesToWayBill(WaybillJournalController.FileNameDump);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Description::=>"+DescriptionText.getText());
                String ID = restored.getDateCreate().toString()+restored.getTimeCreate().toString()+restored.getComment();
                JOptionPane.showMessageDialog(null, "Отправляю на адрес:"+urlServer);
                RequestMessage req = new RequestMessage(ID , DescriptionText.getText(), jsonizer.JSONedRestored(restored));
                try {
                    req.addressToReply=akt.getURL_thisAktor();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                try {
                    akt.send(BinaryMessage.savedToBLOB(req), urlServer);
                } catch (UnknownHostException e) {
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА ПРИ ОТПРАВКЕ => ПРОВЕРЬТЕ СЕТЕВЫЕ НАСТРОЙКИ\n"+e);
                } catch (IOException e) {
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА ПРИ ОТПРАВКЕ => ПРОВЕРЬТЕ СЕТЕВЫЕ НАСТРОЙКИ\n"+e);
                }
                catch (CompletionException e){
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА ПРИ ОТПРАВКЕ => ПРОВЕРЬТЕ СЕТЕВЫЕ НАСТРОЙКИ\n"+e);
                }
                showMessageDialog(null, "запрос отправлен!!!");


            }
        };
        initListeners();
    }

    public void prepareAktor() throws InterruptedException {
        akt = new ServerAktor();

        akt.setAddress("http://127.0.0.1:12215/");
        akt.setCypher(new CypherImpl());
        System.out.println("\n\n\n*************************\n****Spawning JAKtor******\n*************************\n\n\n\n");
        akt.spawn();

    }
    
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, InterruptedException {
    	Example ex = new Example();
    	ex.preperaGUI();
    	ex.prepareAktor();
    	ex.initActions();
    }


    public class CypherImpl implements Cypher {

        @Override
        public byte[] decrypt(byte[] input) {
            return input;
        }

        @Override
        public byte[] encrypt(byte[] input) {
            return input;
        }

    }



}
