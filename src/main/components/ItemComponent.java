package main.components;

import main.MainGame;
import main.MainMap;
import main.helpers.Helpers;
import main.objects.Entity;
import net.slashie.libjcsi.CSIColor;

/**
 * Component for Entities to describe items
 * 
 * @author prokk
 * 
 */
public class ItemComponent
{
	public final static int	HEALING_POTION		= 1;
	public final static int	MANA_POTION			= 2;
	public final static int	LIGHTNING			= 3;
	public final static int	CONFUSION			= 4;

	public final static int	POTION_HEAL			= 10;
	public final static int	POTION_MANA			= 10;

	public final static int	LIGHTNING_DAMAGE	= 20;
	public final static int	LIGHTNING_RANGE		= 5;
	public final static int	LIGHTNING_MANA		= 5;

	public final static int	CONFUSION_TURNS		= 5;
	public final static int	CONFUSION_RANGE		= 3;
	public final static int	CONFUSION_MANA		= 10;

	private Entity			owner;

	private int				usage;

	/**
	 * 
	 * @param owner
	 *            of this component
	 * @param usage
	 *            - type of item
	 */
	public ItemComponent(Entity owner, int usage)
	{
		this.owner = owner;
		this.usage = usage;
	}

	/**
	 * use the current item, effect depends on usage type
	 */
	public void useItem()
	{
		switch (usage)
		{
		case HEALING_POTION:
		{
			MainGame.getInstance().getPlayer().getFighterComponent().healFor(POTION_HEAL);
			break;
		}
		case MANA_POTION:
		{
			MainGame.getInstance().getPlayer().getFighterComponent().addMana(POTION_MANA);
			break;
		}
		case LIGHTNING:
		{
			MainGame.getInstance().getPlayer().getFighterComponent().castLighning();
			break;
		}
		case CONFUSION:
		{
			MainGame.getInstance().getPlayer().getFighterComponent().castConfuse();
			break;
		}
		}
		MainGame.getInstance().getInventory().remove(this.owner);
	}

	/**
	 * pickup item from floor
	 */
	public void pickUp()
	{
		if (MainGame.getInstance().getInventory().size() > 12)
			MainGame.getInstance().newMessage("Your inventory is full!", CSIColor.WHITE);
		else
		{
			MainGame.getInstance().getInventory().add(this.owner);
			MainMap.getInstance().getObjects().remove(this.owner);
			MainGame.getInstance().newMessage("You picked up " + Helpers.capitalizeString(owner.getName()), CSIColor.VIOLET);
		}
	}

	/**
	 * drop item on the floor
	 */
	public void dropItem()
	{
		MainMap.getInstance().getObjects().add(this.owner);
		MainGame.getInstance().getInventory().remove(this.owner);
		this.owner.setX(MainGame.getInstance().getPlayer().getX());
		this.owner.setY(MainGame.getInstance().getPlayer().getY());
		MainGame.getInstance().newMessage("You dropped " + Helpers.capitalizeString(this.owner.getName()), CSIColor.VIOLET);
	}
}
