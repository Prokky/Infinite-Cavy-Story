package main.components;

import main.Entity;
import main.MainGame;

public class AIComponent {
	// // LOGGER INSTANCE ////
	// private static Logger log =
	// Logger.getLogger(AIComponent.class.getName());

	// /// COMPONENTS OWNER ////
	private Entity owner;

	// // GET OWNER ////
	public AIComponent(Entity owner) {
		this.owner = owner;
	}

	// // BASIC MONSTER AI ////
	public void takeTurn() {
		Entity player = MainGame.getInstance().getPlayer();

		// if too far to hit - move towards target
		// else hit the target using fighter component
		if ((owner.distanceTo(player) < 5) && (owner.distanceTo(player) >= 2))
			owner.moveTowards(player.getX(), player.getY());
		else if (owner.distanceTo(player) < 2)
			if (player.getFighterComponent().getHp() > 0)
				owner.getFighterComponent().attack(player);
	}
}
