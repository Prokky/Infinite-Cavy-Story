package main;

public class Rect {
	// //// PRIVATE FIELDS CONTAINING RECTANGLE POSITION
	int x1, x2, y1, y2;

	// /// INITIATING RECTANGLE WITH X,Y POSITION AND WIDTH HEIGHT
	public Rect(int x, int y, int w, int h) {
		this.x1 = x;
		this.y1 = y;
		this.x2 = x + w;
		this.y2 = y + h;
	}

	// //// GETTERS ///////
	public int getX1() {
		return x1;
	}

	public int getX2() {
		return x2;
	}

	public int getY1() {
		return y1;
	}

	public int getY2() {
		return y2;
	}

	// /////////////////////

	// /// CENTER OF ROOM FOR TUNNEL GENERATING /////
	public int getCenterX() {
		return (this.x1 + this.x2) / 2;
	}

	public int getCenterY() {
		return (this.y1 + this.y2) / 2;
	}

	// // CHECKS INTERSECTION WITH ANOTHER RECTANGLE FOR ROOM POSITIONING ////
	public boolean intersect(Rect other) {
		// returns true if this rectangle intersects with another one
		return (this.x1 <= other.x2 && this.x2 >= other.x1
				&& this.y1 <= other.y2 && this.y2 >= other.y1);
	}
}
