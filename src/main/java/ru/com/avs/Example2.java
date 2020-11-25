package ru.com.avs;
import Message.abstractions.BinaryMessage;
import abstractions.Cypher;
import abstractions.RequestMessage;
import ch.roland.ModuleGUI;
import ru.com.avs.controller.WaybillJournalController;
import ru.com.avs.model.WeighingView;
import ru.com.avs.util.*;
import ru.com.avs.util.readfile.Readfile;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.CompletionException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import static javax.swing.JOptionPane.showMessageDialog;

public class Example2 extends  ModuleGUI {
    public ServerAktor akt;
    public String urlServer;
    AbstractAction createinitialrequest;
    AbstractAction saveChanges;
    public String createandsendfatbundle = "createfatbundle";
    public String createfatbundle_shortcut = "control R";

    public String savechanges = "saveChanges";
    public String savechanges_shortcut = "control S";
    public JPanel DescriptionPanel;
    public JTextArea DescriptionText;
    public JButton RequestHelp;
    public JButton Cancel;
    public JButton SaveChanges;
    public Box contents;

    public JPanel MainPanel;
    public JPanel ButtonPanel;
    public JLabel lPosition;
    public JLabel lDescription;
    public JTable PositionTable;
    public JScrollPane pane;
    public JSONizer jsonizer;
    private Readfile readfile;
    JPopupMenu popupMenu;

    FlowLayout experimentLayout;
    public Object[] columnsHeaderAVS = new String[]{"Дата", "Время", "Накладная №", "Комментарий", "Металл", "Брутто", "Тара", "Засор", "Примеси", "Нетто", "Режим", "Завершено", "Состояние"};

    public Example2() {
        jsonizer = new JSONizer();
        readfile = new Readfile("setts.ini");
        urlServer = readfile.readField("urlServer");
        frame = new JFrame();
        WeighingView restored = null;
        try {
            restored = WayBillUtil.restoreBytesToWayBill(WaybillJournalController.FileNameDump);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PositionTable = new JTable(WayBillUtil.dataFromObject(restored), columnsHeaderAVS);


        //fireTableDataChanged()
        // public boolean isCellEditable(int row, int column) {
        //     return false;
        //   };
        //   };

        contents = new Box(BoxLayout.Y_AXIS);
        lPosition = new JLabel("Позиция:");
        lDescription = new JLabel("Опишите проблему");
        DescriptionText = new JTextArea();
        pane = new JScrollPane(PositionTable);
        ButtonPanel = new JPanel(new BorderLayout());
        RequestHelp = new JButton("Запросить изменения");
        SaveChanges = new JButton("Сохранить изменения");

        Cancel = new JButton("Отмена");
        DescriptionPanel = new JPanel();
        experimentLayout = new FlowLayout();

        popupMenu = new JPopupMenu();
        JMenuItem menuItemAdd = new JMenuItem("Add New Row");
        JMenuItem menuItemRemove = new JMenuItem("Remove Current Row");
        JMenuItem menuItemRemoveAll = new JMenuItem("Remove All Rows");

        popupMenu.add(menuItemAdd);
        popupMenu.add(menuItemRemove);
        popupMenu.add(menuItemRemoveAll);


    }

    public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        contents.add(lPosition);

        PositionTable.setRowHeight(40);

        pane.setMaximumSize(new Dimension(1300, 100));
        pane.setPreferredSize(new Dimension(1300, 100));
        pane.setMinimumSize(new Dimension(1300, 100));

        ButtonPanel.setLayout(experimentLayout);
        ButtonPanel.add(RequestHelp);
        ButtonPanel.add(Cancel);
        ButtonPanel.add(SaveChanges);

        DescriptionText.setRows(20);
        DescriptionText.setColumns(10);

        contents.add(pane);
        contents.add(lDescription);
        contents.add(DescriptionText);
        contents.add(ButtonPanel);

        frame.setContentPane(contents);
        frame.setSize(1200, 500);
        frame.setVisible(true);

// set data model for the table...

// sets the popup menu for the table
        PositionTable.setComponentPopupMenu(popupMenu);


        //    PositionTable.setEnabled(false);
        //    SaveChanges.setEnabled(false);
    }

