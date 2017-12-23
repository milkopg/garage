package com.development.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

//@Subselect("SELECT PARKING_LEVEL_ID, Sum(IS_FREE) free, (SELECT COUNT(*)  FROM t_parking_lot WHERE IS_FREE = 0) used  FROM t_parking_lot  where is_free = 1  group by PARKING_LEVEL_ID, used;")
@Immutable
@Entity
@Table(name="V_GARAGE_STATUS")
public class ViewGarageStatus implements Serializable{
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

	@Override
	public String toString() {
		return "ViewGarageStatus levelName: " + getLevelName() + ", capacity: " + getCapacity() + ", free: " + getFree() + ", used: " + getUsed();
	}
	
}
