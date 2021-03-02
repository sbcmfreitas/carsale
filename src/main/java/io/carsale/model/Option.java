package io.carsale.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import org.springframework.validation.annotation.Validated;

/**
 * Option
 */
@Entity
@Validated
@SuppressWarnings("serial")
public class Option extends AbstractEntity<Long> {

    @Column(name="name", nullable = false, unique = true, length = 100)
    private String name = null;

    @ManyToMany(cascade = {CascadeType.REFRESH},
            mappedBy = "options",
            targetEntity = Car.class,
            fetch = FetchType.EAGER
        )
    private List <Car> cars;

    /**
     * Constructor
     **/
    public Option() {
      super();
    }

    public Option(Long id) {
      super();
      this.id = id;
    }

    /**
     * Get name
     * @return name
     **/
    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    /**
     * Get cars with the option
     * @return cars with the option
     **/
    public List<Car> getCars() {
      return cars;
    }

    public void setCars(List<Car> cars) {
      this.cars = cars;
    }


}
