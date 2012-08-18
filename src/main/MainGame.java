package main;

import java.util.ArrayList;
import java.util.Properties;

import main.components.FighterComponent;
import net.slashie.libjcsi.CSIColor;
import net.slashie.libjcsi.CharKey;
import net.slashie.libjcsi.ConsoleSystemInterface;
import net.slashie.libjcsi.wswing.WSwingConsoleInterface;

public class MainGame {
	// ///// LOGGER ///////
//	private static Logger log = Logger.getLogger(MainGame.class.getName());

	// ///// SINGLETONE INSTANCE ///////
	private static MainGame instance;

	// ///// CONSOLE INSTANCE ///////

	private static ConsoleSystemInterface csi;

	// ///// GAME OBJECTS ///////
	private static Tile map[][] = new Tile[MainMap.MAP_WIDTH][MainMap.MAP_HEIGHT];
	private ArrayList<String> game_msgs = new ArrayList<String>();
	private boolean stop;
	private boolean game_started;
	private String gameState;
	private String playerAction;
	private Entity player;

	// ///// SINGLETONE GETTER ///////
	public static MainGame getInstance() {
		return instance;
	}

	// ///// GETTERS ///////
	public Tile[][] getMap() {
		return map;
	}

	public Entity getPlayer() {
		return player;
	}

	public static ConsoleSystemInterface getCSI() {
		return csi;
	}

	// ///////////////////////////////

	// ///// SETTING GAME STATE FROM ANOTHER CLASS ///////
	public void setGameState(String state) {
		gameState = state;
	}

	// //////////////////////////////
	// ///// INITIAL FUNCTION ///////
	// //////////////////////////////
	public static void main(String[] args) {
		Properties text = new Properties();
		text.setProperty("fontSize", "15");
		text.setProperty("font", "Lucida Console");
		csi = new WSwingConsoleInterface("RogueLike", text);
		instance = new MainGame();
		instance.run();
	}

	// ///// RUN INSTANCE ///////
	public void run() {
		// create new player entity
		// and set a fighter component for it
		player = new Entity(MainMap.MAP_WIDTH / 2, MainMap.MAP_HEIGHT / 2, '@',
				"player", CSIColor.RED, true);
		FighterComponent fighter_component = new FighterComponent(player, 30,
				2, 5);
		player.setFighterComponent(fighter_component);

		// initial map generation
		// and map drawing
		MainMap.getInstance().generateMap();
		MainMap.getInstance().drawMap();

		// initial game state and player action
		// set to default
		gameState = "playing";
		playerAction = "";

		// boolean stop for exiting the game
		stop = false;

		// game started boolean for start window
		game_started = false;
		// main game loop
		while (!stop) {

			// clearing the game screen
			if (!inventoryShown)
				csi.cls();

			// / SHOW START HELP WINDOW //
			if (!game_started) {
				showStartWindow();
				handleKeys();
			}
			// printing GUI
			if (!inventoryShown)
				printGUI();
			// drawing the map

			if (!inventoryShown)
				MainMap.getInstance().drawMap();
			// drawing the player
			player.draw();
			// refreshing the console output
			csi.refresh();

			// handling the player keys
			playerAction = handleKeys();
			if (playerAction.equals("exit"))
				System.exit(0);

			// let monsters take their turn
			if (gameState.equals("playing")
					&& (!playerAction.equals("didnt-take-turn")))
				for (Entity object : MainMap.getInstance().getObjects())
					if (object != player)
						if (object.getAIComponent() != null)
							object.getAIComponent().takeTurn();
		}
		System.exit(0);
	}

