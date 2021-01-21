package ru.com.avs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import ru.com.avs.dao.CarDao;
import ru.com.avs.model.Car;

@Service("CarService")
@Transactional
public class CarServiceImpl implements CarService {

    @Autowired
    private CarDao carDao;

    @Override
    public List<Car> getList() {
        return carDao.getList();
    }

   /* @Override
    public void delete(Car car) {
        car.setDeleted(true);
        carDao.update(car);
    }*/

    @Override
    public void save(Car car) {
        if (car.getId() == 0) {
            carDao.save(car);
        } else {
            carDao.update(car);
        }
    }

    @Override
    public Car getCarById(int id) {
        return carDao.get(id);
    }

    @Override
    public Car getCarByIdFromList(List<Car> cars, int id) {
        return cars.stream()
                .filter(car -> id == car.getId())
                .findFirst()
                .orElse(null);
    }
}
