package main;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import net.slashie.libjcsi.CSIColor;
import net.slashie.libjcsi.CharKey;
import net.slashie.libjcsi.ConsoleSystemInterface;
import net.slashie.libjcsi.wswing.WSwingConsoleInterface;

public class MainGame {
	private static MainGame instance;

	private static Logger log = Logger.getLogger(MainGame.class.getName());

	// # game objects
	private Entity player;

	private static Tile map[][] = new Tile[MapGenerator.MAP_WIDTH][MapGenerator.MAP_HEIGHT];

	private static ConsoleSystemInterface csi = new WSwingConsoleInterface(
			"RogueLike", true);

	private boolean stop;
	private String gameState;
	private String playerAction;

	public static MainGame getInstance() {
		return instance;
	}

	public void setGameState(String state) {
		gameState = state;
	}

	public Tile[][] getMap() {
		return map;
	}

	public Entity getPlayer() {
		return player;
	}

	public static ConsoleSystemInterface getCSI() {
		return csi;
	}

	public static void main(String[] args) {
		instance = new MainGame();
		instance.run();
	}

	public void run() {
		player = new Entity(MapGenerator.MAP_WIDTH / 2,
				MapGenerator.MAP_HEIGHT / 2, '@', "player", CSIColor.RED, true);
		Fighter fighter_component = new Fighter(player, 30, 2, 5);
		player.setFighterComponent(fighter_component);

		MapGenerator.getInstance().generateMap();
		gameState = "playing";
		playerAction = "";
		stop = false;
		while (!stop) {
			csi.cls();
			printGUI();
			// draw map
			MapGenerator.getInstance().drawMap();

			player.draw();
			//
			csi.refresh();
			playerAction = handleKeys();
			if (playerAction.equals("exit"))
				System.exit(0);
			// #let monsters take their turn
			if (gameState.equals("playing")
					&& (!playerAction.equals("didnt-take-turn")))
				for (Entity object : MapGenerator.getInstance().getObjects())
					if (object != player)
						if (object.getAIComponent() != null)
							object.getAIComponent().takeTurn();

		}
		System.exit(0);
	}

	public String handleKeys() {
		CharKey dir = csi.inkey();

		if (dir.code == CharKey.Q || dir.code == CharKey.q) {
			stop = true;
			return "exit";
		}

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
			} else
				return "didnt-take-turn";
		}
		return "";
	}

	private ArrayList<String> game_msgs = new ArrayList<String>();

	private void printGUI() {
		csi.print(MapGenerator.MAP_WIDTH, 0, "HP"
				+ player.getFighterComponent().getHp() + "/"
				+ player.getFighterComponent().getMaxHP());
		int y = 1;
		for (String text : game_msgs) {
			if (text.contains("dies"))
				csi.print(MapGenerator.MAP_WIDTH, y + 13, text, CSIColor.RED);
			else
				csi.print(MapGenerator.MAP_WIDTH, y + 13, text, CSIColor.WHITE);
			y++;
		}

	}

	public void newMessage(String message) {
		if (game_msgs.size() > 10)
			game_msgs.remove(0);
		game_msgs.add(message);

	}
}