package main;

public class Tile {
	private boolean blocked;
	private boolean blocked_sight;
	private boolean visited;

	public boolean isBlocked() {
		return blocked;
	}

	public boolean wasVisited() {
		return visited;
	}

	public boolean isBlockedSight() {
		return blocked_sight;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public void setBlockedSight(boolean blocked_sight) {
		this.blocked_sight = blocked_sight;
	}

	public Tile(boolean blocked) {
		this.blocked = blocked;
		this.blocked_sight = blocked;
		this.visited = false;
	}

	public Tile(boolean blocked, boolean blocked_sight) {
		this.blocked = blocked;
		this.blocked_sight = blocked_sight;
		this.visited = false;
	}

}
