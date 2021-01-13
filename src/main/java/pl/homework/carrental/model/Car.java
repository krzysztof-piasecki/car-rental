package pl.homework.carrental.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String company;
    @Column(nullable = false)
    private String model;
    private Double horsePower;
    private Integer engineCapacity;
    private boolean isAvailable = true;

    public long getId() {return id;}

    public String getCompany() {return company;}

    public void setCompany(String company) {this.company = company;}

    public String getModel() {return model;}

    public void setModel(String model) {
        this.model = model;
    }

    public Double getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(Double horsePower) {
        this.horsePower = horsePower;
    }

    public Integer getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(Integer engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public boolean isAvailable() {return isAvailable;}

    public void setAvailable(boolean available) {isAvailable = available;}

    public static final class Builder{
        private String company;
        private String model;
        private Double horsePower;
        private Integer engineCapacity;
        private boolean isAvailable = true;


        public Builder withCompany(String company) {
            this.company = company;
            return this;
        }

        public Builder withModel(String model) {
            this.model = model;
            return this;
        }

        public Builder withHorsePower(Double horsePower) {
            this.horsePower = horsePower;
            return this;
        }

        public Builder withEngineCapacity(Integer engineCapacity) {
            this.engineCapacity = engineCapacity;
            return this;
        }

        public Builder withIsAvailable(boolean isAvailable){
            this.isAvailable = isAvailable;
            return this;
        }
        public Car build() {
            Car car = new Car();
            car.setCompany(company);
            car.setModel(model);
            car.setHorsePower(horsePower);
            car.setEngineCapacity(engineCapacity);
            car.setAvailable(isAvailable);
            return car;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id &&
                isAvailable == car.isAvailable &&
                Objects.equals(company, car.company) &&
                Objects.equals(model, car.model) &&
                Objects.equals(horsePower, car.horsePower) &&
                Objects.equals(engineCapacity, car.engineCapacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, company, model, horsePower, engineCapacity, isAvailable);
    }
}
