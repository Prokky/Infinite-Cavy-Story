package main.world;

import java.awt.Color;

import asciiPanel.AsciiPanel;

public enum Tile {
	FLOOR((char)250, new Color(150, 75, 0)),
	WALL((char)177,  new Color(150, 75, 0)),
	BOUNDS('x', AsciiPanel.brightBlack), 
	STAIRS_DOWN('>', AsciiPanel.white), 
	STAIRS_UP('<', AsciiPanel.white),
	UNKNOWN(' ', AsciiPanel.white);
	
	private char glyph;
	public char glyph() { return glyph; }
	
	private Color color;
	public Color color() { return color; }
	
	Tile(char glyph, Color color){
		this.glyph = glyph;
		this.color = color;
	}

	public boolean isGround() {
		return this != WALL && this != BOUNDS;
	}

	public boolean isDiggable() {
		return this == Tile.WALL;
	}
}
