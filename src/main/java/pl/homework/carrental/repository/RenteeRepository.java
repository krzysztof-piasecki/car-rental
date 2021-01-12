package pl.homework.carrental.repository;

import org.springframework.data.repository.CrudRepository;
import pl.homework.carrental.model.Rentee;

public interface RenteeRepository extends CrudRepository<Rentee, Long> {
}
