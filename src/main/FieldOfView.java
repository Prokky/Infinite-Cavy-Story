package main;

public class FieldOfView
{
	private final static int	RAYS		= 360;
	private static FieldOfView	instance	= new FieldOfView();

	public static FieldOfView getInstance()
	{
		return instance;
	}

	public final static int	STEP	= 3;

	public final static int	RAD		= 6;
	private boolean[][]		fov		= new boolean[MainMap.MAP_WIDTH][MainMap.MAP_HEIGHT];

	public boolean[][] getFov()
	{
		return fov;
	}

	public void calculate()
	{
		for (int i = 0; i < MainMap.MAP_WIDTH; i++)
			for (int j = 0; j < MainMap.MAP_HEIGHT; j++)
				fov[i][j] = false;
		for (int i = 0; i < RAYS + 1; i += STEP)
		{
			double ax = Math.sin(i * Math.PI / 180);// sintable[i]; // # Get
													// precalculated value sin(x
													// / (180 /
			// pi))
			double ay = Math.cos(i * Math.PI / 180);// # cos(x / (180 / pi))

			double x = MainGame.getInstance().getPlayer().getX(); // # Player's
																	// x
			double y = MainGame.getInstance().getPlayer().getY(); // # Player's
																	// y

			for (int z = 0; z < RAD; z++)
			{// # Cast the ray
				x += ax;
				y += ay;

				if ((x < 0) || (y < 0) || (x > MainMap.MAP_WIDTH) || (y > MainMap.MAP_HEIGHT))
					break;

				fov[(int) Math.round(x)][(int) Math.round(y)] = true;
				if (MainGame.getInstance().getMap()[(int) Math.round(x)][(int) Math.round(y)].isBlocked())
					break;
			}
		}
	}
}
