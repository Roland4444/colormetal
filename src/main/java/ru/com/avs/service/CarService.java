package ru.com.avs.service;

import java.util.List;

import ru.com.avs.model.Car;

public interface CarService {
    List<Car> getList();

    void save(Car car);

   /* void delete(Car car);*/

    Car getCarById(int id);

    Car getCarByIdFromList(List<Car> cars, int id);
}
