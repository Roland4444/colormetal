package ru.com.avs.controller;

import java.awt.*;
import java.util.Arrays;
import java.util.List;


import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.com.avs.model.Property;
import ru.com.avs.service.PropertyService;

@Component
public class PropertyEditController extends AbstractController {

    private Property property;

    @FXML
    private TextField name;
    @FXML
    private TextField value;

    @Autowired
    private PropertyService service;

    /**
     * Initialisation.
     *
     * @param property Property
     */
    void initData(Property property) {
        this.property = new Property();
        name.setText(property.getName());
        value.setText(property.getValue());

        if (property != null) {
            this.property = property;
            List<String> ignored = Arrays.asList(service.getProperty("ignore.prop").split(","));
            if (ignored.contains(property.getName())) {
                this.value.setEnabled(false);
            }
        }
    }

    /**
     * Save or update Property.
     */
    @FXML
    public void save() {
        this.property.setValue(value.getText());
        this.property.setName(name.getText());

        if (property.getId() != 0) {
            service.update(property);
        } else {
            service.save(property);
        }

        super.save();
    }
}
