package pl.homework.carrental.repository;

import org.springframework.data.repository.CrudRepository;
import pl.homework.carrental.model.Car;

public interface CarRepository extends CrudRepository <Car, Long> {
}
