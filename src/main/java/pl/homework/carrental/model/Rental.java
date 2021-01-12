package pl.homework.carrental.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;
    private Date startRentalDate;
    private Date endRentalDate;


    public long getId() {
        return id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Date getStartRentalDate() {
        return startRentalDate;
    }

    public void setStartRentalDate(Date startRentalDate) {
        this.startRentalDate = startRentalDate;
    }

    public Date getEndRentalDate() {
        return endRentalDate;
    }

    public void setEndRentalDate(Date endRentalDate) {
        this.endRentalDate = endRentalDate;
    }
}
