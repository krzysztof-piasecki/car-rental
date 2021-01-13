package pl.homework.carrental;

import pl.homework.carrental.model.Car;
import pl.homework.carrental.model.Rentee;

public class BaseTests {

    public Car getFirstTestCar(){
        return new Car.Builder()
                .withCompany("Toyota")
                .withModel("Avensis")
                .withEngineCapacity(2000)
                .withHorsePower(127D)
                .build();
    }
    public Car getSecondTestCar(){
        return new Car.Builder()
                .withCompany("Volkswagen")
                .withModel("Passat")
                .withEngineCapacity(1999)
                .withHorsePower(151D)
                .build();
    }

    public Car getThirdTestCar(){
        return new Car.Builder()
                .withCompany("BMW")
                .withModel("E36")
                .withEngineCapacity(1500)
                .withHorsePower(97D)
                .build();
    }

    public Rentee getFirstRentee(){
        return new Rentee.Builder()
                .withLastName("Kowalski")
                .withFirstName("Jan")
                .build();
    }
    public Rentee getSecondRentee(){
        return new Rentee.Builder()
                .withLastName("Mariusz")
                .withFirstName("Sanitariusz")
                .build();
    }
}
