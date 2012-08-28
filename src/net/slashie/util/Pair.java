package net.slashie.util;

import java.io.Serializable;

public class Pair <K,L> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8806522675655748110L;
	private K a;
	public Pair(K a_, L b_){
		a = a_;
		b = b_;
	}
	public K getA() {
		return a;
	}
	public void setA(K a) {
		this.a = a;
	}
	public L getB() {
		return b;
	}
	public void setB(L b) {
		this.b = b;
	}
	private L b;
}
