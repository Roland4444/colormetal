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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.CompletionException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import static javax.swing.JOptionPane.showMessageDialog;

public class Example2 extends  ModuleGUI {
    public final String version = "0.0.27";
    public final String approve_lock = "ap.lock";
    public final String applock = "app.lock";
    public final String req_lock = "request.lock";
    public ServerAktor akt;
    public String urlServer;
    AbstractAction createinitialrequest;
    AbstractAction saveChanges;
    AbstractAction editAction;
    public String checkaction = "checkaction";
    public String checkaction_shortcut = "control Z";
    public String createandsendfatbundle = "createfatbundle";
    public String createfatbundle_shortcut = "control R";

    public String savechanges = "saveChanges";
    public String savechanges_shortcut = "control S";
    public JPanel DescriptionPanel;
    public JTextArea DescriptionText;
    public JButton RequestHelp;
    public JButton Cancel;
    public JButton SaveChanges;
    public JButton EditButton;
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
    public ArrayList metals;

    FlowLayout experimentLayout;
    public Object[] columnsHeaderAVS = new String[]{"Дата", "Время", "Накладная №", "Комментарий", "Металл", "Брутто", "Тара", "Засор", "Примеси", "Нетто", "Режим", "Завершено", "Состояние"};

    public void defaultmetals(){
        metals = new ArrayList();
        metals.add("12А");
        metals.add("14А");
        metals.add("16А");
        metals.add("17А");
        metals.add("25А");
        metals.add("3А");
        metals.add("5А");
        metals.add("5АЖД");
        metals.add("5АНn");
        metals.add("5АЭ");
        metals.add("АКБ ГЕЛЬ");
        metals.add("АКБ никель-кадмиевые");
        metals.add("АКБ ПП");
        metals.add("АКБ ПП залитые");
        metals.add("АКБ ЭБ");
        metals.add("АЛЮМИНИЙ АД");
        metals.add("Алюминий моторный");
        metals.add("Алюминий пищевой");
        metals.add("Алюминий хлам");
        metals.add("Алюминий электротех");
        metals.add("Бабит 16%");
        metals.add("Бабит 70%");
        metals.add("Бабит 83%");
        metals.add("Бронза");
    }

    public void cleanup(){
        Utils.safeDelete(WaybillJournalController.FileNameDump);
        Utils.safeDelete(approve_lock);
        Utils.safeDelete(req_lock);
    }

    public Example2() throws IOException, InterruptedException {
        if (new File(applock).exists())
            return;
        FileOutputStream fos = new FileOutputStream(applock);
        fos.write("schon".getBytes());
        fos.close();
        Thread.sleep(300);
        defaultmetals();
        jsonizer = new JSONizer();
        readfile = new Readfile("setts.ini");
        urlServer = readfile.readField("urlServer");
        frame = new JFrame("АВС помошник. Версия "+version);
        WeighingView restored = null;
        try {
            restored = WayBillUtil.restoreBytesToWayBill(WaybillJournalController.FileNameDump);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PositionTable = new JTable(WayBillUtil.dataFromObject(restored), columnsHeaderAVS);
        //PositionTable = new JTable(new TableModelSpezial());


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
        SaveChanges = new JButton("Записать изменения");
        EditButton = new JButton("Редактировать");

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
    public void onProd(){
        SaveChanges.setEnabled(false);
        EditButton.setEnabled(false);
    };

    public void enableEdit(){
        SaveChanges.setEnabled(true);
      //  showMessageDialog(null, "включаю кнопку редактирования");
        EditButton.setEnabled(true);
    //    this.frame.setVisible(true);
    };
    public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        onProd();
        contents.add(lPosition);
        PositionTable.setRowHeight(40);

        pane.setMaximumSize(new Dimension(1300, 100));
        pane.setPreferredSize(new Dimension(1300, 100));
        pane.setMinimumSize(new Dimension(1300, 100));

        ButtonPanel.setLayout(experimentLayout);
        ButtonPanel.add(RequestHelp);
        ButtonPanel.add(Cancel);
        ButtonPanel.add(SaveChanges);
        ButtonPanel.add(EditButton);

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

        if (new File(req_lock).exists()) {
            showMessageDialog(null, "запрос уже создан");
            RequestHelp.setEnabled(false);
         //   enableEdit();
            onProd();
        }
        //    PositionTable.setEnabled(false);

        if (new File(approve_lock).exists()) {
            showMessageDialog(null, "Завершите предыдущий запрос");
            RequestHelp.setEnabled(false);
            enableEdit();
        }
    }

    public void initListeners() {
        RequestHelp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(createfatbundle_shortcut), createandsendfatbundle);
        RequestHelp.getActionMap().put(createandsendfatbundle, createinitialrequest);
        RequestHelp.addActionListener(createinitialrequest);

        SaveChanges.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(savechanges_shortcut), savechanges);
        SaveChanges.getActionMap().put(savechanges, saveChanges);
        SaveChanges.addActionListener(saveChanges);

        EditButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(checkaction_shortcut), checkaction);
        EditButton.getActionMap().put(checkaction, editAction);
        EditButton.addActionListener(editAction);

    }

    public void initActions() {
        editAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ////    PositionTable.getModel().setValueAt(DescriptionText.getText(), 0,3);
                PositionTable.updateUI();
                StringBuffer bf = new StringBuffer();
                for (int i = 0; i <= 12; i++) {
                    bf.append("Position #" + i + "data:: " + PositionTable.getModel().getValueAt(0, i) + "\n");
                }
                WeighingView restored = null;
                try {
                    restored = WayBillUtil.restoreBytesToWayBill(WaybillJournalController.FileNameDump);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            //    showMessageDialog(null, bf.toString());
                Editor editor = new Editor(String.valueOf(restored.getWaybill()), restored.getDateCreate().toString(), metals, SaveChanges);
                editor.positiontable = PositionTable;
                ArrayList data = new ArrayList<>();

                editor.inputdata = data;
                /*
                Comment.setText(inputdata.get(0).toString());
                Brutto.setText(inputdata.get(1).toString());;
                Netto.setText(inputdata.get(2).toString());;
                Clogging.setText(inputdata.get(3).toString());;
                Trash.setText(inputdata.get(4).toString());;
                Tara.setText(inputdata.get(5).toString());;
                */
                data.add(restored.getComment());
                data.add(restored.getBrutto());
                data.add(restored.getNetto());
                data.add(restored.getClogging());
                data.add(restored.getTrash());
                data.add(restored.getTare());
                data.add(restored.getMetal().getName());


                try {
                    editor.preperaGUI();
                    editor.pasteData();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (UnsupportedLookAndFeelException unsupportedLookAndFeelException) {
                    unsupportedLookAndFeelException.printStackTrace();
                } catch (InstantiationException instantiationException) {
                    instantiationException.printStackTrace();
                } catch (IllegalAccessException illegalAccessException) {
                    illegalAccessException.printStackTrace();
                }

            }
        };

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
                    cleanup();
                    showMessageDialog(null, "Транзакция завершена");
                 ///   frame.dispose();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            };
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
                //JOptionPane.showMessageDialog(null, "Отправляю на адрес:" + urlServer);
                //JOptionPane.showMessageDialog(null, "Отправляю запрос" );
                RequestMessage req = new RequestMessage(ID, DescriptionText.getText(), jsonizer.JSONedRestored(restored));

                System.out.println("\n\n\nJSON to send::" + req.JSONed);
                req.Description = DescriptionText.getText();
                req.type = RequestMessage.Type.request;
                try {
                    req.addressToReply = akt.getURL_thisAktor();
                } catch (UnknownHostException e) {
                    RequestHelp.setEnabled(true);;
                }
                try {
                    akt.send(BinaryMessage.savedToBLOB(req), urlServer);
                } catch (UnknownHostException e) {
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА ПРИ ОТПРАВКЕ => ПРОВЕРЬТЕ СЕТЕВЫЕ НАСТРОЙКИ\n" + e);
                    RequestHelp.setEnabled(true);
                } catch (IOException e) {
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА ПРИ ОТПРАВКЕ => ПРОВЕРЬТЕ СЕТЕВЫЕ НАСТРОЙКИ\n" + e);
                    RequestHelp.setEnabled(true);
                } catch (CompletionException e) {
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА ПРИ ОТПРАВКЕ => ПРОВЕРЬТЕ СЕТЕВЫЕ НАСТРОЙКИ\n" + e);
                    RequestHelp.setEnabled(true);
                }
                showMessageDialog(null, "запрос отправлен! ожидайте одобрения");
       //in production=>
                RequestHelp.setEnabled(false);


            }
        };
        initListeners();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
               Utils.safeDelete(applock);
               akt.terminate();
           //     frame.setVisible(false);
            }
        });
    }

    public void prepareAktor() throws InterruptedException {
        akt = new ServerAktor();
        akt.editButton = EditButton;
        akt.setAddress("http://127.0.0.1:12215/");
        akt.setCypher(new CypherImpl());
        System.out.println("\n\n\n*************************\n****Spawning JAKtor******\n*************************\n\n\n\n");

        akt.activateGUI =  new enableLambda() {
            @Override
            public void activate() {

            }
        };

        akt.ondeclined = new OnDeclined() {
            @Override
            public void declined() throws IOException, InterruptedException {
                cleanup();
                new ThreadAlertDecline().start();
            //    frame.setVisible(false);
            //    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

            }
        };
        akt.onapproved = new OnApproved() {
            @Override
            public void passed() throws IOException, InterruptedException {
                enableEdit();
                FileOutputStream fos = new FileOutputStream(approve_lock);
                fos.write("schon".getBytes());
                fos.close();
                new ThreadAlertApprove().start();

            }
        };
        akt.spawn();

    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, InterruptedException, IOException {
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
        private String[] columnNames = new String[]{"Дата", "Время", "Накладная №", "Комментарий", "Металл", "Брутто", "Тара", "Засор", "Примеси", "Нетто", "Режим", "Завершено", "Состояние"};
        WeighingView restored = WayBillUtil.restoreBytesToWayBill(WaybillJournalController.FileNameDump);

        private Object[][] data=WayBillUtil.dataFromObject(restored);

        public TableModelSpezial() throws IOException {
            WeighingView restored = null;
            try {
                restored = WayBillUtil.restoreBytesToWayBill(WaybillJournalController.FileNameDump);
            } catch (IOException e) {
                e.printStackTrace();
            }
            data =WayBillUtil.dataFromObject(restored);


            addRow(data[0]);

           // addRow(convertToVector(rowData));
        }

        @Override
        public int getRowCount() {
            return 0;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }




        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true; //falls Änderung auf eine Zeile beschränkt wird, zb 1. dann columnIndex==0;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return null;
        }


        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

            System.out.println(aValue);
            System.out.println(rowIndex);
            System.out.println(columnIndex);

            fireTableCellUpdated(rowIndex, columnIndex);

        }

        public String getColumnName(int columnIndex) {
            return columnNames[columnIndex];
        }
        // {"Дата", "Время", "Накладная №", "Комментарий", "Металл", "Брутто", "Тара", "Засор", "Примеси", "Нетто", "Режим", "Завершено", "Состояние"}
    }

};
