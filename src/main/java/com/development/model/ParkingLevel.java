package com.development.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="T_PARKING_LEVEL")
public class ParkingLevel implements Serializable{

	private static final long serialVersionUID = -7239333350021551267L;
	
	@Id 
	@Column(name= "ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotEmpty
	@Column(name="LEVEL_NAME", unique=true, nullable=false)
	private String levelName;
	
	@Column(name="CAPACITY", nullable=false)
	private Integer capacity;

	@Transient
	private Integer startNumber;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Integer getStartNumber() {
		return startNumber;
	}

	public void setStartNumber(Integer startNumber) {
		this.startNumber = startNumber;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((capacity == null) ? 0 : capacity.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((levelName == null) ? 0 : levelName.hashCode());
		result = prime * result + ((startNumber == null) ? 0 : startNumber.hashCode());
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
		ParkingLevel other = (ParkingLevel) obj;
		if (capacity == null) {
			if (other.capacity != null)
				return false;
		} else if (!capacity.equals(other.capacity))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (levelName == null) {
			if (other.levelName != null)
				return false;
		} else if (!levelName.equals(other.levelName))
			return false;
		if (startNumber == null) {
			if (other.startNumber != null)
				return false;
		} else if (!startNumber.equals(other.startNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ParkingLevel id: " + getId() + ", name: " + getLevelName() + ", capacity" + getCapacity();
	}
}
