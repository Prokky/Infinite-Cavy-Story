package main.components;

import main.MainGame;
import main.objects.Entity;

/**
 * Component class containing AI for Entities. Call takeTurn function.
 * 
 * @author prokk
 */
public class AIComponent
{
	// private static Logger log =
	// Logger.getLogger(AIComponent.class.getName());

	private Entity	owner;

	/**
	 * 
	 * @param owner
	 *            - entity which contains this component
	 */
	public AIComponent(Entity owner)
	{
		this.owner = owner;
	}

	/**
	 * AI for entity.
	 */
	public void takeTurn()
	{
		Entity player = MainGame.getInstance().getPlayer();

		// if too far to hit - move towards target
		// else hit the target using fighter component
		if ((owner.distanceTo(player) < 5) && (owner.distanceTo(player) >= 2))
			owner.moveTowards(player.getX(), player.getY());
		else
			if (owner.distanceTo(player) < 2)
				if (player.getFighterComponent().getHp() > 0)
					owner.getFighterComponent().attack(player);
	}

	/**
	 * 
	 * @param owner
	 *            for the component
	 */
	public void setOwner(Entity owner)
	{
		this.owner = owner;
	}
}
