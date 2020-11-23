package ru.com.avs;

import ch.roland.ModuleGUI;
import impl.JAktor;
import ru.com.avs.controller.WaybillJournalController;
import ru.com.avs.model.WeighingView;
import ru.com.avs.util.ServerAktor;
import ru.com.avs.util.WayBillUtil;
import ru.com.avs.util.abstractions.Cypher;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

public class Example extends ModuleGUI {
    public ServerAktor akt;
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
    FlowLayout experimentLayout;
    public Object[] columnsHeaderAVS = new String[] {"Дата","Время", "Накладная №", "Комментарий", "Металл", "Брутто", "Тара", "Засор" , "Примеси", "Нетто", "Режим" , "Завершено" , "Состояние" };
    public Example(){


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

    }

    public void initActions() {

    }

    public void prepareAktor() throws InterruptedException {
        akt = new ServerAktor();

        akt.setAddress("http://127.0.0.1:12215/");
        akt.setCypher(new CypherImpl());
        akt.spawn();

    }
    
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, InterruptedException {
    	Example ex = new Example();
    	ex.preperaGUI();
    	ex.prepareAktor();
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
