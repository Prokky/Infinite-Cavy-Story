package main;

import main.entities.Creature;

public class Effect {
	protected int duration;
	protected String effectName;

	public boolean isDone() {
		return duration < 1;
	}

	public String name() {
		return effectName;
	}

	public int duration() {
		return duration;
	}

	public Effect(int duration, String effectName) {
		this.duration = duration;
		this.effectName = effectName;
	}

	public Effect(Effect other) {
		this.duration = other.duration;
		this.effectName = other.effectName;
	}

	public void update(Creature creature) {
		duration--;
	}

	public void start(Creature creature) {

	}

	public void end(Creature creature) {

	}
}
