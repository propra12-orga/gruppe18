public class Player {
	double x, y = 0;

	int dir = 4;
	boolean alive = true;
	int health = 100;
	String[] inventar = new String[5];

	public Player(double xdir, double ydir) {

		x = xdir;
		y = ydir;

	}

	// Visuelle ausg

	public void draw(int lastkey) {

		Arena.x = x;

		Arena.y = y;

		if (health > 0) {
			alive = true;
			health();

			if ((lastkey == 1)) {

				StdDraw.picture(x, y, "png/right.png");

			}

			if ((lastkey == 0)) {

				StdDraw.picture(x, y, "png/front.png");

			}

			if ((lastkey == 3)) {

				StdDraw.picture(x, y, "png/back.png");

			}
			if ((lastkey == 4)) {

				StdDraw.picture(x, y, "png/left.png");

			}

			if ((lastkey == 6)) {

				if (Arena.bo.x > Arena.w)
					Arena.bo.x = -Arena.w;
				if (Arena.bo.y > Arena.h) {
					Arena.bo.x -= 1;
					Arena.bo.y = -Arena.h;
				}
				;

				Arena.plr.x = Arena.bo.x;
				Arena.plr.y = Arena.bo.y;
				StdDraw.picture(x, y, "png/front.png");
				Arena.bo.trig = true;
				Arena.bo.x += 0.1;
				Arena.bo.y += Math.random() * 0.5;
				if (Arena.bo.x >= Arena.w)
					Arena.bo.x *= -1;

			}

			// boolische Multiplikation enspricht dem UND
			if ((Arena.down && Arena.up && Arena.left && Arena.right)) {
				StdDraw.picture(x, y, "gif/star.gif", 30, 30);
				// Joker: Wenn der Benutzer alle Richtunngstasten drückt, steht
				// ihm
				// ein Joker bereit.
				// nach dem Benutzen des Jokers werden die Zugänge versperrt.

			}

			if ((Arena.down) && (!Arena.up)) {

				StdDraw.picture(x, y, "png/front.png");
			}

			if ((Arena.right) && (!Arena.left)) {

				StdDraw.picture(x, y, "png/right.png");
			}
			if ((Arena.up) && (!Arena.down)) {

				StdDraw.picture(x, y, "png/back.png");
			}
			if ((Arena.left) && (!Arena.right)) {

				StdDraw.picture(x, y, "png/left.png");
			}
		} else {
			alive = false;
			Arena.levelswitch = 99;

		}

	}

	private void health() {
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.filledRectangle(-10, Arena.h, 10, 1);

		for (int i = 0; i <= health * 0.15; i++) {

			if (health <= 45) {
				StdDraw.setPenColor(StdDraw.RED);
			} else {
				StdDraw.setPenColor(StdDraw.GREEN);
			}
			if (i >= 14) {
				StdDraw.setPenColor(StdDraw.BLUE);
			}

			StdDraw.filledRectangle(-19 + i * 1.2, Arena.h, 0.5, 0.5);
			StdDraw.setPenColor(StdDraw.BLACK);
		}
	}

}
