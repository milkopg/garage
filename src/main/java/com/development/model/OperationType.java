package com.development.model;

public enum OperationType {
	DEVICE_UNKNOWN(-1),
	ENTER(1),
	EXIT(2),
	STATUS(3);
	
	private int id;
	
	private OperationType(int value) {
		id = value;
	}
	
	public int getValue() {
		return id;
	}
	
	public static OperationType valueOf(int i) {
		for(OperationType operationType : OperationType.values()) {
			if(operationType.id == i) {
				return operationType;
			}
		}
		return DEVICE_UNKNOWN;
	}
	
	@Override
	public String toString() {
		return Integer.toString(id);
	}
}
