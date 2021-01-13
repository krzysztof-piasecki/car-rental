package pl.homework.carrental.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Rentee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static final class Builder{
        private String firstName;
        private String lastName;


        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Rentee build() {
            Rentee rentee = new Rentee();
            rentee.setFirstName(firstName);
            rentee.setLastName(lastName);
            return rentee;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rentee rentee = (Rentee) o;
        return Objects.equals(id, rentee.id) &&
                Objects.equals(firstName, rentee.firstName) &&
                Objects.equals(lastName, rentee.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }
}
