package main.helpers;

/**
 * 
 * Class for easy handling with rectangles such as rooms
 * 
 * @author prokk
 * 
 */
public class Rect
{
	int	x1, x2, y1, y2;

	/**
	 * 
	 * @param x
	 *            coord of position of top left corner of rectangle
	 * @param y
	 *            coord of position of top left corner of rectangle
	 * @param w
	 *            - width of rectangle
	 * @param h
	 *            - height of rectangle
	 */
	public Rect(int x, int y, int w, int h)
	{
		this.x1 = x;
		this.y1 = y;
		this.x2 = x + w;
		this.y2 = y + h;
	}

	/**
	 * 
	 * @return x coord of top left corner of rectangle
	 */
	public int getX1()
	{
		return x1;
	}

	/**
	 * 
	 * @return x coord of bottom right corner of rectangle
	 */
	public int getX2()
	{
		return x2;
	}

	/**
	 * 
	 * @return y coord of top left corner of rectangle
	 */
	public int getY1()
	{
		return y1;
	}

	/**
	 * 
	 * @return y coord of bottom right corner of rectangle
	 */
	public int getY2()
	{
		return y2;
	}

	/**
	 * 
	 * @return x coord of center of rectangle
	 */
	public int getCenterX()
	{
		return (this.x1 + this.x2) / 2;
	}

	/**
	 * 
	 * @return y coord of center of rectangle
	 */
	public int getCenterY()
	{
		return (this.y1 + this.y2) / 2;
	}

	/**
	 * Check intersection with another rectangle
	 * 
	 * @param other
	 *            rectangle
	 * @return true if intersection detected
	 */
	public boolean intersect(Rect other)
	{
		// returns true if this rectangle intersects with another one
		return (this.x1 <= other.x2 && this.x2 >= other.x1 && this.y1 <= other.y2 && this.y2 >= other.y1);
	}
}
