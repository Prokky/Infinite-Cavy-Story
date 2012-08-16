package main;

public class Tile {

	// // PRIVATE FIELDS
	private boolean blocked; // is tile blocked
	private boolean blocked_sight; // does tile block sight
	private boolean visited; // was tile ever visited

	// // GETTERS ////
	public boolean isBlocked() {
		return blocked;
	}

	public boolean isBlockedSight() {
		return blocked_sight;
	}

	public boolean wasVisited() {
		return visited;
	}

	// ///////////////

	// //// SETTERS //////
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public void setBlockedSight(boolean blocked_sight) {
		this.blocked_sight = blocked_sight;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	// ///////////////

	// / INITIATE TILE WITH BLOCKED BOOLEAN
	public Tile(boolean blocked) {
		this.blocked = blocked;
		this.blocked_sight = blocked;
		this.visited = false;
	}

	// // INITIATE TILE WITH 2 BOOLEANS
	public Tile(boolean blocked, boolean blocked_sight) {
		this.blocked = blocked;
		this.blocked_sight = blocked_sight;
		this.visited = false;
	}

}
