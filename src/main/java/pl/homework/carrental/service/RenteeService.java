package pl.homework.carrental.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.homework.carrental.model.Rentee;
import pl.homework.carrental.repository.RenteeRepository;

import java.util.Optional;

@Service
public class RenteeService {

    @Autowired
    RenteeRepository renteeRepository;

    public void saveRentee(Rentee rentee){
        renteeRepository.save(rentee);
    }

    public void deleteRentee(Rentee rentee){
        renteeRepository.delete(rentee);
    }
    public Optional<Rentee> getRenteeById(Long id){
        return renteeRepository.findById(id);
    }
}
