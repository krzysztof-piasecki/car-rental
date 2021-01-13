package pl.homework.carrental.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.homework.carrental.BaseTests;
import pl.homework.carrental.model.Car;

import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

@SpringBootTest
@ActiveProfiles("test")
class CarServiceTests extends BaseTests {

    @Autowired
    CarService carService;

    @Test
    void saveAndDeleteCar(){
        //clear table
        carService.deleteAllCars();

        //given
        Car firstCar = getFirstTestCar();
        Car secondCar = getSecondTestCar();

        Car car = new Car();
        //then
        //save
        carService.saveCar(firstCar);
        assertEquals(firstCar,carService.getCarById(firstCar.getId()).get());

        firstCar.setAvailable(false);
        assertNotEquals(firstCar,carService.getCarById(firstCar.getId()).get());

        //check if the table contains two items
        carService.saveCar(secondCar);
        assertEquals(2,StreamSupport.stream(carService.getAllCars().spliterator(), false).count());

        //delete
        carService.deleteCar(firstCar);
        assertTrue(carService.getCarById(firstCar.getId()).isEmpty());

        //clear table
        carService.deleteAllCars();
        //check if deleted all
        assertFalse(carService.getAllCars().iterator().hasNext());
    }

    @Test
    void getCarById(){
        //clear table
        carService.deleteAllCars();

        //given
        Car car = getFirstTestCar();

        //then
        carService.saveCar(car);
        assertEquals(car, carService.getCarById(car.getId()).get());

        //clear table
        carService.deleteAllCars();
    }

    @Test
    void getAllAvailableCars(){
        //clear table
        carService.deleteAllCars();

        //given
        Car firstCar = getFirstTestCar();
        Car secondCar = getSecondTestCar();
        Car thirdCar = getThirdTestCar();

        //then
        carService.saveCar(firstCar);
        carService.saveCar(secondCar);
        carService.saveCar(thirdCar);

        assertEquals(3, carService.getAllAvailableCars().size());

        //clear table
        carService.deleteAllCars();
    }
}
