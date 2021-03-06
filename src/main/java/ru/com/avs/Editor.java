package ru.com.avs;
import ch.roland.ModuleGUI;
import ru.com.avs.util.Callback;
import ru.com.avs.util.Checker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Editor extends ModuleGUI {
    public Checker checker;
    public Callback callback;
    public JPanel CommentPanel, CommentLabelPanel, CommentTextPanel,
            BruttoPanel, BruttoLabelPanel, BruttoTextPanel,
            NettoPanel, NettoLabelPanel, NettoTextPanel,
            CloggingPanel, CloggingLabelPanel, CloggingTextPanel,
            TrashPanel, TrashLabelPanel, TrashTextPanel,
            TaraPanel, TaraLabelPanel, TaraTextPanel,
            MetalPanel, MetalLabelPanel, MetalItemPanel;
    public JTextField Comment;
    public JLabel Ldata;
    public JLabel LComment;
    public JTextField Brutto;
    public JLabel LBrutto;
    public JTextField Netto;
    public JLabel LNetto;
    public JTextField Clogging;
    public JLabel LClogging;
    public JTextField Trash;
    public JLabel LTrash;
    public JTextField Tara;
    public JLabel LTara;
    public JComboBox Metal;
    public JLabel LMetal;
    public JButton UpdateComment;
    public JPanel containerPanel;
    public JTable positiontable;
    AbstractAction updateAction;
    public String updateaction = "checkaction";
    JButton Save;
    public JLabel description;
    public JPanel buttonPanel;
    public String updateaction_shortcut = "control Z";
    public ArrayList inputdata;
    private String[] data1 = { "Чай" ,"Кофе"  ,"Минеральная","Морс", "Алюминий хлам"};
    public ArrayList metals;
    public JButton saver;
    public String errorDescription;
    public Editor(String number, String date, ArrayList data1, JButton saver){
        checker = new Checker();
        this.saver = saver;
        saver.setEnabled(true);
        CommentPanel = new JPanel(new GridLayout());
        CommentLabelPanel = new JPanel(new BorderLayout());
        CommentTextPanel = new JPanel(new BorderLayout());

        BruttoPanel= new JPanel(new GridLayout());
        BruttoLabelPanel = new JPanel(new BorderLayout());
        BruttoTextPanel = new JPanel(new BorderLayout());

        NettoPanel= new JPanel(new GridLayout());
        NettoLabelPanel = new JPanel(new BorderLayout());
        NettoTextPanel = new JPanel(new BorderLayout());

        CloggingPanel= new JPanel(new GridLayout());
        CloggingLabelPanel = new JPanel(new BorderLayout());
        CloggingTextPanel = new JPanel(new BorderLayout());

        TaraPanel= new JPanel(new GridLayout());
        TaraLabelPanel = new JPanel(new BorderLayout());
        TaraTextPanel = new JPanel(new BorderLayout());

        TrashPanel= new JPanel(new GridLayout());
        TrashLabelPanel = new JPanel(new BorderLayout());
        TrashTextPanel = new JPanel(new BorderLayout());

        MetalPanel = new JPanel(new GridLayout());
        MetalLabelPanel = new JPanel(new BorderLayout());
        MetalItemPanel = new JPanel(new BorderLayout());

        buttonPanel =  new JPanel();

        frame = new JFrame("Накладная номер #"+ number+ " @"+ date);
        Comment = new JTextField("",15);
        LComment = new JLabel("Комментарий   ");
        UpdateComment = new JButton("Обновить");
        containerPanel = new JPanel();
        description = new JLabel("Накладная номер #"+ number+ " @"+ date);
        GridLayout gr = new GridLayout(12,1);
        gr.setHgap(2);
        gr.setVgap(2);
        containerPanel.setLayout(gr);
        LBrutto = new JLabel("Брутто   ");
        String[] array = new String[data1.size()];
        for (int i=0; i<data1.size(); i++){
            array[i]= (String) data1.get(i);
        }
        Metal = new JComboBox(array);
        Brutto = new JTextField("",15);
        Netto = new JTextField("",15);
        LNetto = new JLabel("Нетто   ");
        Clogging = new JTextField("",15);
        LClogging  = new JLabel("Засор   ");
        Trash = new JTextField("",15);
        LTrash = new JLabel("Примеси   ");
        Tara = new JTextField("",15);
        LTara = new JLabel("Тара   ");
        LMetal = new JLabel("Металл   ");

        metals = data1;
        Save = new JButton("Сохранить");
     //   springLayoutPanel = new JPanel(new SpringLayout());

    }

    public Editor(String[] data){
        frame = new JFrame();
        Comment = new JTextField();
        LComment = new JLabel("Комментарий");
        UpdateComment = new JButton("Обновить");
        containerPanel = new JPanel();
        Metal = new JComboBox(data);
    }


    @Override
    public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        CommentTextPanel.add(Comment, BorderLayout.WEST);
        CommentLabelPanel.add(LComment, BorderLayout.EAST);
        CommentPanel.add(CommentLabelPanel);
        CommentPanel.add(CommentTextPanel);

        BruttoLabelPanel.add(LBrutto, BorderLayout.EAST);
        BruttoTextPanel.add(Brutto, BorderLayout.WEST);
        BruttoPanel.add(BruttoLabelPanel);
        BruttoPanel.add(BruttoTextPanel);

        NettoLabelPanel.add(LNetto, BorderLayout.EAST);
        NettoTextPanel.add(Netto, BorderLayout.WEST);
        NettoPanel.add(NettoLabelPanel);
        NettoPanel.add(NettoTextPanel);

        CloggingLabelPanel.add(LClogging, BorderLayout.EAST);
        CloggingTextPanel.add(Clogging, BorderLayout.WEST);
        CloggingPanel.add(CloggingLabelPanel);
        CloggingPanel.add(CloggingTextPanel);

        TrashLabelPanel.add(LTrash, BorderLayout.EAST);
        TrashTextPanel.add(Trash, BorderLayout.WEST);
        TrashPanel.add(TrashLabelPanel);
        TrashPanel.add(TrashTextPanel);

        TaraLabelPanel.add(LTara, BorderLayout.EAST);
        TaraTextPanel.add(Tara, BorderLayout.WEST);
        TaraPanel.add(TaraLabelPanel);
        TaraPanel.add(TaraTextPanel);

        MetalLabelPanel.add(LMetal, BorderLayout.EAST);
        MetalItemPanel.add(Metal, BorderLayout.WEST);
        MetalPanel.add(MetalLabelPanel);
        MetalPanel.add(MetalItemPanel);

        buttonPanel.add(Save);

        containerPanel.add(CommentPanel);
        containerPanel.add(BruttoPanel);
        containerPanel.add(NettoPanel);
        containerPanel.add(CloggingPanel);
        containerPanel.add(TrashPanel);
        containerPanel.add(TaraPanel);
        containerPanel.add(MetalPanel);

        containerPanel.add(buttonPanel);

        frame.setContentPane(containerPanel);
        frame.setSize(500, 450);
        frame.setVisible(true);
        initActions();
    }

    @Override
    public void initListeners() {
        UpdateComment.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(updateaction_shortcut), updateaction);
        UpdateComment.getActionMap().put(updateaction, updateAction);
        UpdateComment.addActionListener(updateAction);

        Save.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(updateaction_shortcut), updateaction);
        Save.getActionMap().put(updateaction, updateAction);
        Save.addActionListener(updateAction);

    }

    public void pasteData(){
        Comment.setText(inputdata.get(0).toString());
        Brutto.setText(inputdata.get(1).toString());
        Netto.setText(inputdata.get(2).toString());
        Clogging.setText(inputdata.get(3).toString());
        Trash.setText(inputdata.get(4).toString());
        Tara.setText(inputdata.get(5).toString());
        Metal.setSelectedItem(inputdata.get(6).toString());
    };

    public boolean checkInput(){
        if (!checker.isnumber(Brutto.getText())) {
            errorDescription = "Брутто";
            return false;
        }
        if (!checker.isnumber(Tara.getText())) {
            errorDescription = "Тара";
            return false;
        }
        if (!checker.isnumber(Clogging.getText())) {
            errorDescription = "Засор";
            return false;
        }
        if (!checker.isnumber(Trash.getText())) {
            errorDescription = "Примесь";
            return false;
        }
        if (!checker.isnumber(Netto.getText())) {
            errorDescription = "Нетто";
            return false;
        }
        return true;
    };

    public void update(){
        if (!checkInput()) {
            JOptionPane.showMessageDialog(null, "Проверьте ввод чисел. Ошибка в поле "+errorDescription);
            return;
        }
        positiontable.setValueAt(Comment.getText(), 0, 3);
        positiontable.setValueAt(Metal.getSelectedItem(), 0, 4);
        positiontable.setValueAt(Brutto.getText(), 0, 5);
        positiontable.setValueAt(Tara.getText(), 0, 6);
        positiontable.setValueAt(Clogging.getText(), 0, 7);
        positiontable.setValueAt(Trash.getText(), 0, 8);
        positiontable.setValueAt(Netto.getText(), 0, 9);
        positiontable.updateUI();
        JOptionPane.showMessageDialog(null, "Сохраняю измнения");
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        callback.call();
    }

    @Override
    public void initActions() {
        updateAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            //    JOptionPane.showMessageDialog(null, "NETTO::"+Netto.getText());
            update();

            }
        };

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println("Hiding window!!!!");
                frame.setVisible(false);
            }
        });

        initListeners();
    }
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        new Editor("  3   ", "  26.11.2020   ", null, null).preperaGUI();
    }
}
