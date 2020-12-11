package ru.com.avs.controller;

import static ru.com.avs.util.UserUtils.fileExists;
import static ru.com.avs.util.UserUtils.getImage;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.com.avs.model.Metal;
import ru.com.avs.model.Mode;
import ru.com.avs.model.Tare;
import ru.com.avs.model.WeighingView;
import ru.com.avs.model.Waybill;
import ru.com.avs.model.Weighing;
import ru.com.avs.service.AuthService;
import ru.com.avs.service.MetalService;
import ru.com.avs.service.ModeService;
import ru.com.avs.service.PropertyService;
import ru.com.avs.service.WaybillService;
import ru.com.avs.service.WeighingService;
import ru.com.avs.util.CmdRunner;
import ru.com.avs.util.ExampleTask;
import ru.com.avs.util.WayBillUtil;

import javax.swing.*;

@Component("WaybillJournalController")
public class WaybillJournalController extends AbstractController {

    public TableColumn<WeighingView, LocalDate> dateColumn;
    public TableColumn<WeighingView, LocalTime> timeColumn;
    public TableColumn<WeighingView, Integer> waybillColumn;
    public TableColumn<WeighingView, Metal> metalColumn;
    public TableColumn<WeighingView, String> commentColumn;
    public TableColumn<WeighingView, String> bruttoColumn;
    public TableColumn<WeighingView, Tare> tareColumn;
    public TableColumn<WeighingView, String> nettoColumn;
    public TableColumn<WeighingView, String> cloggingColumn;
    public TableColumn<WeighingView, String> trashColumn;
    public TableColumn<WeighingView, Boolean> modeColumn;
    public TableColumn<WeighingView, Boolean> completeColumn;
    public TableColumn<WeighingView, String> stateColumn;
    public static final String FileNameDump  = "waybill.bin";

    public TableView<WeighingView> getTable(){
        return waybillTable;
    }

    private WeighingView viewModel;
    @FXML
    private TableView<WeighingView> waybillTable;
    @FXML
    private DatePicker dateStart;
    @FXML
    private DatePicker dateEnd;
    @FXML
    private TextField commentFilter;
    @FXML
    private ImageView preview;

    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button exportButton;
    @FXML
    private Button printButton;

    @Autowired
    private PropertyService property;

    @Autowired
    private AuthService authService;

    @Autowired
    private WaybillService waybillService;

    @Autowired
    private WeighingService weighingService;

    @Autowired
    private MetalService metalService;

    @Autowired
    private ModeService modeService;

