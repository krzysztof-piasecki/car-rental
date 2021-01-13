package pl.homework.carrental.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.homework.carrental.BaseTests;
import pl.homework.carrental.model.Car;
import pl.homework.carrental.model.Rental;
import pl.homework.carrental.model.Rentee;

import java.util.Iterator;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;


@SpringBootTest
@ActiveProfiles("test")
class RentalServiceTests extends BaseTests {

    @Autowired
    RentalService rentalService;
    @Autowired
    CarService carService;
    @Autowired
    RenteeService renteeService;

    @Test
    void rentAndReturnCar(){
        //clear table
        rentalService.deleteAllRental();
        renteeService.deleteAllRentees();
        carService.deleteAllCars();

        //given
        Car firstCar = getFirstTestCar();
        Car secondCar = getSecondTestCar();
        Car thirdCar = getThirdTestCar();
        Rentee firstRentee = getFirstRentee();
        Rentee secondRentee = getSecondRentee();

        carService.saveCar(firstCar);
        carService.saveCar(secondCar);
        carService.saveCar(thirdCar);
        renteeService.saveRentee(firstRentee);
        renteeService.saveRentee(secondRentee);
        //then
        //check all available cars
        assertEquals(3, carService.getAllAvailableCars().size());

        rentalService.rentACar(firstCar,firstRentee);
        rentalService.rentACar(secondCar,secondRentee);

        //check all available cars
        assertEquals(1, carService.getAllAvailableCars().size());

        //check all rentals
        assertEquals(2, StreamSupport.stream(rentalService.getAllRental().spliterator(), false).count());

        Iterator<Rental> allRentals = rentalService.getAllRental().iterator();

        rentalService.returnACar(allRentals.next());
        //check all available cars
        assertEquals(2, carService.getAllAvailableCars().size());

        rentalService.returnACar(allRentals.next());
        //check all available cars
        assertEquals(3, carService.getAllAvailableCars().size());

        //clear table
        rentalService.deleteAllRental();
        renteeService.deleteAllRentees();
        carService.deleteAllCars();
    }

}
