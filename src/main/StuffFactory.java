package main;

import java.util.List;

import main.entities.BatAi;
import main.entities.Creature;
import main.entities.FungusAi;
import main.entities.PlayerAi;
import main.items.Item;
import main.world.World;
import asciiPanel.AsciiPanel;

public class StuffFactory {
	private World world;

	public StuffFactory(World world) {
		this.world = world;
	}

	public Creature newPlayer(List<String> messages, FieldOfView fov) {
		Creature player = new Creature(world, '@', "Player",
				AsciiPanel.brightRed, 100, 20, 5);
		world.addAtEmptyLocation(player, 0);
		new PlayerAi(player, messages, fov);
		return player;
	}

	public Creature newFungus(int depth) {
		Creature fungus = new Creature(world, 'f', "Fungus", AsciiPanel.green,
				10, 0, 0);
		world.addAtEmptyLocation(fungus, depth);
		new FungusAi(fungus, this);
		return fungus;
	}

	public Creature newBat(int depth) {
		Creature bat = new Creature(world, 'b', "Bat", AsciiPanel.yellow, 15,
				5, 0);
		world.addAtEmptyLocation(bat, depth);
		new BatAi(bat);
		return bat;
	}

	public Item newRock(int depth) {
		Item rock = new Item(',', AsciiPanel.yellow, "rock");
		world.addAtEmptyLocation(rock, depth);
		return rock;
	}

	public Item newVictoryItem(int depth) {
		Item item = new Item('*', AsciiPanel.brightWhite, "teddy bear");
		world.addAtEmptyLocation(item, depth);
		return item;
	}
}
