package ru.com.avs.util;

import javafx.scene.control.Alert;
import ru.com.avs.Example;
import ru.com.avs.controller.WaybillJournalController;
import ru.com.avs.model.WeighingView;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static ru.com.avs.controller.WaybillJournalController.FileNameDump;

public class ExampleTask  extends javafx.concurrent.Task {
    public WaybillJournalController controller;
    @Override
    protected Object call() throws Exception {
        lambda();
        return null;
    };

    void lambda() throws IOException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        WeighingView selectedwaybill = controller.getTable().getSelectionModel().getSelectedItem();
        FileOutputStream fos = new FileOutputStream(FileNameDump);
        fos.write(WayBillUtil.saveWayBillToBytes(selectedwaybill));
        fos.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("WrittenTo=>"+FileNameDump);

        alert.showAndWait();
        new Example().preperaGUI();
    }


}
