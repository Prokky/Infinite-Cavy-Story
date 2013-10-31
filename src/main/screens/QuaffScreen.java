package main.screens;

import main.entities.Creature;
import main.items.Item;

public class QuaffScreen extends InventoryBasedScreen {

	public QuaffScreen(Creature player) {
		super(player);
	}

	protected String getVerb() {
		return "quaff";
	}

	protected boolean isAcceptable(Item item) {
		return item.quaffEffect() != null;
	}

	protected Screen use(Item item) {
		player.quaff(item);
		return null;
	}
}
