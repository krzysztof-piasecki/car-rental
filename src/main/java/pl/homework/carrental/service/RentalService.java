package pl.homework.carrental.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.homework.carrental.model.Car;
import pl.homework.carrental.model.Rental;
import pl.homework.carrental.model.Rentee;
import pl.homework.carrental.repository.RentalRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class RentalService {

    @Autowired
    RentalRepository rentalRepository;
    public void rentACar (Car car, Rentee rentee){
        Rental rental = new Rental();

        car.setAvailable(false);
        rental.setCar(car);
        rental.setRentee(rentee);
        rental.setStartRentalDate(Date.valueOf(LocalDate.now()));
        rentalRepository.save(rental);
    }

    public void returnACar (Rental rental){
        rental.setEndRentalDate(Date.valueOf(LocalDate.now()));
        rental.getCar().setAvailable(true);
        rentalRepository.save(rental);
    }
    public void deleteRental (Rental rental){
        rentalRepository.delete(rental);
    }

    public Optional<Rental> getRentalById (long id) {
        return rentalRepository.findById(id);
    }
}
