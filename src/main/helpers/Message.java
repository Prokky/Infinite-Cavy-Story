package main.helpers;

import net.slashie.libjcsi.CSIColor;

/**
 * 
 * Class for easy handling with messages in combat log
 * 
 * @author prokk
 * 
 */
public class Message {

	private String message;
	private CSIColor color;

	/**
	 * 
	 * @return message
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * 
	 * @return color of message
	 */
	public CSIColor getColor() {
		return this.color;
	}

	/**
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 
	 * @param color
	 *            of message
	 */
	public void setColor(CSIColor color) {
		this.color = color;
	}

	/**
	 * Constructor
	 * 
	 * @param message
	 * @param color
	 *            of message
	 */
	public Message(String message, CSIColor color) {
		this.message = message;
		this.color = color;
	}
}
