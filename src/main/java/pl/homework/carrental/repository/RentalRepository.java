package pl.homework.carrental.repository;

import org.springframework.data.repository.CrudRepository;
import pl.homework.carrental.model.Rental;

public interface RentalRepository extends CrudRepository <Rental, Long> {
}
