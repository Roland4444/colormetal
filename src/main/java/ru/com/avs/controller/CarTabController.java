package ru.com.avs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ru.com.avs.model.Car;
import ru.com.avs.service.CarService;
import ru.com.avs.view.CustomWindowEvent;

@Component("CarController")
public class CarTabController extends AbstractController {

    @FXML
    private Button btnDelete;
    @FXML
    private TableView<Car> carsTable;
    @FXML
    private TableColumn<Car, Integer> idCarColumn;
    @FXML
    private TableColumn<Car, String> nameCarColumn;
    @FXML
    private TableColumn<Car, String> weightCarColumn;

    @Autowired
    private CarService carService;

    private Car car;
    private String carEditForm = "settings/carEditForm";

    /**
     * Initialize.
     */
    public void initialize() {
        refreshCarsData();
        idCarColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCarColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        weightCarColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        carsTable.setEditable(true);
    }

    @FXML
    private void onCarEditStart() {
        Car car = carsTable.getSelectionModel().getSelectedItem();
        CarEditController controller =
                (CarEditController) runController(carEditForm, "Редактирование справочника", true);

        controller.initData(car);
        Stage stage = controller.getStage();
        stage.setOnCloseRequest(we -> {
            refreshCarsData();
        });
    }

    @FXML
    private void onSelectCar() {
        car = carsTable.getSelectionModel().getSelectedItem();
        if (car != null) {
            btnDelete.setDisable(false);
        }
    }

   /* @FXML
    private void deleteCar() {
        if (car != null) {
            carService.delete(car);
            btnDelete.setDisable(true);
            refreshCarsData();
        }
    }*/

    @FXML
    private void addCar() {
        CarEditController controller =
                (CarEditController) runController(carEditForm, "Редактирование справочника", true);
        controller.initData(null);
        Stage stage = controller.getStage();
        stage.setOnCloseRequest(we -> {
            if (we.getEventType() == CustomWindowEvent.SAVE) {
                alert("Успешно сохранено", "Успешно сохранено", Alert.AlertType.INFORMATION);
                refreshCarsData();
            }
        });
    }

    private void refreshCarsData() {
        ObservableList<Car> carData = FXCollections.observableArrayList();
        carsTable.getItems().clear();

        List<Car> cars = carService.getList();
        for (Car car : cars) {
            carData.add(car);
        }
        carsTable.setItems(carData);
    }
}
