package main.screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import main.Effect;
import main.FieldOfView;
import main.StuffFactory;
import main.entities.Creature;
import main.items.Item;
import main.world.Tile;
import main.world.World;
import main.world.WorldBuilder;
import asciiPanel.AsciiPanel;

public class PlayScreen implements Screen {
	private World world;
	private Creature player;
	private int screenWidth;
	private int screenHeight;
	private List<String> messages;
	private FieldOfView fov;
	private Screen subscreen;

	public PlayScreen() {
		screenWidth = 70;
		screenHeight = 40;
		messages = new ArrayList<String>();
		createWorld();
		fov = new FieldOfView(world);

		StuffFactory factory = new StuffFactory(world);
		createCreatures(factory);
		createItems(factory);
	}

	private void createCreatures(StuffFactory factory) {
		player = factory.newPlayer(messages, fov);

		for (int z = 0; z < world.depth(); z++) {
			for (int i = 0; i < 4; i++) {
				factory.newFungus(z);
			}
			for (int i = 0; i < 10; i++) {
				factory.newBat(z);
			}
			for (int i = 0; i < z * 2 + 1; i++) {
				factory.newZombie(z, player);
				factory.newGoblin(z, player);
			}
		}
	}

	private void createItems(StuffFactory factory) {
		for (int z = 0; z < world.depth(); z++) {
			for (int i = 0; i < world.width() * world.height() / 50; i++) {
				factory.newRock(z);
			}

			factory.newFruit(z);
			factory.newEdibleWeapon(z);
			factory.newBread(z);
			factory.randomArmor(z);
			factory.randomWeapon(z);
			factory.randomWeapon(z);

			for (int i = 0; i < z + 1; i++) {
				factory.randomPotion(z);
				factory.randomSpellBook(z);
			}
		}
		factory.newBlueMagesSpellbook(1);
		factory.newVictoryItem(world.depth() - 1);
	}

	private void createWorld() {
		world = new WorldBuilder(150, 100, 10).makeCaves().build();
	}

	public int getScrollX() {
		return Math.max(
				0,
				Math.min(player.x - screenWidth / 2, world.width()
						- screenWidth));
	}

	public int getScrollY() {
		return Math.max(
				0,
				Math.min(player.y - screenHeight / 2, world.height()
						- screenHeight));
	}

	@Override
	public void displayOutput(AsciiPanel terminal) {
		int left = getScrollX();
		int top = getScrollY();

		displayTiles(terminal, left, top);
		displayMessages(terminal, messages);

		String hp = String.format("%d/%d hp", player.hp(), player.maxHp());
		String mana = String.format("%d/%d mana", player.mana(),
				player.maxMana());
		String hunger = String.format("%s", hunger());

		Color hpColor = Color.GREEN;
		if (player.hp() / player.maxHp() < 0.5)
			hpColor = Color.YELLOW;
		else if (player.hp() / player.maxHp() < 0.3)
			hpColor = Color.RED;
		terminal.write(hp, 71, 1, hpColor);
		terminal.write(mana, 71, 2, Color.CYAN);

		Color hungerColor = Color.WHITE;
		if (hunger.equals("Hungry"))
			hungerColor = Color.YELLOW;
		else if (hunger.equals("Starving"))
			hungerColor = Color.RED;
		terminal.write("Satiety:" + hunger, 71, 3, hungerColor);

		terminal.write("============================", 71, 4);

		terminal.write("Weapon:" + getWeaponName(), 71, 5);
		terminal.write("Armor:" + getArmorName(), 71, 6);

		terminal.write("============================", 71, 7);

		terminal.write("Attack:" + player.attackValue(), 71, 8);
		terminal.write("Defense:" + player.defenseValue(), 71, 9);
		terminal.write("Vision:" + player.visionRadius(), 71, 10);

		String hpreg = String.format(Locale.US, "HP Reg per turn:%.2f",
				(float) player.hpRegenerationRate() / 1000);
		String manareg = String.format(Locale.US, "Mana Reg per turn:%.2f",
				(float) player.manaRegenRate() / 1000);

		terminal.write(hpreg, 71, 11);
		terminal.write(manareg, 71, 12);

		terminal.write("============================", 71, 13);

		terminal.write("Effects:", 71, 14);
		int x = 15;

		if (player.effects().size() == 0)
			terminal.write("None", 71, 15);
		else {
			for (Effect effect : player.effects()) {
				String effectText = String.format("%s %d left", effect.name(),
						effect.duration());
				terminal.write(effectText, 71, x);
				x++;
			}
		}

		displayHelp(terminal);

		if (subscreen != null)
			subscreen.displayOutput(terminal);
	}

	private String hunger() {
		if (player.food() < player.maxFood() * 0.10)
			return "Starving";
		else if (player.food() < player.maxFood() * 0.25)
			return "Hungry";
		else if (player.food() > player.maxFood() * 0.90)
			return "Stuffed";
		else if (player.food() > player.maxFood() * 0.75)
			return "Full";
		else
			return "Fine";
	}

