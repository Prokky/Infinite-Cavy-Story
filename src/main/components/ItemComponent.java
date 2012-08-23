package main.components;

import main.Entity;
import main.MainGame;
import main.MainMap;
import main.helpers.Helpers;
import main.helpers.Message;
import net.slashie.libjcsi.CSIColor;

public class ItemComponent {
	public final static int HEALING_POTION = 1;
	public final static int MANA_POTION = 2;
	public final static int LIGHTNING = 3;
	public final static int CONFUSION = 4;
	
	public final static int POTION_HEAL = 10;
	public final static int POTION_MANA = 10;
	
	public final static int LIGHTNING_DAMAGE = 20;
	public final static int LIGHTNING_RANGE = 5;
	public final static int LIGHTNING_MANA = 5;
	
	public final static int CONFUSION_TURNS = 5;
	public final static int CONFUSION_RANGE = 3;
	public final static int CONFUSION_MANA = 10;

	// /// COMPONENTS OWNER ////
	private Entity owner;

	private int usage;

	// // GET OWNER ////
	public ItemComponent(Entity owner, int usage) {
		this.owner = owner;
		this.usage = usage;
	}

	public void useItem() {
		switch (usage) {
		case HEALING_POTION: {
			MainGame.getInstance().getPlayer().getFighterComponent().healFor(POTION_HEAL);
			break;
		}
		case MANA_POTION:{
			MainGame.getInstance().getPlayer().getFighterComponent().addMana(POTION_MANA);
			break;
		}
		case LIGHTNING: {
			MainGame.getInstance().getPlayer().getFighterComponent()
					.castLighning();
			break;
		}
		case CONFUSION: {
			MainGame.getInstance().getPlayer().getFighterComponent()
					.castConfuse();
			break;
		}
		}
		MainMap.getInstance().getInventory().remove(this.owner);
	}

	public void pickUp() {
		if (MainMap.getInstance().getInventory().size() > 12)
			MainGame.getInstance().newMessage(new Message("Your inventory is full!", CSIColor.WHITE));
		else {
			MainMap.getInstance().getInventory().add(this.owner);
			MainMap.getInstance().getObjects().remove(this.owner);
			MainGame.getInstance().newMessage(new Message(
					"You picked up "
							+ Helpers.capitalizeString(owner.getName()),CSIColor.VIOLET));
		}
	}

	public void dropItem() {
		MainMap.getInstance().getObjects().add(this.owner);
		MainMap.getInstance().getInventory().remove(this.owner);
		this.owner.setX(MainGame.getInstance().getPlayer().getX());
		this.owner.setY(MainGame.getInstance().getPlayer().getY());
		MainGame.getInstance().newMessage(new Message("You dropped " + Helpers.capitalizeString(this.owner.getName()),CSIColor.VIOLET));
	}
}
