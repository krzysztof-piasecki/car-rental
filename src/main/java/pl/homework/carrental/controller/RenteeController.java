package pl.homework.carrental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.homework.carrental.model.Rentee;
import pl.homework.carrental.service.RenteeService;

import java.util.Optional;

@RestController
public class RenteeController {

    private static final String RENTEE_NOT_FOUND = "Rentee not found";

    @Autowired
    RenteeService renteeService;

    @GetMapping(path = "rentee:{id}")
    public Rentee getRenteeById(@PathVariable Long id){
        Optional<Rentee> rentee = renteeService.getRenteeById(id);
        checkIfEmpty(rentee);
        return rentee.get();
    }
    @PostMapping(path = "rentee")
    public void saveRentee (@RequestBody Rentee rentee){
        renteeService.saveRentee(rentee);
    }

    @DeleteMapping(path = "rentee:{id}")
    public void deleteRenteeById (@PathVariable long id){
        Optional<Rentee> rentee = renteeService.getRenteeById(id);
        checkIfEmpty(rentee);
        renteeService.deleteRentee(rentee.get());
    }

    private void checkIfEmpty(Optional<?> optional){
        if (optional.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, RENTEE_NOT_FOUND
            );
        }
    }
}
