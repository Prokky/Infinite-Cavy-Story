package main;

import main.helpers.Helpers;

public class FighterComponent {
	// /// LOGGING INSTANCE ////
	// private static Logger log =
	// Logger.getLogger(FighterComponent.class.getName());

	// //// PRIVATE FIELD FOR COMPONENTS
	private Entity owner; // owner
	private int max_hp; // maximum hp
	private int hp; // current hp
	private int defence; // defence
	private int power; // power

	// /// FIGHTER COMPONENT INITIATED WITH OWNER, HP, DEFENCE AND PAWA! ///////
	public FighterComponent(Entity owner, int hp, int defence, int power) {
		this.owner = owner;
		this.max_hp = hp;
		this.hp = hp;
		this.defence = defence;
		this.power = power;
	}

	// //// GETTERS ////////
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

	// /////////////////////

	// //// SETTERS ////////
	public void setHp(int hp) {
		this.hp = hp;
	}

	// /////////////////////

	// TAKE DAMAGE FUNCTION
	public void takeDamage(int damage) {
		if (damage > 0)
			this.hp -= damage;

		if (this.hp <= 0)
			owner.deathFunction();
	}

	// ATTACK ANOTHER ENTITY FUNCTION
	public void attack(Entity target) {
		int damage = this.power - target.getFighterComponent().getDefence();
		if (damage > 0) {
			// hit gowz in!
			MainGame.getInstance().newMessage(Helpers.capitalizeString(
					this.owner.getName()) + " hits " + Helpers.capitalizeString(target.getName())
							+ " for " + damage + " damage");
			target.getFighterComponent().takeDamage(damage);
		} else {
			// such much defence!
			MainGame.getInstance().newMessage(
					Helpers.capitalizeString(this.owner.getName()) + " hits " + Helpers.capitalizeString(target.getName())
							+ " but it has no effect!");
		}
	}
}
