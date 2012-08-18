package main;

import java.util.ArrayList;
import java.util.Properties;

import net.slashie.libjcsi.CSIColor;
import net.slashie.libjcsi.CharKey;
import net.slashie.libjcsi.ConsoleSystemInterface;
import net.slashie.libjcsi.wswing.WSwingConsoleInterface;

public class MainGame {
	// ///// LOGGER ///////
	// private static Logger log = Logger.getLogger(MainGame.class.getName());

	// ///// SINGLETONE INSTANCE ///////
	private static MainGame instance;

	// ///// CONSOLE INSTANCE ///////

	private static ConsoleSystemInterface csi;

	// ///// GAME OBJECTS ///////
	private static Tile map[][] = new Tile[MapGenerator.MAP_WIDTH][MapGenerator.MAP_HEIGHT];
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
		player = new Entity(MapGenerator.MAP_WIDTH / 2,
				MapGenerator.MAP_HEIGHT / 2, '@', "player", CSIColor.RED, true);
		FighterComponent fighter_component = new FighterComponent(player, 30,
				2, 5);
		player.setFighterComponent(fighter_component);

		// initial map generation
		// and map drawing
		MapGenerator.getInstance().generateMap();
		MapGenerator.getInstance().drawMap();

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
			csi.cls();

			// / SHOW START HELP WINDOW //
			if (!game_started) {
				showStartWindow();
				handleKeys();
			}
			// printing GUI
			printGUI();
			// drawing the map
			MapGenerator.getInstance().drawMap();
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
				for (Entity object : MapGenerator.getInstance().getObjects())
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

		// moving the player with ARROWS
		if (gameState.equals("playing")) {
			if (dir.isUpArrow() && (player.getY() - 1 >= 0)) {
				MapGenerator.getInstance().playerMoveOrAttack(0, -1);
			} else if (dir.isDownArrow()
					&& (player.getY() + 1 < MapGenerator.MAP_HEIGHT)) {
				MapGenerator.getInstance().playerMoveOrAttack(0, 1);
			} else if (dir.isLeftArrow() && (player.getX() - 1 >= 0)) {
				MapGenerator.getInstance().playerMoveOrAttack(-1, 0);
			} else if (dir.isRightArrow()
					&& (player.getX() + 1 < MapGenerator.MAP_WIDTH)) {
				MapGenerator.getInstance().playerMoveOrAttack(1, 0);
			} else if (dir.code == CharKey.Z || dir.code == CharKey.z) {
				MapGenerator.getInstance().nextLevel();
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
		csi.print(MapGenerator.MAP_WIDTH, 0, "HP"
				+ player.getFighterComponent().getHp() + "/"
				+ player.getFighterComponent().getMaxHP());

		// printing the combat log
		int y = 1;
		for (String text : game_msgs) {
			if (text.contains("dies"))
				csi.print(MapGenerator.MAP_WIDTH, y + 13, text, CSIColor.RED);
			else
				csi.print(MapGenerator.MAP_WIDTH, y + 13, text, CSIColor.WHITE);
			y++;
		}
		// ////////////////////////
	}

	// //////////////////////////////////////////////////////
	// //// FUNCTION TO ADD NEW MESSAGES TO COMBAT LOG //////
	// //////////////////////////////////////////////////////
	public void newMessage(String message) {
		// removing the messages from log if too much
		if (game_msgs.size() > 10)
			game_msgs.remove(0);
		game_msgs.add(message);
	}

	// //////////////////////////////////////
	// // FUNCTION TO SHOW START WINDOW /////
	// //////////////////////////////////////

	public void showStartWindow() {
		// clear all window
		csi.cls();
		csi.print(30, 5, "INFINITE CAVY STORY", CSIColor.AMETHYST);
		csi.print(27, 6, "Old-school rogue-like game");

		csi.print(20, 9, "Use arrows to move");
		csi.print(20, 10, "Use arrows to the targets direction to attack");
		csi.print(20, 11, "Press C to clear combat log");
		csi.print(20, 11, "Press Z to go to kext level while on stairs");
		csi.print(20, 13, "Press Q to quit");

		csi.print(28, 17, "PRESS ENTER TO CONTINUE", CSIColor.GREEN_YELLOW);

		csi.print(64, 24, "Created by Prokk");
	}
}