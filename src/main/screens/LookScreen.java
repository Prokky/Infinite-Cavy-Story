package main.screens;

import main.entities.Creature;
import main.items.Item;
import main.world.Tile;

public class LookScreen extends TargetBasedScreen {

	public LookScreen(Creature player, String caption, int sx, int sy) {
		super(player, caption, sx, sy);
	}

	public void enterWorldCoordinate(int x, int y, int screenX, int screenY) {
		Creature creature = player.creature(x, y, player.z);
		if (creature != null) {
			caption = creature.glyph() + " " + creature.name()
					+ creature.details();
			return;
		}

		Item item = player.item(x, y, player.z);
		if (item != null) {
			caption = item.glyph() + " " + item.name() + item.details();
			return;
		}

		Tile tile = player.tile(x, y, player.z);
		caption = tile.glyph() + " " + tile.details();
	}
}
