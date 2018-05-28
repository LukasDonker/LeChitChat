package model;

import java.io.Serializable;

public class ChatOperation implements Serializable{

	private static final long serialVersionUID = 2L;

	private Operation operation;
	private Object data;
	
	public ChatOperation(Operation operation, Object data) {
		this.operation = operation;
		this.data = data;
	}
	
	public Operation getOperation() {
		return operation;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
