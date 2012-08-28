package main.objects;

import java.util.Random;

import main.MainGame;
import main.components.AIComponent;
import main.helpers.Helpers;
import net.slashie.libjcsi.CSIColor;

/**
 * Class to hangle monsters, charmed with Confusion Scroll. Extends AIComponent
 * 
 * @author prokk
 * 
 */
public class ConfusedMonster extends AIComponent {
	private Entity owner;
	private AIComponent old_ai;
	private int num_turns;

	/**
	 * 
	 * @param owner
	 *            of this component
	 * @param old_ai
	 *            - previous AIComponent
	 * @param num_turns
	 *            - number of confused turns
	 */
	public ConfusedMonster(Entity owner, AIComponent old_ai, int num_turns) {
		super(owner);
		this.owner = owner;
		this.old_ai = old_ai;
		this.num_turns = num_turns;
	}

	/**
	 * Overrides the AIComponent function with random movement takeTurn
	 */
	public void takeTurn() {
		Random rand = new Random();
		if (this.num_turns > 0) {
			this.owner.move(rand.nextInt(3) - 1, rand.nextInt(3) - 1);
			this.num_turns--;
		} else {
			this.owner.setAIComponent(old_ai);
			MainGame.getInstance().newMessage(
					"The " + Helpers.capitalizeString(owner.getName())
							+ " is no longer confused!", CSIColor.AMETHYST);
		}
	}
}
