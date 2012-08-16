package main;

import java.util.logging.Logger;

import net.slashie.libjcsi.CSIColor;

public class Entity {
	private static Logger log = Logger.getLogger(Entity.class.getName());
	private int x, y;
	private char key;
	private String name;
	private CSIColor color;
	private boolean blocks;
	private Fighter fighter;
	private Monster ai;

	public Entity(int x, int y, char key, String name, CSIColor color,
			Boolean blocks, Fighter fighter, Monster ai) {
		this.x = x;
		this.y = y;
		this.key = key;
		this.name = name;
		this.color = color;
		this.blocks = blocks;
		this.fighter = fighter;
		this.ai = ai;
	}

	public Entity(int x, int y, char key, String name, CSIColor color,
			Boolean blocks, Fighter fighter) {
		this.x = x;
		this.y = y;
		this.key = key;
		this.name = name;
		this.color = color;
		this.blocks = blocks;
		this.fighter = fighter;
	}

	public Entity(int x, int y, char key, String name, CSIColor color,
			Boolean blocks) {
		this.x = x;
		this.y = y;
		this.key = key;
		this.name = name;
		this.color = color;
		this.blocks = blocks;
	}

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

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void move(int dx, int dy) {
		if (!MapGenerator.getInstance().isBlocked(this.x + dx, this.y + dy)) {
			this.x += dx;
			this.y += dy;
		}
	}

	public void draw() {
		MainGame.getCSI().print(this.x, this.y, this.key, this.color);
	}

	public void clear() {
		MainGame.getCSI().print(this.x, this.y, ' ', this.color);
	}

	public void moveTowards(int target_x, int target_y) {
		int dx = target_x - this.x;
		int dy = target_y - this.y;
		double distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

		// #normalize it to length 1 (preserving direction), then round it and
		// #convert to integer so the movement is restricted to the map grid
		dx = (int) (Math.round(dx / distance));
		dy = (int) (Math.round(dy / distance));
		this.move(dx, dy);
	}

	public double distanceTo(Entity other) {
		int dx = other.x - this.x;
		int dy = other.y - this.y;

		return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
	}

	public void setFighterComponent(Fighter fighter) {
		this.fighter = fighter;
	}

	public void setAIComponent(Monster ai) {
		this.ai = ai;
	}

	public Fighter getFighterComponent() {
		return this.fighter;
	}

	public Monster getAIComponent() {
		return this.ai;
	}

	public void deathFunction() {
		String name = this.getName();
		if (name.equals("player")) {
			MainGame.getInstance().setGameState("dead");
			this.key = '%';
			this.color = CSIColor.DARK_RED;
		} else if ((name.equals("troll")) || (name.equals("orc"))) {
			this.key = '%';
			this.color = CSIColor.DARK_RED;
			this.blocks = false;
			this.fighter = null;
			this.ai = null;
			MainGame.getInstance().newMessage("--" + this.name + " dies");
			this.name = "remains";
		}
	}
}
