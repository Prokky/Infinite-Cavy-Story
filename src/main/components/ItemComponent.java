package main.components;

import main.Entity;
import main.MainGame;
import main.MainMap;
import main.helpers.Helpers;

public class ItemComponent {
	public final static int HEALING_POTION = 1;

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
		case 1:
			MainGame.getInstance().getPlayer().getFighterComponent().healFor(5);
			break;
		}
		MainMap.getInstance().getInventory().remove(this.owner);
	}

	public void pickUp() {
		if (MainMap.getInstance().getInventory().size() > 12)
			MainGame.getInstance().newMessage("Your inventory is full!");
		else {
			MainMap.getInstance().getInventory().add(this.owner);
			MainMap.getInstance().getObjects().remove(this.owner);
			MainGame.getInstance()
					.newMessage(
							"You picked up "
									+ Helpers.capitalizeString(owner.getName()));
		}
	}
}
