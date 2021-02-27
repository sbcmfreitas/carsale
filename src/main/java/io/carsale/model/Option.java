package io.carsale.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Option
 */
@Entity
@Validated
@SuppressWarnings("serial")
public class Option extends AbstractEntity<Long> {

    @JsonProperty("name")
    @Column(name="name", nullable = false, unique = true, length = 100)
    private String name = null;

    @JsonProperty("cars")
    @JsonManagedReference
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
    @Schema(example = "Ford", required = true, description = "") @NotNull
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
    @Schema(required = false, description = "")
    public List<Car> getCars() {
      return cars;
    }

    public void setCars(List<Car> cars) {
      this.cars = cars;
    }


}
