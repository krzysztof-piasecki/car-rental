package pl.homework.carrental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.homework.carrental.model.Car;
import pl.homework.carrental.service.CarService;

import java.util.List;
import java.util.Optional;


@RestController
public class CarController {

    private static final String CAR_NOT_FOUND = "Car not found";

    @Autowired
    private CarService carService;

    @GetMapping(path = "car:{id}")
    public Car getCarById(@PathVariable Long id){
        Optional<Car> car = carService.getCarById(id);
        checkIfEmpty(car);
        return car.get();
    }

    @PostMapping(path = "car")
    public void saveCar (@RequestBody Car car){
        carService.saveCar(car);
    }

    @DeleteMapping(path = "car:{id}")
    public void deleteCarById (@PathVariable long id){
        Optional<Car> car = carService.getCarById(id);
        checkIfEmpty(car);
        carService.deleteCar(car.get());
    }

    @GetMapping(path = "car/available")
    public List<Car> getAllAvailableCars(){
        return carService.getAllAvailableCars();
    }

    private void checkIfEmpty(Optional<?> optional){
        if (optional.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, CAR_NOT_FOUND
            );
        }
    }
}
