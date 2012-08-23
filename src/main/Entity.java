package main;

import main.components.AIComponent;
import main.components.FighterComponent;
import main.components.ItemComponent;
import main.helpers.Helpers;
import main.helpers.Message;
import net.slashie.libjcsi.CSIColor;

////////////////////////////////////////////////////////////////////////
/////// BASIC CLASS TO HANDLE ENTITIES SUCH AS AIComponent OR PLAYER ///////
////////////////////////////////////////////////////////////////////////
public class Entity {
	// //// LOGGER INSTANCE //////
	// private static Logger log = Logger.getLogger(Entity.class.getName());

	// //// PRIVATE PARAMETERS //////
	private int x, y; // position
	private char key; // char symbol
	private String name; // entity name
	private CSIColor color; // entity color
	private boolean blocks; // blocks movement or not
	private FighterComponent fighter; // fighter component
	private AIComponent ai; // ai component
	private ItemComponent item; // item component
	private int level = 1;

	// //// CREATE ENTITY WITH BOTH COMPONENTS /////
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

	// ///// CREATE ENTITY WITH FIGHTER COMPONENT //////
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

	// /////// CREATE ENTITY WITHOUT COMPONENTS /////////
	public Entity(int x, int y, char key, String name, CSIColor color,
			Boolean blocks) {
		this.x = x;
		this.y = y;
		this.key = key;
		this.name = name;
		this.color = color;
		this.blocks = blocks;
	}

	// //// GETTERS /////////
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public char getKey() {
		return key;
	}

	public boolean blocks() {
		return blocks;
	}

	public CSIColor getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

	public FighterComponent getFighterComponent() {
		return this.fighter;
	}

	public AIComponent getAIComponent() {
		return this.ai;
	}

	public ItemComponent getItemComponent() {
		return this.item;
	}

	public int getLevel() {
		return this.level;
	}

	// ////////////////////////

	// /// SETTERS/////////////

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setFighterComponent(FighterComponent fighter) {
		this.fighter = fighter;
	}

	public void setAIComponent(AIComponent ai) {
		this.ai = ai;
	}

	public void setItemComponent(ItemComponent item) {
		this.item = item;
	}

	public void incLevel() {
		this.level++;
	}

	// ////////////////////////

	// // FUNCTION TO MOVE ENTITY /////
	public void move(int dx, int dy) {
		if (!MainMap.getInstance().isBlocked(this.x + dx, this.y + dy)) {
			this.x += dx;
			this.y += dy;
		}
	}

	// // FUNCTION TO DRAW ENTITY /////
	public void draw() {
		int new_x = MainMap.getInstance().toCameraCoordX(this.x);
		int new_y = MainMap.getInstance().toCameraCoordY(this.y);
		// check if within camera range
		if ((new_x > 0) && (new_x < MainMap.CAMERA_WIDTH))
			if ((new_y > 0) && (new_y < MainMap.CAMERA_HEIGHT))
				MainGame.getCSI().print(new_x, new_y, this.key, this.color);
	}

	// // CREARING THE ENTITIES SPACE
	public void clear() {
		int new_x = MainMap.getInstance().toCameraCoordX(this.x);
		int new_y = MainMap.getInstance().toCameraCoordY(this.y);
		// check if within camera range
		if ((new_x > 0) && (new_x < MainMap.CAMERA_WIDTH))
			if ((new_y > 0) && (new_y < MainMap.CAMERA_HEIGHT))
				MainGame.getCSI().print(new_x, new_y, ' ', this.color);
	}

	// // FUNCTIONS TO MOVE ENTITY TOWARS TARGET LOCATION ////
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

	// /// FUNCTION TO COUNT DISTANCE TO OTHER ENTITY //////
	public double distanceTo(Entity other) {
		int dx = other.x - this.x;
		int dy = other.y - this.y;

		return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
	}

	// //// ACTIONS FOR ENTITIES DEATH /////
	public void deathFunction() {
		String name = this.getName();
		if (name.equals("player")) {
			MainGame.getInstance().setGameState("dead");
			this.key = '%';
			this.color = CSIColor.DARK_RED;
			Message message = new Message("YOU DIE, BWAAHAHHAAHHA!",
					CSIColor.BURGUNDY);
			MainGame.getInstance().newMessage(message);
		} else if ((name.equals("troll")) || (name.equals("orc"))) {
			this.key = '%';
			this.color = CSIColor.DARK_RED;
			this.blocks = false;
			this.fighter = null;
			this.ai = null;
			Message message = new Message(Helpers.capitalizeString(this.name)
					+ " dies", CSIColor.BURGUNDY);
			MainGame.getInstance().newMessage(message);
			this.name = "remains";
		}
	}
}
