package ru.com.avs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ru.com.avs.model.Car;
import ru.com.avs.service.CarService;
import ru.com.avs.view.DecimalField;

@Component("CarEditController")
public class CarEditController extends AbstractController {

    private Car car;


    @FXML
    private TextField name;
    @FXML
    private DecimalField weight;

    @Autowired
    private CarService carService;

    void initData(Car car) {
        this.car = new Car();

        if (car != null) {
            this.car = car;
            this.name.textProperty().setValue(car.getName());
            this.weight.textProperty().setValue(String.valueOf(car.getWeight()));
        }
    }

    /**
     * Save or edit Car.
     */
    @FXML
    public void save() {
        this.car.setName(name.getText());
        this.car.setWeight(weight.getDecimal());
        carService.save(car);
        super.save();
    }
}
