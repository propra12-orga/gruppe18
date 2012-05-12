public class Apple implements Runnable {
	double x = 0;
	double y = 0;
	int anzahl = 0;

	public Apple(double d, double e) {
		x = d;
		y = e;
	}

	@Override
	public void run() {
		StdDraw.picture(x, y, "gif/apple.gif", Math.abs(x * 0.4),
				Math.abs(x * 0.4), x * 720);


	}

}
