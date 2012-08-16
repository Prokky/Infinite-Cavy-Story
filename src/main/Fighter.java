package main;

import java.util.logging.Logger;

public class Fighter {

	private static Logger log = Logger.getLogger(Fighter.class.getName());
	private Entity owner;
	private int max_hp;
	private int hp;
	private int defence;
	private int power;

	public Fighter(Entity owner, int hp, int defence, int power) {
		this.owner = owner;
		this.max_hp = hp;
		this.hp = hp;
		this.defence = defence;
		this.power = power;
	}

	public int getMaxHP() {
		return this.max_hp;
	}

	public int getHp() {
		return this.hp;
	}

	public int getDefence() {
		return this.defence;
	}

	public int getPower() {
		return this.power;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public void takeDamage(int damage) {
		if (damage > 0)
			this.hp -= damage;

		if (this.hp <= 0)
			owner.deathFunction();
	}

	public void attack(Entity target) {
		int damage = this.power - target.getFighterComponent().getDefence();
		if (damage > 0) {
			MainGame.getInstance().newMessage(
					"--" + this.owner.getName() + " hits ");
			MainGame.getInstance().newMessage(
					target.getName() + " for " + damage);
			target.getFighterComponent().takeDamage(damage);
		} else {
			MainGame.getInstance().newMessage(
					"--" + this.owner.getName() + " hits ");
			MainGame.getInstance().newMessage(" but it has no effect!");
		}
	}
}
