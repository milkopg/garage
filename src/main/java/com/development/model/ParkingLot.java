package com.development.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="T_PARKING_LOT")
public class ParkingLot implements Serializable{
	private static final long serialVersionUID = -1753980509280051635L;
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotEmpty
	@Column(name="NAME", unique=true, nullable=false)
	private String name;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PARKING_LEVEL_ID")
	private ParkingLevel parkingLevel;
	
	@Column(nullable = false, columnDefinition = "TINYINT(1)")
	private Boolean isFree;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ParkingLevel getParkingLevel() {
		return parkingLevel;
	}

	public void setParkingLevel(ParkingLevel parkingLevel) {
		this.parkingLevel = parkingLevel;
	}

	public Boolean getIsFree() {
		return isFree;
	}

	public void setIsFree(Boolean isFree) {
		this.isFree = isFree;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isFree == null) ? 0 : isFree.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parkingLevel == null) ? 0 : parkingLevel.hashCode());
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
		ParkingLot other = (ParkingLot) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isFree == null) {
			if (other.isFree != null)
				return false;
		} else if (!isFree.equals(other.isFree))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parkingLevel == null) {
			if (other.parkingLevel != null)
				return false;
		} else if (!parkingLevel.equals(other.parkingLevel))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ParkingLot id: " + getId() + ", name: " + getName() + ", parkingLevelId: " + getParkingLevel().getId();
	}
}
