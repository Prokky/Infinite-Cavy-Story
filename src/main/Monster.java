package main;

import java.util.logging.Logger;

public class Monster {
	private static Logger log = Logger.getLogger(Monster.class.getName());
	private Entity owner;

	public Monster(Entity owner) {
		this.owner = owner;
	}

	public void takeTurn() {
		Entity player = MainGame.getInstance().getPlayer();
		if ((owner.distanceTo(player) < 5) && (owner.distanceTo(player) >= 2))
			owner.moveTowards(player.getX(), player.getY());
		else if (owner.distanceTo(player) < 2)
			if (player.getFighterComponent().getHp() > 0)
				owner.getFighterComponent().attack(player);
	}
}