    public void initListeners() {
        RequestHelp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(createfatbundle_shortcut), createandsendfatbundle);
        RequestHelp.getActionMap().put(createandsendfatbundle, createinitialrequest);
        RequestHelp.addActionListener(createinitialrequest);

        SaveChanges.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(savechanges_shortcut), savechanges);
        SaveChanges.getActionMap().put(savechanges, saveChanges);
        SaveChanges.addActionListener(saveChanges);

    }

    public void initActions() {
        saveChanges = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WeighingView restored = null;
                try {
                    restored = WayBillUtil.restoreBytesToWayBill(WaybillJournalController.FileNameDump);
                } catch (IOException u) {
                    u.printStackTrace();
                }
                System.out.println("Description::=>" + DescriptionText.getText());
                String ID = restored.getDateCreate().toString() + restored.getTimeCreate().toString() + restored.getComment();
                JOptionPane.showMessageDialog(null, "Сохраняю измнения");
                ArrayList data = new ArrayList();
                PositionTable.updateUI();


                for (int i = 0; i <= 12; i++) {
                    System.out.println(i + "index@Value::" + PositionTable.getModel().getValueAt(0, i));
                    data.add(PositionTable.getModel().getValueAt(0, i));
                }
                RequestMessage req = new RequestMessage(ID, DescriptionText.getText(), jsonizer.JSONedRestored(data));
                req.type = RequestMessage.Type.update;
                try {
                    req.addressToReply = akt.getURL_thisAktor();
                } catch (UnknownHostException p) {
                    p.printStackTrace();
                }
                try {
                    akt.send(BinaryMessage.savedToBLOB(req), urlServer);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            ;
        };
        createinitialrequest = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent f) {
                WeighingView restored = null;
                try {
                    restored = WayBillUtil.restoreBytesToWayBill(WaybillJournalController.FileNameDump);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Description::=>" + DescriptionText.getText());
                String ID = restored.getDateCreate().toString() + restored.getTimeCreate().toString() + restored.getComment();
                JOptionPane.showMessageDialog(null, "Отправляю на адрес:" + urlServer);

                RequestMessage req = new RequestMessage(ID, DescriptionText.getText(), jsonizer.JSONedRestored(restored));

                System.out.println("\n\n\nJSON to send::" + req.JSONed);
                req.Description = DescriptionText.getText();
                req.type = RequestMessage.Type.request;
                try {
                    req.addressToReply = akt.getURL_thisAktor();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                try {
                    akt.send(BinaryMessage.savedToBLOB(req), urlServer);
                } catch (UnknownHostException e) {
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА ПРИ ОТПРАВКЕ => ПРОВЕРЬТЕ СЕТЕВЫЕ НАСТРОЙКИ\n" + e);
                } catch (IOException e) {
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА ПРИ ОТПРАВКЕ => ПРОВЕРЬТЕ СЕТЕВЫЕ НАСТРОЙКИ\n" + e);
                } catch (CompletionException e) {
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА ПРИ ОТПРАВКЕ => ПРОВЕРЬТЕ СЕТЕВЫЕ НАСТРОЙКИ\n" + e);
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
        akt.onapproved = new OnApproved() {
            @Override
            public void passed() throws IOException, InterruptedException {
                new ThreadAlert().start();
                PositionTable.setEnabled(true);
                SaveChanges.setEnabled(true);
            }
        };

    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, InterruptedException {
        Example2 ex = new Example2();
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

    public class TableModelSpezial extends DefaultTableModel {

        private static final long serialVersionUID = 1L;


        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true; //falls Änderung auf eine Zeile beschränkt wird, zb 1. dann columnIndex==0;
        }


        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

            System.out.println(aValue);
            System.out.println(rowIndex);
            System.out.println(columnIndex);

            fireTableCellUpdated(rowIndex, columnIndex);

        }

        public String getColumnName(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return "Дата";
                case 1:
                    return "Время";
                case 2:
                    return "Накладная №";
                case 3:
                    return "Комментарий";
                case 4:
                    return "Металл";
                case 5:
                    return "Брутто";
                case 6:
                    return "Тара";
                case 7:
                    return "Засор";
            }
            return "";

        }
        // {"Дата", "Время", "Накладная №", "Комментарий", "Металл", "Брутто", "Тара", "Засор", "Примеси", "Нетто", "Режим", "Завершено", "Состояние"}
    }

};
