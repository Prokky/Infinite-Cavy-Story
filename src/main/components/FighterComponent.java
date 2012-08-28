package main.components;

import main.MainGame;
import main.MainMap;
import main.helpers.Helpers;
import main.objects.ConfusedMonster;
import main.objects.Entity;
import net.slashie.libjcsi.CSIColor;

/**
 * Component containing fighter properties for entity.
 * 
 * @author prokk
 * 
 */
public class FighterComponent {
	// private static Logger log =
	// Logger.getLogger(FighterComponent.class.getName());

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

	/**
	 * 
	 * @param owner
	 *            comtaining this component
	 * @param hp
	 *            of entity
	 * @param xp
	 *            entity gives for kill
	 * @param defence
	 *            of entity
	 * @param power
	 *            - damage of entity
	 */
	public FighterComponent(Entity owner, int hp, int xp, int defence, int power) {
		this.owner = owner;
		this.max_hp = hp;
		this.xp = xp;
		this.hp = hp;
		this.defence = defence;
		this.power = power;
	}

	/**
	 * 
	 * @return max HP of entity
	 */
	public int getMaxHP() {
		return this.max_hp;
	}

	/**
	 * 
	 * @return max MANA of entity
	 */
	public int getMaxMana() {
		return this.maxmana;
	}

	/**
	 * 
	 * @return current mana of entity
	 */
	public int getMana() {
		return this.mana;
	}

	/**
	 * 
	 * @return current hp of entity
	 */
	public int getHp() {
		return this.hp;
	}

	/**
	 * 
	 * @return defence of entity
	 */
	public int getDefence() {
		return this.defence;
	}

	/**
	 * 
	 * @return power of entity
	 */
	public int getPower() {
		return this.power;
	}

	/**
	 * 
	 * @return XP for entities kill OR players current XP
	 */
	public int getXP() {
		return this.xp;
	}

	/**
	 * 
	 * @return constitution of player. Allmost equal his maximum XP
	 */
	public int getConstitution() {
		return this.constitution;
	}

	/**
	 * 
	 * @return intellect of player
	 */
	public int getIntellect() {
		return this.intellect;
	}

	/**
	 * 
	 * @param hp
	 *            of entity
	 */
	public void setHp(int hp) {
		this.hp = hp;
	}

	/**
	 * 
	 * @param mana
	 *            of entity
	 */
	public void setMana(int mana) {
		this.mana = mana;
	}

	/**
	 * 
	 * @param maxhp
	 *            of entity
	 */
	public void setMaxHp(int maxhp) {
		this.max_hp = maxhp;
	}

	/**
	 * 
	 * @param maxmana
	 *            of entity
	 */
	public void setMaxMana(int maxmana) {
		this.maxmana = maxmana;
	}

	/**
	 * 
	 * @param xp
	 *            of entity
	 */
	public void setXP(int xp) {
		this.xp = xp;
	}

	/**
	 * increase power of entity
	 * 
	 * @param inc
	 *            - add this number to power
	 */
	public void incPower(int inc) {
		this.power += inc;
	}

	/**
	 * increaset defence of entity
	 * 
	 * @param inc
	 *            - add this num to defence
	 */
	public void incDefence(int inc) {
		this.defence += inc;
	}

	/**
	 * increase intellect of entity
	 * 
	 * @param inc
	 *            - add this to intellect
	 */
	public void incIntellect(int inc) {
		this.intellect += inc;
	}

	/**
	 * intcreast constitution of entity
	 * 
	 * @param inc
	 *            - add this to constitution
	 */
	public void incConst(int inc) {
		this.constitution += inc;
	}

	/**
	 * Function for entity to take the damage
	 * 
	 * @param damage
	 */
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

	/**
	 * add xp to entity(player)
	 * 
	 * @param xp
	 */
	private void addXP(int xp2) {
		this.xp += xp2;

	}

	/**
	 * funtion to attack another entity
	 * 
	 * @param target
	 *            entity
	 */
	public void attack(Entity target) {
		int damage = this.power - target.getFighterComponent().getDefence();
		if (damage > 0) {
			// hit gowz in!
			MainGame.getInstance().newMessage(
					Helpers.capitalizeString(this.owner.getName()) + " hits "
							+ Helpers.capitalizeString(target.getName())
							+ " for " + damage + " damage", CSIColor.RED);
			target.getFighterComponent().takeDamage(damage);
		} else {
			// such much defence!
			MainGame.getInstance().newMessage(
					Helpers.capitalizeString(this.owner.getName()) + " hits "
							+ Helpers.capitalizeString(target.getName())
							+ " but it has no effect!", CSIColor.WHITE);
		}
	}

	/**
	 * Heal the owner entity
	 * 
	 * @param heal
	 *            - num hp to add using heal
	 */
	public void healFor(int heal) {
		this.hp += heal;
		if (this.hp > this.max_hp)
			this.hp = this.max_hp;
		MainGame.getInstance().newMessage(
				Helpers.capitalizeString(this.owner.getName())
						+ " is healed for " + heal, CSIColor.GREEN);
	}

	/**
	 * Add mana to the owner entity
	 * 
	 * @param mana
	 *            to add
	 */
	public void addMana(int mana) {
		this.mana += mana;
		if (this.mana > this.maxmana)
			this.mana = this.maxmana;
		MainGame.getInstance().newMessage(
				Helpers.capitalizeString(this.owner.getName()) + " gets "
						+ mana + " mana", CSIColor.BLUE);
	}

	/**
	 * Cast Lighning Function, if player uses the scroll
	 */
	public void castLighning() {
		Entity monster = MainMap.getInstance().getClosestMonster(
				ItemComponent.LIGHTNING_RANGE);
		if (monster == null) {
			MainGame.getInstance().newMessage(
					"No enemy is close enough to strike.", CSIColor.WHITE);
		} else {
			if (MainGame.getInstance().getPlayer().getFighterComponent()
					.getMana() >= ItemComponent.LIGHTNING_MANA) {
				MainGame.getInstance().newMessage(
						"A lightning strikes the "
								+ Helpers.capitalizeString(monster.getName())
								+ " for " + ItemComponent.LIGHTNING_DAMAGE
								+ " damage", CSIColor.AQUAMARINE);
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
						"Not enough mana to use Lightning", CSIColor.WHITE);
			}
		}
	}

	/**
	 * Cast Confuse Function, if player uses the scroll
	 */
	public void castConfuse() {

		Entity monster = MainMap.getInstance().getClosestMonster(
				ItemComponent.CONFUSION_RANGE);
		if (monster == null) {
			MainGame.getInstance().newMessage(
					"No enemy is close enough to confuse.", CSIColor.WHITE);
		} else {
			if (MainGame.getInstance().getPlayer().getFighterComponent()
					.getMana() >= ItemComponent.CONFUSION_MANA) {

				AIComponent old_ai = monster.getAIComponent();
				monster.setAIComponent(new ConfusedMonster(monster, old_ai,
						ItemComponent.CONFUSION_TURNS));
				monster.getAIComponent().setOwner(monster);
				MainGame.getInstance().newMessage(
						"The eyes of the "
								+ Helpers.capitalizeString(monster.getName())
								+ " look vacant, as he stumbles around!",
						CSIColor.CERULEAN);
				MainGame.getInstance()
						.getPlayer()
						.getFighterComponent()
						.setMana(
								MainGame.getInstance().getPlayer()
										.getFighterComponent().getMana()
										- ItemComponent.CONFUSION_MANA);

			} else {
				MainGame.getInstance().newMessage(
						"Not enough mana to use Confusion", CSIColor.WHITE);
			}
		}
	}
}
