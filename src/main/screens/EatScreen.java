package main.screens;

import main.entities.Creature;
import main.items.Item;

public class EatScreen extends InventoryBasedScreen {

	public EatScreen(Creature player) {
		super(player);
	}

	protected String getVerb() {
		return "eat";
	}

	protected boolean isAcceptable(Item item) {
		return item.foodValue() != 0;
	}

	protected Screen use(Item item) {
		player.eat(item);
		return null;
	}
}
