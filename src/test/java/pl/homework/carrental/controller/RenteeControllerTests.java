package pl.homework.carrental.controller;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import pl.homework.carrental.BaseTests;
import pl.homework.carrental.model.Rentee;
import pl.homework.carrental.service.RenteeService;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("Test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RenteeControllerTests extends BaseTests {
    
    @LocalServerPort
    int randomServerPort;
    
    @Autowired
    RenteeService renteeService;

    @Test
    void getRenteeById() {
        renteeService.deleteAllRentees();

        port = randomServerPort;
        String path = "/rentee:{id}";
        //given
        Rentee rentee = getFirstRentee();
        renteeService.saveRentee(rentee);
        int id = rentee.getId().intValue();

        when()
                .get(path,id)
                .then()
                .statusCode(200)
                .body("id", equalTo((id)))
                .body("firstName", equalTo(rentee.getFirstName()))
                .body("lastName", equalTo(rentee.getLastName()));

        renteeService.deleteAllRentees();
    }

    @Test
    void saveRentee() {
        renteeService.deleteAllRentees();

        port = randomServerPort;
        //given
        Rentee rentee = getFirstRentee();
        String path = "/rentee";
        String requestString = "{ " +
                "\"firstName\": \"Jan\"," +
                "\"lastName\": \"Kowalski\"}";

        given()
                .contentType(ContentType.JSON)
                .body(requestString)
                .post(path)
                .then()
                .statusCode(200)
                .extract()
                .response();

        Rentee jsonRentee = renteeService.getAllRentees().iterator().next();

        assertEquals(rentee.getFirstName(),jsonRentee.getFirstName());
        assertEquals(rentee.getLastName(),jsonRentee.getLastName());

        renteeService.deleteAllRentees();
    }

    @Test
    void deleteRenteeById() {
        renteeService.deleteAllRentees();

        port = randomServerPort;
        String path = "/rentee:{id}";
        //given
        Rentee rentee = getFirstRentee();
        renteeService.saveRentee(rentee);
        Long id = rentee.getId();

        assertNotNull(renteeService.getRenteeById(id));

        when()
                .delete(path, id)
                .then()
                .statusCode(200);

        assertTrue(renteeService.getRenteeById(id).isEmpty());
    }
}