	private String getArmorName() {
		if (player.armor() == null)
			return "None";
		else
			return player.armor().name();
	}

	private String getWeaponName() {
		if (player.weapon() == null)
			return "None";
		else
			return player.weapon().name();
	}

	private void displayMessages(AsciiPanel terminal, List<String> messages) {
		int top = 42 - messages.size();
		for (int i = 0; i < messages.size(); i++) {
			terminal.writeCenter(messages.get(i), top + i);
		}
		if (subscreen == null)
			messages.clear();
	}

	private void displayHelp(AsciiPanel terminal) {
		int y = 29;
		terminal.write("============================", 71, y++);
		terminal.write("Controls:", 71, y++);
		terminal.write("[>][<] to move down and up", 71, y++);
		terminal.write("[g] to pick up", 71, y++);
		terminal.write("[d] to drop", 71, y++);
		terminal.write("[e] to eat", 71, y++);
		terminal.write("[w] to wear or wield", 71, y++);
		terminal.write("[x] to examine your items", 71, y++);
		terminal.write("[;] to look around", 71, y++);
		terminal.write("[t] to throw an item", 71, y++);
		terminal.write("[q] to quaff a potion", 71, y++);
		terminal.write("[r] to read something", 71, y++);
		terminal.write("============================", 71, y++);
	}

	private void displayTiles(AsciiPanel terminal, int left, int top) {
		fov.update(player.x, player.y, player.z, player.visionRadius());

		for (int x = 0; x < screenWidth; x++) {
			for (int y = 0; y < screenHeight; y++) {
				int wx = x + left;
				int wy = y + top;

				if (player.canSee(wx, wy, player.z))
					terminal.write(world.glyph(wx, wy, player.z), x, y,
							world.color(wx, wy, player.z));
				else
					terminal.write(fov.tile(wx, wy, player.z).glyph(), x, y,
							Color.darkGray);
			}
		}
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		int level = player.level();

		if (subscreen != null) {
			subscreen = subscreen.respondToUserInput(key);
		} else {
			switch (key.getKeyCode()) {
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_H:
				player.moveBy(-1, 0, 0);
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_L:
				player.moveBy(1, 0, 0);
				break;
			case KeyEvent.VK_UP:
			case KeyEvent.VK_K:
				player.moveBy(0, -1, 0);
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_J:
				player.moveBy(0, 1, 0);
				break;
			case KeyEvent.VK_Y:
				player.moveBy(-1, -1, 0);
				break;
			case KeyEvent.VK_U:
				player.moveBy(1, -1, 0);
				break;
			case KeyEvent.VK_B:
				player.moveBy(-1, 1, 0);
				break;
			case KeyEvent.VK_N:
				player.moveBy(1, 1, 0);
				break;
			case KeyEvent.VK_D:
				subscreen = new DropScreen(player);
				break;
			case KeyEvent.VK_E:
				subscreen = new EatScreen(player);
				break;
			case KeyEvent.VK_W:
				subscreen = new EquipScreen(player);
				break;
			case KeyEvent.VK_X:
				subscreen = new ExamineScreen(player);
				break;
			case KeyEvent.VK_SEMICOLON:
				subscreen = new LookScreen(player, "Looking", player.x
						- getScrollX(), player.y - getScrollY());
				break;
			case KeyEvent.VK_T:
				subscreen = new ThrowScreen(player, player.x - getScrollX(),
						player.y - getScrollY());
				break;
			case KeyEvent.VK_F:
				if (player.weapon() == null
						|| player.weapon().rangedAttackValue() == 0)
					player.notify("You don't have a ranged weapon equiped.");
				else
					subscreen = new FireWeaponScreen(player, player.x
							- getScrollX(), player.y - getScrollY());
				break;
			case KeyEvent.VK_Q:
				subscreen = new QuaffScreen(player);
				break;
			case KeyEvent.VK_R:
				subscreen = new ReadScreen(player, player.x - getScrollX(),
						player.y - getScrollY());
				break;
			}

			switch (key.getKeyChar()) {
			case 'g':
			case ',':
				player.pickup();
				break;
			case '<':
				if (userIsTryingToExit())
					return userExits();
				else
					player.moveBy(0, 0, -1);
				break;
			case '>':
				player.moveBy(0, 0, 1);
				break;
			}
		}

		if (player.level() > level)
			subscreen = new LevelUpScreen(player, player.level() - level);

		if (subscreen == null)
			world.update();

		if (player.hp() < 1)
			return new LoseScreen(player);

		return this;
	}

	private boolean userIsTryingToExit() {
		return player.z == 0
				&& world.tile(player.x, player.y, player.z) == Tile.STAIRS_UP;
	}

	private Screen userExits() {
		for (Item item : player.inventory().getItems()) {
			if (item != null && item.name().equals("teddy bear"))
				return new WinScreen();
		}
		player.modifyHp(0, "Died while cowardly fleeing the caves.");
		return new LoseScreen(player);
	}
}
