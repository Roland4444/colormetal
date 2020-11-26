package ru.com.avs;


/**
 * Created by md on 5.04.2016.
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import javafx.util.StringConverter;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JDatapiker extends Application {

    public static void main(String[] args) {
        // launch(args);
        launchSwing();
    }

    private static void launchSwing() {
        SwingUtilities.invokeLater(() -> {
            JFrame         frame   = new JFrame("Swing and JavaFX");
            final JFXPanel fxPanel = new JFXPanel();
            frame.add(fxPanel);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Platform.runLater(() -> fxPanel.setScene(createScene()));
        });
    }

    private static Scene createScene() {
        Button                    addButton     = new Button("+");
        Button                    removeButton  = new Button("-");
        ObservableList<LocalDate> selectedDates = FXCollections.observableArrayList();
        ListView<LocalDate>       dateList      = new ListView<>(selectedDates);
        String                    pattern       = "yyyy-MM-dd";
        DateTimeFormatter         dateFormatter = DateTimeFormatter.ofPattern(pattern);
        DatePicker                datePicker    = new DatePicker();
        datePicker.setShowWeekNumbers(true);
        datePicker.setOnAction(event -> System.out.println("Selected date: " + datePicker.getValue()));
        datePicker.setPromptText(pattern);
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return (date == null) ? "" : dateFormatter.format(date);
            }

            @Override
            public LocalDate fromString(String string) {
                return ((string == null) || string.isEmpty()) ? null : LocalDate.parse(string, dateFormatter);
            }
        });
        datePicker.setOnAction(event -> selectedDates.add(datePicker.getValue()));

        datePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        boolean alreadySelected = selectedDates.contains(item);
                        setDisable(alreadySelected);
                        setStyle(alreadySelected ? "-fx-background-color: #09a30f;" : "");
                    }
                };
            }
        });

        // TODO: Hide text field of the date picker combo. Show dropdown directly on clicking "+" button.
        // TODO: Keep dropdown of the date picker combo open until intentionally clicking some other where.

        dateList.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DELETE) {
                removeSelectedDates(selectedDates, dateList);
            }
        });
        dateList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        removeButton.disableProperty().bind(dateList.getSelectionModel().selectedItemProperty().isNull());
        addButton.setOnAction(event -> {
            Popup popup = new Popup();
            popup.getContent().add(datePicker);
            popup.setAutoHide(true);
            Window window = addButton.getScene().getWindow();
            Bounds bounds = addButton.localToScene(addButton.getBoundsInLocal());
            double x      = window.getX() + bounds.getMinX();
            double y      = window.getY() + bounds.getMinY() + bounds.getHeight() + 5;
            popup.show(addButton, x, y);
            datePicker.show();
        });
        removeButton.setOnAction(event -> removeSelectedDates(selectedDates, dateList));

        HBox buttons = new HBox(addButton, removeButton);
        return new Scene(new VBox(buttons, dateList));
    }

    private static boolean removeSelectedDates(ObservableList<LocalDate> selectedDates, ListView<LocalDate> dateList) {
        return selectedDates.removeAll(dateList.getSelectionModel().getSelectedItems());
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(createScene());
        primaryStage.show();
    }
}