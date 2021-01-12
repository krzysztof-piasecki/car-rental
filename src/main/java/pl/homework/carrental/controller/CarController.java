package pl.homework.carrental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.homework.carrental.model.Car;
import pl.homework.carrental.service.CarService;

import java.util.Optional;


@RestController
public class CarController {

    private static final String CARNOTFOUND = "Car not found";

    @Autowired
    private CarService carService;

    @GetMapping(path = "car:{id}")
    public Car getCarById(@PathVariable Long id){
        Optional<Car> car = carService.getCarById(id);
        if (car.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, CARNOTFOUND
            );
        }
        return car.get();
    }

    @PostMapping("car")
    public void saveCar (@RequestBody Car car){
        carService.saveCar(car);
    }

    @DeleteMapping("car:{id}")
    public void deleteCarById (@PathVariable long id){
        Optional<Car> car = carService.getCarById(id);
        if (car.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, CARNOTFOUND
            );
        }else{
            carService.deleteCar(car.get());
        }
    }
}
