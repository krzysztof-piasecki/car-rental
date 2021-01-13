package pl.homework.carrental.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id")
    private Car car;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rentee_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Rentee rentee;
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

    public Rentee getRentee() {return rentee;}

    public void setRentee(Rentee rentee) {this.rentee = rentee;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rental rental = (Rental) o;
        return id == rental.id &&
                Objects.equals(car, rental.car) &&
                Objects.equals(rentee, rental.rentee) &&
                Objects.equals(startRentalDate, rental.startRentalDate) &&
                Objects.equals(endRentalDate, rental.endRentalDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, car, rentee, startRentalDate, endRentalDate);
    }
}
