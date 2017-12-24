package com.development.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;


//@Subselect("SELECT PARKING_LEVEL_ID, Sum(IS_FREE) free, (SELECT COUNT(*)  FROM t_parking_lot WHERE IS_FREE = 0) used  FROM t_parking_lot  where is_free = 1  group by PARKING_LEVEL_ID, used;")
@NamedNativeQuery(
	    name="myQuery",
	    query = "SELECT PARKING_LEVEL_ID, Sum(IS_FREE) free, (SELECT COUNT(*)  FROM t_parking_lot WHERE IS_FREE = 0) used  FROM t_parking_lot  where is_free = 1  group by PARKING_LEVEL_ID, used;",
	    resultClass=GarageStatus.class
	)
@Entity
@Immutable
public class GarageStatus implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2244457277579266669L;
	
	@Id
	@Column(name = "LEVEL_NAME")
	private String levelName;
	
	@Column(name = "CAPACITY")
	private Integer capacity;
	
	@Column(name = "FREE")
	private Integer free;
	
	@Column(name = "USED")
	private Integer used;
	
	public Integer getCapacity() {
		return capacity;
	}

	public String getLevelName() {
		return levelName;
	}

	public Integer getFree() {
		return free;
	}

	
	public Integer getUsed() {
		return used;
	}
	
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public void setFree(Integer free) {
		this.free = free;
	}

	public void setUsed(Integer used) {
		this.used = used;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((capacity == null) ? 0 : capacity.hashCode());
		result = prime * result + ((free == null) ? 0 : free.hashCode());
		result = prime * result + ((levelName == null) ? 0 : levelName.hashCode());
		result = prime * result + ((used == null) ? 0 : used.hashCode());
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
		GarageStatus other = (GarageStatus) obj;
		if (capacity == null) {
			if (other.capacity != null)
				return false;
		} else if (!capacity.equals(other.capacity))
			return false;
		if (free == null) {
			if (other.free != null)
				return false;
		} else if (!free.equals(other.free))
			return false;
		if (levelName == null) {
			if (other.levelName != null)
				return false;
		} else if (!levelName.equals(other.levelName))
			return false;
		if (used == null) {
			if (other.used != null)
				return false;
		} else if (!used.equals(other.used))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ViewGarageStatus levelName: " + getLevelName() + ", capacity: " + getCapacity() + ", free: " + getFree() + ", used: " + getUsed();
	}
	
}
