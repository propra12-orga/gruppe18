public class Apple {
	double x = 0;
	double y = 0;
	int anzahl = 0;

	public Apple(double d, double e) {
		x = d;
		y = e;
	}

	public void draw() {

		StdDraw.picture(x, y, "gif/apple.gif", 2, 2);

	}

}
