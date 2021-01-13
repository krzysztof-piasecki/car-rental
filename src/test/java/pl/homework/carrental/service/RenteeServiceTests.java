package pl.homework.carrental.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.homework.carrental.BaseTests;
import pl.homework.carrental.model.Rentee;

import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

@SpringBootTest
@ActiveProfiles("test")
class RenteeServiceTests extends BaseTests {
    
    @Autowired
    RenteeService renteeService;

    @Test
    void saveAndDeleteRentee(){
        //clear table
        renteeService.deleteAllRentees();

        //given
        Rentee firstRentee = getFirstRentee();
        Rentee secondRentee = getSecondRentee();

        //when
        //save
        renteeService.saveRentee(firstRentee);
        renteeService.saveRentee(secondRentee);

        //then
        assertEquals(firstRentee,renteeService.getRenteeById(firstRentee.getId()).get());
        assertNotEquals(secondRentee,renteeService.getRenteeById(firstRentee.getId()).get());

        //check if the table contains two items
        assertEquals(2, StreamSupport.stream(renteeService.getAllRentees().spliterator(), false).count());

        //delete
        //when
        renteeService.deleteRentee(firstRentee);
        //then
        assertTrue(renteeService.getRenteeById(firstRentee.getId()).isEmpty());

        //clear table
        renteeService.deleteAllRentees();
        //check if deleted all
        assertFalse(renteeService.getAllRentees().iterator().hasNext());
    }

    @Test
    void getRenteeById(){
        //clear table
        renteeService.deleteAllRentees();

        //given
        Rentee firstRentee = getFirstRentee();
        Rentee secondRentee = getSecondRentee();
        //then
        renteeService.saveRentee(firstRentee);
        assertEquals(firstRentee, renteeService.getRenteeById(firstRentee.getId()).get());
        assertNotEquals(secondRentee, renteeService.getRenteeById(firstRentee.getId()).get());

        //clear table
        renteeService.deleteAllRentees();
    }
}
