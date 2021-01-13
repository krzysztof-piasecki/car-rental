package pl.homework.carrental.controller;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import pl.homework.carrental.BaseTests;
import pl.homework.carrental.model.Car;
import pl.homework.carrental.service.CarService;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@ActiveProfiles("Test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CarControllerTests extends BaseTests {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    CarService carService;

    @Test
    void getCarById() {
        carService.deleteAllCars();

        port = randomServerPort;
        String path = "/car:{id}";
        //given
        Car car = getFirstTestCar();
        carService.saveCar(car);
        int id = (int) car.getId();

        when()
                .get(path,id)
                .then()
                .statusCode(200)
                .body("id", equalTo((id)))
                .body("company", equalTo(car.getCompany()))
                .body("model", equalTo(car.getModel()))
                .body("horsePower", hasToString(car.getHorsePower().toString()))
                .body("engineCapacity",equalTo(car.getEngineCapacity()))
                .body("available", equalTo(car.isAvailable()));

        carService.deleteAllCars();
    }

    @Test
    void saveCar() {
        carService.deleteAllCars();

        port = randomServerPort;
        //given
        Car car = getFirstTestCar();
        String path = "/car";
        String requestString = "{ " +
                "\"id\": 1," +
                "\"company\": \"Toyota\"," +
                "\"model\": \"Avensis\", " +
                "\"horsePower\": 127, " +
                "\"engineCapacity\": 2000, " +
                "\"available\": true }";

        given()
                .contentType(ContentType.JSON)
                .body(requestString)
                .post(path)
                .then()
                .statusCode(200)
                .extract()
                .response();

        Car jsonCar = carService.getAllCars().iterator().next();

        assertEquals(car.getCompany(),jsonCar.getCompany());
        assertEquals(car.getModel(),jsonCar.getModel());
        assertEquals(car.getHorsePower(),jsonCar.getHorsePower());
        assertEquals(car.getEngineCapacity(),jsonCar.getEngineCapacity());
        assertEquals(car.isAvailable(),jsonCar.isAvailable());

        carService.deleteAllCars();
    }

    @Test
    void deleteCarById() {
        carService.deleteAllCars();

        port = randomServerPort;
        String path = "/car:{id}";
        //given
        Car car = getFirstTestCar();
        carService.saveCar(car);
        Long id = car.getId();

        assertNotNull(carService.getCarById(id));

        when()
                .delete(path, id)
                .then()
                .statusCode(200);

        assertTrue(carService.getCarById(id).isEmpty());
    }

    @Test
    void getAllAvailableCars(){
        port = randomServerPort;
        String path = "car/available";
        //given
        Car firstCar = getFirstTestCar();
        Car secondCar = getSecondTestCar();
        carService.saveCar(firstCar);
        carService.saveCar(secondCar);

        when()
                .get(path)

                .then()
                .statusCode(200)
                .body("id", hasItem((int) firstCar.getId()))
                .body("company", hasItem(firstCar.getCompany()))
                .body("model", hasItem(firstCar.getModel()))
                .body("horsePower", hasItem(firstCar.getHorsePower().floatValue()))
                .body("engineCapacity",hasItem(firstCar.getEngineCapacity()))
                .body("available", hasItem(firstCar.isAvailable()))
                .body("id", hasItem(((int) secondCar.getId())))
                .body("company", hasItem(secondCar.getCompany()))
                .body("model", hasItem(secondCar.getModel()))
                .body("horsePower", hasItem(secondCar.getHorsePower().floatValue()))
                .body("engineCapacity",hasItem(secondCar.getEngineCapacity()))
                .body("available", hasItem(secondCar.isAvailable()))
                .body("size()", is(2));
        carService.deleteAllCars();
    }
}
