package pl.homework.carrental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.homework.carrental.model.Car;
import pl.homework.carrental.model.Rental;
import pl.homework.carrental.service.CarService;
import pl.homework.carrental.service.RentalService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
public class RentalController {

    private static final String RENTNOTFOUND = "Item not found";

    @Autowired
    private RentalService rentalService;
    @Autowired
    private CarService carService;

    @GetMapping(path = "rent:{id}")
    public Rental getRentById(@PathVariable Long id){
        Optional<Rental> rental = rentalService.getRentalById(id);
        if (rental.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, RENTNOTFOUND
            );
        }
        return rental.get();
    }

    @PostMapping("rent:{carId}")
    public void rentACar (@PathVariable long carId){
        Optional<Car> car = carService.getCarById(carId);
        if (car.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, RENTNOTFOUND
            );
        }
        rentalService.rentACar(car.get());
    }

    @PostMapping("return:{id}")
    public void returnACar (@PathVariable long id){
        Optional<Rental> rental = rentalService.getRentalById(id);
        if (rental.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, RENTNOTFOUND
            );
        }
        rentalService.returnACar(rental.get());
    }

    @DeleteMapping("rent:{id}")
    public void deleteCarById (@PathVariable long id){
        Optional<Rental> rental = rentalService.getRentalById(id);
        if (rental.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, RENTNOTFOUND
            );
        }else{
            rentalService.deleteRental(rental.get());
        }
    }

    @GetMapping("rental/in-progress")
    public ArrayList<Rental> getAllRentalsInProgress(){
        ArrayList<Rental> takenCars = (ArrayList<Rental>) rentalService.getAllRentalsInProgress();
        return takenCars;
    }
}