    /**
     * init.
     */
    public void initialize() {
        dateStart.setValue(LocalDate.now());
        dateEnd.setValue(LocalDate.now());
        adminMode(authService.isAdminMode());

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateCreate"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("timeCreate"));
        waybillColumn.setCellValueFactory(new PropertyValueFactory<>("waybill"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
        metalColumn.setCellValueFactory(new PropertyValueFactory<>("metal"));
        bruttoColumn.setCellValueFactory(new PropertyValueFactory<>("brutto"));
        tareColumn.setCellValueFactory(new PropertyValueFactory<>("tare"));
        nettoColumn.setCellValueFactory(new PropertyValueFactory<>("netto"));
        cloggingColumn.setCellValueFactory(new PropertyValueFactory<>("clogging"));
        trashColumn.setCellValueFactory(new PropertyValueFactory<>("trash"));
        modeColumn.setCellValueFactory(new PropertyValueFactory<>("mode"));
        completeColumn.setCellValueFactory(new PropertyValueFactory<>("complete"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("upload"));

        refreshData();
        waybillTable.setEditable(true);
    }

    @FXML
    private void isSelect() {
        viewModel = waybillTable.getSelectionModel().getSelectedItem();
        if (viewModel != null) {
            String imgPath = property.getProperty("image.path")
                    + viewModel.getDateCreate() + "/previewOur-" + viewModel.getWeighingId() + ".jpg";

            if (!fileExists(imgPath)) {
                imgPath = property.getProperty("image.path")
                        + viewModel.getDateCreate() + "/preview-" + viewModel.getWeighingId() + ".jpg";
            }
            Image image = getImage(imgPath);
            preview.setImage(image);
            deactivateButtons(false);
        }
    }

    @FXML
    private void search() {
        refreshData();
    }


    @FXML
    private void help() throws IOException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
       Alert alert = new Alert(Alert.AlertType.INFORMATION);

        WeighingView selectedwaybill = waybillTable.getSelectionModel().getSelectedItem();
        FileOutputStream fos = new FileOutputStream(FileNameDump);
        fos.write(WayBillUtil.saveWayBillToBytes(selectedwaybill));
        fos.close();

        alert = new Alert(Alert.AlertType.INFORMATION);
    //    alert.setTitle("Information Dialog");
    //    alert.setHeaderText("Trying run=>"+FileNameDump);

    //    alert.showAndWait();
    //   new Example().preperaGUI();

        new CmdRunner().run();
    }

    @FXML
    private void edit() {
        if (viewModel != null) {
            WaybillEditController controller =
                    (WaybillEditController) runController("waybillEditForm", "Редактирование сделки", true);
            controller.initData(viewModel);
            Stage stage = controller.getStage();
            stage.setOnCloseRequest(we -> {
                refreshData();
                deactivateButtons(true);
            });
        } else {
            alert("Не выбрано взвешивание", "Ошибка", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void delete() {

        if (viewModel != null) {
            Optional<ButtonType> option = dialog("Удалить выбранное взвешивание?",
                    "Удаление", Alert.AlertType.CONFIRMATION);

            if (option.get() == ButtonType.OK) {
                Weighing weighing = weighingService.getById(viewModel.getWeighingId());

                if (weighing.getWaybill().getWeighings().size() == 1) {
                    waybillService.delete(weighing.getWaybill());
                } else {
                    weighingService.delete(weighing);
                }
                refreshData();
            }
            deactivateButtons(true);
        } else {
            alert("Не выбрано взвешивание", "Ошибка", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void export() {
        Waybill waybill = waybillService.getById(viewModel.getWaybillId());
        if (waybill.isComplete()) {
            if (waybillService.exportWaybill(waybill)) {
                alert("Экспортировано", "Ок", Alert.AlertType.INFORMATION);
            } else {
                alert("Произошла ошибка", "Ошибка", Alert.AlertType.ERROR);
            }
        } else {
            alert("Невозможно экспортировать\n" +
                    "Сделка еще не завершена!", "Ошибка", Alert.AlertType.ERROR);
        }
        deactivateButtons(true);
    }

    @FXML
    private void print() {
        Waybill waybill = waybillService.getById(viewModel.getWaybillId());
        if (waybill.isComplete()) {
            PrintController controller =
                    (PrintController) runController("printForm", "Чек", true);

            controller.initData(waybill, false);
            Stage stage = controller.getStage();
            stage.setOnCloseRequest(we -> {
                deactivateButtons(true);
            });
        } else {
            alert("Невозможно напечатаь чек\n" +
                    "Сделка еще не завершена!", "Ошибка", Alert.AlertType.ERROR);
        }
    }

    private void adminMode(boolean enable) {
        editButton.setVisible(enable);
        deleteButton.setVisible(enable);
        deactivateButtons(true);
    }

    private void deactivateButtons(boolean activate) {
        editButton.setDisable(activate);
        deleteButton.setDisable(activate);
        exportButton.setDisable(activate);
        printButton.setDisable(activate);
    }

    private void refreshData() {
        ObservableList<WeighingView> data = FXCollections.observableArrayList();
        waybillTable.getItems().clear();
        List<Waybill> waybills =
                waybillService.search(dateStart.getValue(), dateEnd.getValue(), commentFilter.getText());
        List<WeighingView> viewModels = createModel(waybills);
        for (WeighingView viewModel : viewModels) {
            data.add(viewModel);
        }
        waybillTable.setItems(data);
    }

    private List<WeighingView> createModel(List<Waybill> waybills) {
        List<WeighingView> viewModels = new ArrayList<>();
        for (Waybill waybill : waybills) {

            List<Weighing> weighings = waybill.getWeighings();

            List<Mode> modes = modeService.getList();
            for (Weighing weighing : weighings) {
                WeighingView viewModel = new WeighingView();
                viewModel.setWaybillId(waybill.getId());
                viewModel.setDateCreate(waybill.getDateCreate());
                viewModel.setTimeCreate(waybill.getTimeCreate());
                viewModel.setWaybill(waybill.getWaybill());
                Metal metal = metalService.find(weighing.getMetal());
                viewModel.setMetal(metal);
                viewModel.setComment(waybill.getComment());
                viewModel.setUpload(waybill.isUpload() ? "Выгружен" : "Не выгружен");
                viewModel.setComplete(waybill.isComplete() ? "Да" : "Нет");

                Mode mode = modeService.getByCode(modes, waybill.getMode());
                viewModel.setMode(mode.getName());
                viewModel.setWeighingId(weighing.getId());
                viewModel.setBrutto(weighing.getBrutto());
                viewModel.setNetto(weighing.getNetto());
                viewModel.setTrash(weighing.getTrash());
                viewModel.setClogging(weighing.getClogging());
                viewModel.setTare(weighing.getTare());
                viewModels.add(viewModel);
            }
        }
        return viewModels;
    }
}
