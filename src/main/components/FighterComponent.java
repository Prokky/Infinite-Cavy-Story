package main.components;

import main.ConfusedMonster;
import main.Entity;
import main.MainGame;
import main.MainMap;
import main.helpers.Helpers;
import main.helpers.Message;
import net.slashie.libjcsi.CSIColor;

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
	private int xp; // xp
	private int intellect = 1; // intellect for player
	private int constitution = 1; // const for player
	private int maxmana = 20; // mana for player
	private int mana = 20; // mana

	// /// FIGHTER COMPONENT INITIATED WITH OWNER, HP, DEFENCE AND PAWA! ///////
	public FighterComponent(Entity owner, int hp, int xp, int defence, int power) {
		this.owner = owner;
		this.max_hp = hp;
		this.xp = xp;
		this.hp = hp;
		this.defence = defence;
		this.power = power;
	}

	// //// GETTERS ////////
	public int getMaxHP() {
		return this.max_hp;
	}

	public int getMaxMana() {
		return this.maxmana;
	}

	public int getMana() {
		return this.mana;
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

	public int getXP() {
		return this.xp;
	}

	public int getConstitution() {
		return this.constitution;
	}

	public int getIntellect() {
		return this.intellect;
	}

	// /////////////////////

	// //// SETTERS ////////
	public void setHp(int hp) {
		this.hp = hp;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public void setMaxHp(int maxhp) {
		this.max_hp = maxhp;
	}

	public void setMaxMana(int maxmana) {
		this.maxmana = maxmana;
	}

	public void setXP(int xp) {
		this.xp = xp;
	}

	public void incPower(int inc) {
		this.power += inc;
	}

	public void incDefence(int inc) {
		this.defence += inc;
	}

	public void incIntellect(int inc) {
		this.intellect += inc;
	}

	public void incConst(int inc) {
		this.constitution += inc;
	}

	// /////////////////////

	// TAKE DAMAGE FUNCTION
	public void takeDamage(int damage) {
		if (damage > 0)
			this.hp -= damage;

		if (this.hp <= 0) {
			if (this.owner != MainGame.getInstance().getPlayer())
				MainGame.getInstance().getPlayer().getFighterComponent()
						.addXP(owner.getFighterComponent().getXP());
			owner.deathFunction();
		}
	}

	private void addXP(int xp2) {
		this.xp += xp2;

	}

	// ATTACK ANOTHER ENTITY FUNCTION
	public void attack(Entity target) {
		int damage = this.power - target.getFighterComponent().getDefence();
		if (damage > 0) {
			// hit gowz in!
			Message message = new Message(Helpers.capitalizeString(this.owner
					.getName())
					+ " hits "
					+ Helpers.capitalizeString(target.getName())
					+ " for "
					+ damage + " damage", CSIColor.RED);
			MainGame.getInstance().newMessage(message);
			target.getFighterComponent().takeDamage(damage);
		} else {
			// such much defence!
			Message message = new Message(Helpers.capitalizeString(this.owner
					.getName())
					+ " hits "
					+ Helpers.capitalizeString(target.getName())
					+ " but it has no effect!", CSIColor.WHITE);
			MainGame.getInstance().newMessage(message);
		}
	}

	public void healFor(int heal) {
		this.hp += heal;
		if (this.hp > this.max_hp)
			this.hp = this.max_hp;
		Message message = new Message(Helpers.capitalizeString(this.owner
				.getName()) + " is healed for " + heal, CSIColor.GREEN);
		MainGame.getInstance().newMessage(message);
	}

	public void addMana(int mana) {
		this.mana += mana;
		if (this.mana > this.maxmana)
			this.mana = this.maxmana;
		Message message = new Message(Helpers.capitalizeString(this.owner
				.getName()) + " gets " + mana + " mana", CSIColor.BLUE);
		MainGame.getInstance().newMessage(message);
	}

	public void castLighning() {
		Entity monster = MainMap.getInstance().getClosestMonster(
				ItemComponent.LIGHTNING_RANGE);
		if (monster == null) {
			MainGame.getInstance().newMessage(
					new Message("No enemy is close enough to strike.",
							CSIColor.WHITE));
		} else {
			if (MainGame.getInstance().getPlayer().getFighterComponent()
					.getMana() >= ItemComponent.LIGHTNING_MANA) {
				Message message = new Message("A lightning strikes the "
						+ Helpers.capitalizeString(monster.getName()) + " for "
						+ ItemComponent.LIGHTNING_DAMAGE + " damage",
						CSIColor.AQUAMARINE);
				MainGame.getInstance().newMessage(message);
				monster.getFighterComponent().takeDamage(
						ItemComponent.LIGHTNING_DAMAGE);
				MainGame.getInstance()
						.getPlayer()
						.getFighterComponent()
						.setMana(
								MainGame.getInstance().getPlayer()
										.getFighterComponent().getMana()
										- ItemComponent.LIGHTNING_MANA);
			} else {
				MainGame.getInstance().newMessage(
						new Message("Not enough mana to use Lightning",
								CSIColor.WHITE));
			}
		}
	}

	public void castConfuse() {

		Entity monster = MainMap.getInstance().getClosestMonster(
				ItemComponent.CONFUSION_RANGE);
		if (monster == null) {
			MainGame.getInstance().newMessage(
					new Message("No enemy is close enough to confuse.",
							CSIColor.WHITE));
		} else {
			if (MainGame.getInstance().getPlayer().getFighterComponent()
					.getMana() >= ItemComponent.CONFUSION_MANA) {

				AIComponent old_ai = monster.getAIComponent();
				monster.setAIComponent(new ConfusedMonster(monster, old_ai,
						ItemComponent.CONFUSION_TURNS));
				monster.getAIComponent().setOwner(monster);
				Message message = new Message("The eyes of the "
						+ Helpers.capitalizeString(monster.getName())
						+ " look vacant, as he stumbles around!",
						CSIColor.CERULEAN);
				MainGame.getInstance().newMessage(message);
				MainGame.getInstance()
						.getPlayer()
						.getFighterComponent()
						.setMana(
								MainGame.getInstance().getPlayer()
										.getFighterComponent().getMana()
										- ItemComponent.CONFUSION_MANA);

			} else {
				MainGame.getInstance().newMessage(
						new Message("Not enough mana to use Confusion",
								CSIColor.WHITE));
			}
		}
	}
}
