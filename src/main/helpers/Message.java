package main.helpers;

import net.slashie.libjcsi.CSIColor;

public class Message {
	
	private String message;
	private CSIColor color;

	public String getMessage() {
		return this.message;
	}

	public CSIColor getColor() {
		return this.color;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setColor(CSIColor color) {
		this.color = color;
	}

	///////////////////////////////
	//// MESSAGE FOR COMBATLOG ////
	///////////////////////////////
	public Message(String message, CSIColor color) {
		this.message = message;
		this.color = color;
	}
}
