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
    @Autowired
    CarService carService;
    public void rentACar (Car car, Rentee rentee){
        Rental rental = new Rental();

        car.setAvailable(false);
        carService.saveCar(car);

        rental.setCar(car);
        rental.setRentee(rentee);
        rental.setStartRentalDate(Date.valueOf(LocalDate.now()));
        rentalRepository.save(rental);
    }

    public void returnACar (Rental rental){
        rental.setEndRentalDate(Date.valueOf(LocalDate.now()));
        Car car = rental.getCar();
        car.setAvailable(true);
        carService.saveCar(car);
        rentalRepository.save(rental);
    }

    public Optional<Rental> getRentalById (long id) {
        return rentalRepository.findById(id);
    }

    public Iterable<Rental> getAllRental(){return rentalRepository.findAll();}

    public void deleteAllRental() {rentalRepository.deleteAll();}
}
