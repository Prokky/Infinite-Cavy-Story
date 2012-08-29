package main.objects;

/**
 * Class to handle with tiles
 * 
 * @author prokk
 * 
 */
public class Tile
{

	private boolean	blocked;		// is tile blocked
	private boolean	blocked_sight;	// does tile block sight
	private boolean	visited;		// was tile ever visited
	private boolean	wall;			// is tile a wall

	/**
	 * 
	 * @return tile is blocked
	 */
	public boolean isBlocked()
	{
		return blocked;
	}

	/**
	 * 
	 * @return tile blocks sight
	 */
	public boolean isBlockedSight()
	{
		return blocked_sight;
	}

	/**
	 * 
	 * @return tile was visited or not
	 */
	public boolean wasVisited()
	{
		return visited;
	}

	/**
	 * 
	 * @return is the tile a wall
	 */
	public boolean isWall()
	{
		return wall;
	}

	/**
	 * 
	 * @param blocked
	 */
	public void setBlocked(boolean blocked)
	{
		this.blocked = blocked;
	}

	/**
	 * 
	 * @param blocked_sight
	 */
	public void setBlockedSight(boolean blocked_sight)
	{
		this.blocked_sight = blocked_sight;
	}

	/**
	 * 
	 * @param visited
	 */
	public void setVisited(boolean visited)
	{
		this.visited = visited;
	}

	/**
	 * 
	 * @param wall
	 */
	public void setWall(boolean wall)
	{
		this.wall = wall;
	}

	/**
	 * Basic constructor
	 * 
	 * @param blocked
	 *            - tile blocks movement
	 */
	public Tile(boolean blocked)
	{
		this.blocked = blocked;
		this.blocked_sight = blocked;
		this.visited = false;
		this.wall = false;
	}

	/**
	 * Extender constructor
	 * 
	 * @param blocked
	 *            - tile blocks movement
	 * @param blocked_sight
	 *            - tile blocks sight
	 */
	public Tile(boolean blocked, boolean blocked_sight)
	{
		this.blocked = blocked;
		this.blocked_sight = blocked_sight;
		this.visited = false;
		this.wall = false;
	}
}