	// /////////////////////////////////////////////
	// ////// FUNCTION TO HANDLE KEY PRESSES ///////
	// /////////////////////////////////////////////
	public String handleKeys() {
		// getch()
		CharKey dir = csi.inkey();
		// Q is the key for exit
		if (dir.code == CharKey.Q || dir.code == CharKey.q) {
			stop = true;
			return "exit";
		}

		if (!game_started)
			if (dir.code == CharKey.ENTER) {
				game_started = true;
				csi.cls();
			}

		// C is the key to clear the combat log
		if (gameState.equals("playing"))
			if (dir.code == CharKey.c || dir.code == CharKey.c)
				game_msgs.clear();

		if (inventoryShown) {
			if (dir.code == CharKey.I || dir.code == CharKey.i)
				showInventory();
			switch (dir.code) {
			case CharKey.F1:
				MainMap.getInstance().getInventory().get(0).getItemComponent()
						.useItem();
				break;
			case CharKey.F2:
				MainMap.getInstance().getInventory().get(1).getItemComponent()
						.useItem();
				break;
			case CharKey.F3:
				MainMap.getInstance().getInventory().get(2).getItemComponent()
						.useItem();
				break;
			case CharKey.F4:
				MainMap.getInstance().getInventory().get(3).getItemComponent()
						.useItem();
				break;
			case CharKey.F5:
				MainMap.getInstance().getInventory().get(4).getItemComponent()
						.useItem();
				break;
			case CharKey.F6:
				MainMap.getInstance().getInventory().get(5).getItemComponent()
						.useItem();
				break;
			case CharKey.F7:
				MainMap.getInstance().getInventory().get(6).getItemComponent()
						.useItem();
				break;
			case CharKey.F8:
				MainMap.getInstance().getInventory().get(7).getItemComponent()
						.useItem();
				break;
			case CharKey.F9:
				MainMap.getInstance().getInventory().get(8).getItemComponent()
						.useItem();
				break;
			case CharKey.F10:
				MainMap.getInstance().getInventory().get(9).getItemComponent()
						.useItem();
				break;
			case CharKey.F11:
				MainMap.getInstance().getInventory().get(10).getItemComponent()
						.useItem();
				break;
			case CharKey.F12:
				MainMap.getInstance().getInventory().get(11).getItemComponent()
						.useItem();
				break;
			}
			return "didnt-take-turn";
		}
		// moving the player with ARROWS
		if (gameState.equals("playing")) {
			if (dir.isUpArrow() && (player.getY() - 1 >= 0)) {
				MainMap.getInstance().playerMoveOrAttack(0, -1);
			} else if (dir.isDownArrow()
					&& (player.getY() + 1 < MainMap.MAP_HEIGHT)) {
				MainMap.getInstance().playerMoveOrAttack(0, 1);
			} else if (dir.isLeftArrow() && (player.getX() - 1 >= 0)) {
				MainMap.getInstance().playerMoveOrAttack(-1, 0);
			} else if (dir.isRightArrow()
					&& (player.getX() + 1 < MainMap.MAP_WIDTH)) {
				MainMap.getInstance().playerMoveOrAttack(1, 0);
			} else if (dir.code == CharKey.Z || dir.code == CharKey.z) {
				MainMap.getInstance().nextLevel();
			} else if (dir.code == CharKey.I || dir.code == CharKey.i) {
				showInventory();
				return "didnt-take-turn";
			} else if (dir.code == CharKey.G || dir.code == CharKey.g) {
				MainMap.getInstance().grabItem();
			} else
				return "didnt-take-turn";
		}
		return "";
	}

	// //////////////////////////////////////////////
	// ///// FUNCTION TO PRINT USER INTERFACE ///////
	// //////////////////////////////////////////////
	private void printGUI() {
		// printing player hp
		csi.print(0, MainMap.MAP_HEIGHT, "HP"
				+ player.getFighterComponent().getHp() + "/"
				+ player.getFighterComponent().getMaxHP());
		// printing the combat log
		int y = 0;
		for (String text : game_msgs) {
			if (text.contains("dies"))
				csi.print(20, y + MainMap.MAP_HEIGHT, text, CSIColor.RED);
			else if (text.contains("pick"))
				csi.print(20, y + MainMap.MAP_HEIGHT, text, CSIColor.VIOLET);
			else if (text.contains("healed"))
				csi.print(20, y + MainMap.MAP_HEIGHT, text, CSIColor.GREEN);
			else
				csi.print(20, y + MainMap.MAP_HEIGHT, text, CSIColor.WHITE);
			y++;
		}
		// ////////////////////////
	}

	// //////////////////////////////////////////////////////
	// //// FUNCTION TO ADD NEW MESSAGES TO COMBAT LOG //////
	// //////////////////////////////////////////////////////
	public void newMessage(String message) {
		// removing the messages from log if too much
		if (game_msgs.size() > 4)
			game_msgs.remove(0);
		game_msgs.add(message);
	}

	// //////////////////////////////////////
	// // FUNCTION TO SHOW START WINDOW /////
	// //////////////////////////////////////

	public void showStartWindow() {
		// clear all window
		csi.cls();
		csi.print(30, 15, "INFINITE CAVY STORY", CSIColor.AMETHYST);
		csi.print(27, 16, "Old-school rogue-like game");

		csi.print(20, 19, "Use arrows to move");
		csi.print(20, 20, "Use arrows to the targets direction to attack");
		csi.print(20, 21, "Press C to clear combat log");
		csi.print(20, 21, "Press Z to go to kext level while on stairs");
		csi.print(20, 22, "Press G to grab item");
		csi.print(20, 22, "Press I to open inventory");
		csi.print(20, 25, "Press Q to quit");

		csi.print(28, 27, "PRESS ENTER TO CONTINUE", CSIColor.GREEN_YELLOW);

		csi.print(64, 44, "Created by Prokk");
	}

	// ////////////////////////
	// /// SHOW INVENTORY /////
	// ////////////////////////
	boolean inventoryShown = false;

	public void showInventory() {
		if (!inventoryShown) {
			csi.print(30, 15, "====== INVENTORY ======");
			int i = 0;
			if (MainMap.getInstance().getInventory().isEmpty()) {
				csi.print(30, 16, "Empty");
				i++;
			} else {
				for (int count = 0; count < MainMap.getInstance()
						.getInventory().size(); count++) {
					csi.print(30, 16 + i, "F"
							+ (i + 1)
							+ ": "
							+ MainMap.getInstance().getInventory().get(i)
									.getName());
					i++;
				}
			}
			csi.print(30, 16 + i, "=======================");
			inventoryShown = true;
		} else {
			MainMap.getInstance().drawMap();
			inventoryShown = false;
		}
	}
}