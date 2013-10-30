package main.screens;

import main.entities.Creature;
import main.items.Item;

public class ExamineScreen extends InventoryBasedScreen {

	public ExamineScreen(Creature player) {
		super(player);
	}

	protected String getVerb() {
		return "examine";
	}

	protected boolean isAcceptable(Item item) {
		return true;
	}

	protected Screen use(Item item) {
		String article = "aeiou".contains(item.name().subSequence(0, 1)) ? "an "
				: "a ";
		player.notify("It's " + article + item.name() + "." + item.details());
		return null;
	}
}
