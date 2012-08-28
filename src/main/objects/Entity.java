package main.objects;

import main.MainGame;
import main.MainMap;
import main.components.AIComponent;
import main.components.FighterComponent;
import main.components.ItemComponent;
import main.helpers.Helpers;
import net.slashie.libjcsi.CSIColor;

/**
 * Basic class to handle with entities(monsters,items)
 * 
 * @author prokk
 * 
 */
public class Entity {
	// private static Logger log = Logger.getLogger(Entity.class.getName());

	private int x, y; // position
	private char key; // char symbol
	private String name; // entity name
	private CSIColor color; // entity color
	private boolean blocks; // blocks movement or not
	private FighterComponent fighter; // fighter component
	private AIComponent ai; // ai component
	private ItemComponent item; // item component
	private int level = 1;

	/**
	 * 
	 * @param x
	 *            position
	 * @param y
	 *            position
	 * @param key
	 *            - symbol presentation
	 * @param name
	 * @param color
	 *            of symbol
	 * @param blocks
	 *            - can pass through
	 * @param fighter
	 *            component, describing combat properties
	 * @param ai
	 *            component
	 */
	public Entity(int x, int y, char key, String name, CSIColor color,
			Boolean blocks, FighterComponent fighter, AIComponent ai) {
		this.x = x;
		this.y = y;
		this.key = key;
		this.name = name;
		this.color = color;
		this.blocks = blocks;
		this.fighter = fighter;
		this.ai = ai;
	}

	/**
	 * 
	 * @param x
	 *            position
	 * @param y
	 *            position
	 * @param key
	 *            - symbol presentation
	 * @param name
	 * @param color
	 *            of symbol
	 * @param blocks
	 *            - can pass through
	 * @param fighter
	 *            component, describing combat properties
	 */
	public Entity(int x, int y, char key, String name, CSIColor color,
			Boolean blocks, FighterComponent fighter) {
		this.x = x;
		this.y = y;
		this.key = key;
		this.name = name;
		this.color = color;
		this.blocks = blocks;
		this.fighter = fighter;
	}

	/**
	 * 
	 * @param x
	 *            position
	 * @param y
	 *            position
	 * @param key
	 *            - symbol presentation
	 * @param name
	 * @param color
	 *            of symbol
	 * @param blocks
	 *            - can pass through
	 */
	public Entity(int x, int y, char key, String name, CSIColor color,
			Boolean blocks) {
		this.x = x;
		this.y = y;
		this.key = key;
		this.name = name;
		this.color = color;
		this.blocks = blocks;
	}

	/**
	 * 
	 * @return x position of entity
	 */
	public int getX() {
		return x;
	}

	/**
	 * 
	 * @return y position of entity
	 */
	public int getY() {
		return y;
	}

	/**
	 * 
	 * @return symbol of entity
	 */
	public char getKey() {
		return key;
	}

	/**
	 * 
	 * @return can pass through
	 */
	public boolean blocks() {
		return blocks;
	}

	/**
	 * 
	 * @return color of symbol
	 */
	public CSIColor getColor() {
		return color;
	}

	/**
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return fighter component, can be NULL
	 */
	public FighterComponent getFighterComponent() {
		return this.fighter;
	}

	/**
	 * 
	 * @return AI component, can be NULL
	 */
	public AIComponent getAIComponent() {
		return this.ai;
	}

	/**
	 * 
	 * @return Item component, can be NULL
	 */
	public ItemComponent getItemComponent() {
		return this.item;
	}

	/**
	 * 
	 * @return level of entity. Only for player
	 */
	public int getLevel() {
		return this.level;
	}

	/**
	 * 
	 * @param x
	 *            coord
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * 
	 * @param y
	 *            coord
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * 
	 * @param fighter
	 *            component, contains all combat properties
	 */
	public void setFighterComponent(FighterComponent fighter) {
		this.fighter = fighter;
	}

	/**
	 * 
	 * @param ai
	 *            component
	 */
	public void setAIComponent(AIComponent ai) {
		this.ai = ai;
	}

	/**
	 * 
	 * @param item
	 *            component
	 */
	public void setItemComponent(ItemComponent item) {
		this.item = item;
	}

	/**
	 * increase level by 1
	 */
	public void incLevel() {
		this.level++;
	}

	/**
	 * Move the entity by distance
	 * 
	 * @param dx
	 *            distance
	 * @param dy
	 *            distance
	 */
	public void move(int dx, int dy) {
		if (!MainMap.getInstance().isBlocked(this.x + dx, this.y + dy)) {
			this.x += dx;
			this.y += dy;
		}
	}

	/**
	 * Draw entity on map, if the entity is in camera range
	 */
	public void draw() {
		int new_x = MainMap.getInstance().toCameraCoordX(this.x);
		int new_y = MainMap.getInstance().toCameraCoordY(this.y);
		// check if within camera range
		if ((new_x > 0) && (new_x < MainMap.CAMERA_WIDTH))
			if ((new_y > 0) && (new_y < MainMap.CAMERA_HEIGHT))
				MainGame.getCSI().print(new_x, new_y, this.key, this.color);
	}

	/**
	 * Clear entity from map, if the entity is in camera range
	 */
	public void clear() {
		int new_x = MainMap.getInstance().toCameraCoordX(this.x);
		int new_y = MainMap.getInstance().toCameraCoordY(this.y);
		// check if within camera range
		if ((new_x > 0) && (new_x < MainMap.CAMERA_WIDTH))
			if ((new_y > 0) && (new_y < MainMap.CAMERA_HEIGHT))
				MainGame.getCSI().print(new_x, new_y, ' ', this.color);
	}

	/**
	 * Move the entity towards another coord
	 * 
	 * @param target_x
	 *            coord
	 * @param target_y
	 *            coord
	 */
	public void moveTowards(int target_x, int target_y) {
		int dx = target_x - this.x;
		int dy = target_y - this.y;
		double distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

		// normalize it to length 1 (preserving direction), then round it and
		// convert to integer so the movement is restricted to the map grid
		dx = (int) (Math.round(dx / distance));
		dy = (int) (Math.round(dy / distance));
		this.move(dx, dy);
	}

	/**
	 * Count distance to target Entity
	 * 
	 * @param other
	 *            entity
	 * @return distance
	 */
	public double distanceTo(Entity other) {
		int dx = other.x - this.x;
		int dy = other.y - this.y;

		return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
	}

	/**
	 * Fuction describes death of this entity
	 */
	public void deathFunction() {
		String name = this.getName();
		if (name.equals("player")) {
			MainGame.getInstance().setGameState("dead");
			this.key = '%';
			this.color = CSIColor.DARK_RED;
			MainGame.getInstance().newMessage("YOU DIE, BWAAHAHHAAHHA!",
					CSIColor.BURGUNDY);
		} else if ((name.equals("troll")) || (name.equals("orc"))) {
			this.key = '%';
			this.color = CSIColor.DARK_RED;
			this.blocks = false;
			this.fighter = null;
			this.ai = null;
			MainGame.getInstance().newMessage(
					Helpers.capitalizeString(this.name) + " dies",
					CSIColor.BURGUNDY);
			this.name = "remains";
		}
	}
}
