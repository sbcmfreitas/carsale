package io.carsale.model;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonProperty;

@MappedSuperclass
@SuppressWarnings("serial")
public abstract class AbstractEntity<ID extends Serializable> implements Serializable {
	
    @Id
	@JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id = null;

    /**
     * Get id
     * 
     * @return id
     **/
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		int rand = ThreadLocalRandom.current().nextInt();
		result = prime * result + ((id == null) ? rand : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractEntity<?> other = (AbstractEntity<?>) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "id = " + id ;
	}	

}
