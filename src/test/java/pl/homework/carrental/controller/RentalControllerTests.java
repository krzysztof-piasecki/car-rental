package pl.homework.carrental.controller;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import pl.homework.carrental.BaseTests;
import pl.homework.carrental.model.Car;
import pl.homework.carrental.model.Rental;
import pl.homework.carrental.model.Rentee;
import pl.homework.carrental.service.CarService;
import pl.homework.carrental.service.RentalService;
import pl.homework.carrental.service.RenteeService;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@ActiveProfiles("Test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RentalControllerTests extends BaseTests {
    @LocalServerPort
    int randomServerPort;

    @Autowired
    CarService carService;
    @Autowired
    RentalService rentalService;
    @Autowired
    RenteeService renteeService;

    @Test
    void getRentById(){
        rentalService.deleteAllRental();
        carService.deleteAllCars();
        renteeService.deleteAllRentees();

        port = randomServerPort;
        String path = "/rent:{id}";
        //given
        Car car = getFirstTestCar();
        carService.saveCar(car);
        Rentee rentee = getFirstRentee();
        renteeService.saveRentee(rentee);

        rentalService.rentACar(car, rentee);

        Rental rental = rentalService.getAllRental().iterator().next();
        int id = (int) rental.getId();
        String carString = "{horsePower=127.0, available=false, company=Toyota, model=Avensis, id=" + car.getId() + ", engineCapacity=2000}";
        String renteeString = "{firstName=Jan, lastName=Kowalski, id=" + rentee.getId() + "}";

        when()
                .get(path,id)
                .then()
                .statusCode(200)
                .body("id", equalTo((id)))
                .body("car", hasToString(carString))
                .body("startRentalDate", notNullValue())
                .body("endRentalDate", nullValue())
                .body("rentee",hasToString(renteeString));

        rentalService.deleteAllRental();
        carService.deleteAllCars();
        renteeService.deleteAllRentees();
    }

    @Test
    void rentACar(){
        rentalService.deleteAllRental();
        renteeService.deleteAllRentees();
        carService.deleteAllCars();

        port = randomServerPort;
        String path = "/rent:{carId}/rentee:{renteeId}";
        //given
        Car car = getFirstTestCar();
        carService.saveCar(car);
        Long carId = car.getId();

        Rentee rentee = getFirstRentee();
        renteeService.saveRentee(rentee);
        Long renteeId = rentee.getId();

        given()
                .post(path,carId, renteeId)
                .then()
                .statusCode(200);

        Rental rental = rentalService.getAllRental().iterator().next();
        car = carService.getCarById(carId).get();

        assertEquals(car, rental.getCar());
        assertEquals(rentee, rental.getRentee());
        assertNotNull(rental.getStartRentalDate());
        assertNull(rental.getEndRentalDate());

        rentalService.deleteAllRental();
        renteeService.deleteAllRentees();
        carService.deleteAllCars();
    }

    @Test
    void returnACar(){
        rentalService.deleteAllRental();
        renteeService.deleteAllRentees();
        carService.deleteAllCars();

        port = randomServerPort;
        String path = "return:{id}";
        //given
        Car car = getFirstTestCar();
        carService.saveCar(car);
        Long carId = car.getId();

        Rentee rentee = getFirstRentee();
        renteeService.saveRentee(rentee);
        rentalService.rentACar(car, rentee);
        Rental rental = rentalService.getAllRental().iterator().next();
        long id = rental.getId();

        given()
                .post(path,id)
                .then()
                .statusCode(200);

        car = carService.getCarById(carId).get();
        rental = rentalService.getRentalById(id).get();

        assertEquals(car, rental.getCar());
        assertEquals(rentee, rental.getRentee());
        assertNotNull(rental.getEndRentalDate());
        assertNotNull(rental.getStartRentalDate());

        rentalService.deleteAllRental();
        renteeService.deleteAllRentees();
        carService.deleteAllCars();

    }
}
