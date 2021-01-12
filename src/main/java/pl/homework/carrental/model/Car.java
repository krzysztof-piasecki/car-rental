package pl.homework.carrental.model;

import javax.persistence.*;

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
    private boolean isAvailable;

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
}
