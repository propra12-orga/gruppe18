public class Bombe {

	public double y;
	public double x;
	public boolean trig = false;

	public Bombe(double x2, double y2) {

		x = x2;
		y = y2;

	}

	public void draw(boolean trig2) {

		if (Arena.bo.trig)

			StdDraw.picture(Arena.bo.x, Arena.bo.y, "gif/orange_bomb.gif");

	}

}
