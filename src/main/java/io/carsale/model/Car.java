package io.carsale.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import io.carsale.dto.CarRequest;

/**
 * Car
 */
@Entity
@Validated
@SuppressWarnings("serial")
public class Car extends AbstractEntity<Long>{

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand = null;

    @Column(nullable= false, precision=7, scale=2)
    @Digits(integer=9, fraction=2)     
    private BigDecimal value = null;

    @NotEmpty(message="Is need one options, at least")
    @Size(min=1, max=2,message = "Allowed between 1 and 2 options")
    @ManyToMany(
            targetEntity=Option.class,
            fetch=FetchType.LAZY, 
            cascade={CascadeType.REFRESH}
        )
        @JoinTable(
            name="car_option",
            joinColumns=@JoinColumn(name="car_id"),
            inverseJoinColumns=@JoinColumn(name="option_id"),
            uniqueConstraints=@javax.persistence.UniqueConstraint(columnNames = {"car_id", "option_id"}) 
        )
    @OrderBy("name ASC")
    private List<Option> options;

    /**
     * Constructors
     **/
    public Car() {
    }

    public Car(CarRequest req) {
      super();
      this.id = req.getId()!=null ? req.getId() : null;
      this.brand = new Brand(req.getBrandId());
      this.value = req.getValue();
      this.options = req.getOptionIds().stream().map(p -> new Option(p)).collect(Collectors.toList());
    }

    public Car(Long id) {
      super();
      this.id = id;
    }

    /**
     * Get brand
     * 
     * @return brand
     **/
    public Brand getBrand() {
      return brand;
    }

    public void setBrand(Brand brand) {
      this.brand = brand;
    }

    /**
     * Get value
     * 
     * @return value
     **/
    public BigDecimal getValue() {
      return value;
    }

    public void setValue(BigDecimal value) {
      this.value = value;
    }

    /**
     * Get options
     * 
     * @return options
     **/
    public List<Option> getOptions() {
      return options;
    }

    public void setOptions(List<Option> options) {
      this.options = options;
    }


}
