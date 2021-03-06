public class Bombe implements Runnable {

	public static double y;
	public static double x;
	public static boolean trig = false;
	public static int radius = 2;
	public static int timer = 300;
	public int bombencounter = 0;
	public static int vis = 0;

	/*
	 * public Bombe(double x2, double y2, int i, int j) {
	 * 
	 * x = x2; y = y2; radius = i; timer = j;
	 * 
	 * }
	 */
	public void run() {

		x = Player.x;
		y = Player.y;

		Bombe.vis = 1;

	}

	public static void draw() {
		Arena.Player.draw();
		if (vis == 1) {
			for (int j = 0; j < timer / 10; j++) {

				Arena.steuerung(); // Tastatureingaben

				Arena.levelswitch();

				Arena.Player.draw();
				StdDraw.picture(Bombe.x, Bombe.y, "gif/teleport.gif",
						radius * 2, radius * 2);
				int i = (int) (j * 0.1);
				StdDraw.text(0, j * 0.2, String.valueOf(i));
				StdDraw.picture(Bombe.x, Bombe.y, "gif/orange_bomb.gif");
				StdDraw.show(20);
				

			}

			for (int j = 0; j < Bombe.radius * 15; j++) {

				StdDraw.picture(Bombe.x, Bombe.y, "png/blocks.png", j * 0.4,
						j * 0.4, j * 1024);

				StdDraw.picture(Bombe.x, Bombe.y, "png/fireworx.png", 2, j);

				StdDraw.picture(Bombe.x, Bombe.y, "png/fireworx.png", j, 2);

				StdDraw.show(33);

			}
			Player.space = false;

			if ((Player.x == Bombe.x)) {
				Player.health -= 100;
			}

			if ((Player.x <= Bombe.x + radius * 2)
					&& (Player.y <= Bombe.y + radius * 2)) {
				Player.health -= 10;

			}
			Bombe.vis = 0;
		}
		return;
	}
}
