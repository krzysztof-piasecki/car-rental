package pl.homework.carrental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.homework.carrental.model.Car;
import pl.homework.carrental.model.Rental;
import pl.homework.carrental.model.Rentee;
import pl.homework.carrental.service.CarService;
import pl.homework.carrental.service.RentalService;
import pl.homework.carrental.service.RenteeService;

import java.util.Optional;


@RestController
public class RentalController {

    private static final String RENT_NOT_FOUND = "Rental not found";
    private static final String RENTEE_NOT_FOUND = "Rentee not found";
    private static final String CAR_IS_NOT_AVAILABLE = "Car is not available";

    @Autowired
    private RentalService rentalService;
    @Autowired
    private CarService carService;
    @Autowired
    private RenteeService renteeService;

    @GetMapping(path = "rent:{id}")
    public Rental getRentById(@PathVariable Long id){
        Optional<Rental> rental = rentalService.getRentalById(id);
        checkIfEmpty(rental);
        return rental.get();
    }

    @PostMapping(path = "rent:{carId}/rentee:{renteeId}")
    public void rentACar (@PathVariable long carId, @PathVariable long renteeId){
        Optional<Car> car = carService.getCarById(carId);
        Optional<Rentee> rentee = renteeService.getRenteeById(renteeId);
        checkIfEmpty(car);
        if(!car.get().isAvailable()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, CAR_IS_NOT_AVAILABLE
            );
        }
        if(rentee.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, RENTEE_NOT_FOUND
            );
        }
        rentalService.rentACar(car.get(), rentee.get());
    }

    @PostMapping(path = "return:{id}")
    public void returnACar (@PathVariable long id){
        Optional<Rental> rental = rentalService.getRentalById(id);
        checkIfEmpty(rental);
        rentalService.returnACar(rental.get());
    }

    @DeleteMapping(path = "rent:{id}")
    public void deleteCarById (@PathVariable long id){
        Optional<Rental> rental = rentalService.getRentalById(id);
        checkIfEmpty(rental);
        rentalService.deleteRental(rental.get());
    }

    private void checkIfEmpty(Optional<?> optional){
        if (optional.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, RENT_NOT_FOUND
            );
        }
    }
}
