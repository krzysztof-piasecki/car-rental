package pl.homework.carrental.repository;

import org.springframework.data.repository.CrudRepository;
import pl.homework.carrental.model.Car;

import java.util.List;

public interface CarRepository extends CrudRepository <Car, Long> {
    List<Car> findByIsAvailableIsTrue();

}
