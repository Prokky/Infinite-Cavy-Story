package main;

import java.util.Random;

import main.components.AIComponent;
import main.helpers.Helpers;
import main.helpers.Message;
import net.slashie.libjcsi.CSIColor;

public class ConfusedMonster extends AIComponent {
	private Entity owner;
	private AIComponent old_ai;
	private int num_turns;

	public ConfusedMonster(Entity owner, AIComponent old_ai, int num_turns) {
		super(owner);
		this.owner = owner;
		this.old_ai = old_ai;
		this.num_turns = num_turns;
	}

	public void takeTurn() {
		Random rand = new Random();
		if (this.num_turns > 0) {
			this.owner.move(rand.nextInt(3) - 1, rand.nextInt(3) - 1);
			this.num_turns--;
		} else {
			this.owner.setAIComponent(old_ai);
			Message message = new Message(
					"The " + Helpers.capitalizeString(owner.getName())
							+ " is no longer confused!", CSIColor.AMETHYST);
			MainGame.getInstance().newMessage(message);
		}
	}
}
