package ru.com.avs;

import ch.roland.ModuleGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Editor extends ModuleGUI {
    public JTextField Comment;
    public JLabel LComment;
    public JButton UpdateComment;
    public JPanel containerPanel;
    public JTable positiontable;
    AbstractAction updateAction;
    public String updateaction = "checkaction";
    public String updateaction_shortcut = "control Z";
    public Editor(){
        frame = new JFrame();
        Comment = new JTextField();
        LComment = new JLabel("Комментарий");
        UpdateComment = new JButton("Обновить");
        containerPanel = new JPanel();

    }

    @Override
    public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.PAGE_AXIS));
        containerPanel.add(LComment);
        containerPanel.add(Comment);
        containerPanel.add(UpdateComment);


        frame.setContentPane(containerPanel);
        frame.setSize(500, 500);
        frame.setVisible(true);
        initActions();

    }

    @Override
    public void initListeners() {
        UpdateComment.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(updateaction_shortcut), updateaction);
        UpdateComment.getActionMap().put(updateaction, updateAction);
        UpdateComment.addActionListener(updateAction);

    }

    @Override
    public void initActions() {
        updateAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Сохраняю измнения");
                positiontable.setValueAt(Comment.getText(), 0, 4);
                positiontable.updateUI();
            }
        };
        initListeners();
    }
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        new Editor().preperaGUI();
    }
}
