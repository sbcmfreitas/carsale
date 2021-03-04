package io.carsale.model;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Brand
 */
@Entity
@SuppressWarnings("serial")
public class Brand extends AbstractEntity<Long> {

    @Column(name="name", nullable = false, unique = true, length = 100)
    private String name = null;

   /**
     * Constructors
     **/
    public Brand() {
      super();
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

}
