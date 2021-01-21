package ru.com.avs.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.com.avs.model.Car;


import java.util.List;


@Repository("CarDao")
@Transactional
public class CarDaoImpl extends DaoImpl<Car> implements CarDao {

    public CarDaoImpl() {
        super(Car.class);
    }

    @Override
    public List<Car> getList() {
        Criteria criteria = getSession().createCriteria(Car.class);
        criteria.add(Restrictions.eq("deleted", false));
        return criteria.list();
    }

    @Override
    public Car get(int id) {
        Criteria criteria = getSession().createCriteria(Car.class);
        criteria.add(Restrictions.eq("id", id));
        criteria.add(Restrictions.eq("deleted", false));
        return (Car) criteria.uniqueResult();
    }
}
