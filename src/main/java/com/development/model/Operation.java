package com.development.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="T_PARKING_LEVEL")
public class Operation implements Serializable{
	private static final long serialVersionUID = 4154778492372534803L;
	
	@Id 
	@Column(name= "ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "TIME_START", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeEnter;
	
	@Column(name = "TIME_EXIT", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeExit;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="VEHICLE_ID")
	private Vehicle vehicle;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PARKING_LOT_ID")
	private ParkingLot lot;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTimeEnter() {
		return timeEnter;
	}

	public void setTimeEnter(Date timeEnter) {
		this.timeEnter = timeEnter;
	}

	public Date getTimeExit() {
		return timeExit;
	}

	public void setTimeExit(Date timeExit) {
		this.timeExit = timeExit;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public ParkingLot getLot() {
		return lot;
	}

	public void setLot(ParkingLot lot) {
		this.lot = lot;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((timeEnter == null) ? 0 : timeEnter.hashCode());
		result = prime * result + ((timeExit == null) ? 0 : timeExit.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lot == null) ? 0 : lot.hashCode());
		result = prime * result + ((vehicle == null) ? 0 : vehicle.hashCode());
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
		Operation other = (Operation) obj;
		if (timeEnter == null) {
			if (other.timeEnter != null)
				return false;
		} else if (!timeEnter.equals(other.timeEnter))
			return false;
		if (timeExit == null) {
			if (other.timeExit != null)
				return false;
		} else if (!timeExit.equals(other.timeExit))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lot == null) {
			if (other.lot != null)
				return false;
		} else if (!lot.equals(other.lot))
			return false;
		if (vehicle == null) {
			if (other.vehicle != null)
				return false;
		} else if (!vehicle.equals(other.vehicle))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Operation id: " + getId() +  ", vehicleId: " + getVehicle().getId() + ", lotId: "  + getLot().getId() + ", timeEnter: " + getTimeEnter() + ", timeExit: " + getTimeExit();
	}
}
