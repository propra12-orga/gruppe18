import java.awt.Color;

public class Arena {



	public static int b = 0;

	static byte w = 25;
	static byte h = 25;
	static Color color;
	static byte levelswitch = 0;
	static byte soundswitch = 0;

	static double x = 0;
	static double y = 0;
	static int n = 0;

	static byte gamespeed = 20; // 14 ist defaultwert

	
	static int lastkey = 0;
	static double[][] feld = new double[w][h];
	// Keycodes
	public static boolean right = false;
	public static boolean left = false;
	public static boolean up = false;
	public static boolean down = false;
	public static boolean space = false;
	public static boolean click = false;
	// Zufallszahlen
	static double randx = (int) (Math.random() * w);
	static double randy = (int) (Math.random() * h);
	// counter
	public static int counter = 0;
	public static int apfelcounter = 0;

	// Konstruktoren

	static double[] inventar = new double[5];

	public static Player plr = new Player(randy, randx);
	public static Bombe bo = new Bombe(0, 0);
	private static String signal;

	// ////////////////////////////////////////////////////////////////////////////////
	// / main
	// test test
	public static void main(String[] args) {
		draw(); // Initialisiere den Bildschirminhalt mit draw() einmal
		// welcome();
		system(); // Setze die Grafik und die Steuerung in eine Schleife

	}

	private static void system() {
		while (true) {
			// Beginn der Messung in Millisekunden

			long start = System.currentTimeMillis();

			fr(gamespeed); // Bildschirmrefresh
			steuerung(); // Tastatureingaben
			levelset(); // Leveldaten

			plr.draw(lastkey); // Player zeichnen
			bo.draw(bo.trig);

			signal = ("Duration in ms: " + (System.currentTimeMillis() - start));

			// Ende der Messung
			out(signal);
		}

	}

	private static void out(String signal2) {

		StdDraw.textLeft(-w, -h, signal);

		Clicks(counter);
	}

	private static void Clicks(int counter2) {

	}

	private static void setcolor(byte i) {

		if (i == 0) {
			StdDraw.setPenColor(StdDraw.BLACK);
		}
		if (i == 1) {
			StdDraw.setPenColor(StdDraw.WHITE);
		}

	}

	// Initialisiere Screen

	private static void draw() {
		StdDraw.setCanvasSize(w * 25, h * 25);
		StdDraw.setXscale(-w, w);
		StdDraw.setYscale(-h, h);
		setcolor((byte) 0);

	}

	private static void fr(byte gamespeed2) {

		// Die zwei Linien nicht ändern oder schleifen sonst kommt es zu Fehlern
		// : show and clear Vorsicht!!!!
		StdDraw.show(gamespeed);
		StdDraw.clear();

	}

	// ////////////////////////////////////////////////////////////////////////////////
	// / Steuerung
	// ///////////////////////////////////////////////////////////////////////////////

	private static void tasten(byte levelswitch2) {

		if (space) {

			lastkey = 6;

		}

		if (left) {
			x = plr.x;
			plr.x--;

			lastkey = 4; // Speicher die Aktion separat

		}

		if (down && up && left && right) {

			plr.x = 0;
			plr.y = 0;
			lastkey = 9;

		}

		if (right) {
			x = plr.x;
			plr.x++;

			lastkey = 1;

		}

		if (up) {

			y = plr.y;
			plr.y++;

			lastkey = 3;

		}

		if (down) {
			plr.y--;
			y = plr.y;
			lastkey = 0;

		}

		if (StdDraw.mousePressed()) {

			counter++;

		}

	}

	private static void steuerung() {

		collision();
		tasten(levelswitch);

		// Levelswitch für das schnelle wechseln von Levels

	}

	// ////////////////////////////////////////////////////////////////////////////////
	// / Kollision
	// ///////////////////////////////////////////////////////////////////////////////

	private static void collision() {

	
		Arena.x = plr.x;
		Arena.y = plr.y;

		if (plr.x >= w) {

			plr.x = w;
			colorwall();
		}
		if (plr.x <= -w) {
			plr.x = -w;

		}
		if (plr.y >= h) {
			plr.y = h;
			unilogo();
		}
		if (plr.y <= -h) {
			plr.y = -h;
			apfel();

		}

		

	}

	// ////////////////////////////////////////////////////////////////////////////////
	// / Levels
	// ///////////////////////////////////////////////////////////////////////////////

	private static void welcome() {

		StdDraw.picture(0, 0, "jpg/introscreen.jpg");
		String message = "Viel Spass!";
		StdDraw.textLeft(0, -5, message);
		StdDraw.show(400);
		StdDraw.picture(0, 0, "jpg/introscreen.jpg");
		message = "Deine beiden Glückszahlen sind die: " + randx + " und "
				+ randy;
		StdDraw.textLeft(-w, 0, message);
		StdDraw.show(400);
		String kette = "Die Position des Spielers ist ";
		StdDraw.textLeft(-w, 0, message);
		StdDraw.show(1000);
		StdDraw.clear();
		StdDraw.picture(0, 0, "jpg/introscreen.jpg");
		message = "Steuer das Spiel mit Pfeil oben, unten, rechts und Space.";

		if (plr.y <= 0) {
			kette += " unterhalb";
		} else {
			kette += " oberhalb";
		}

		if (plr.x <= w / 8) {
			kette += " und mittig";
		} else {
			kette += " und aussen";
		}

		if (plr.x <= 0) {
			kette += " links im Feld";

		} else {
			kette += " rechts im Feld.";
		}

		StdDraw.textLeft(-w, 0, message);
		StdDraw.textLeft(-w, -1, kette);
		StdDraw.show(500);

	}

	private static void levelset() {

		// Objektspuren des players

		gate1();
		gate2();
		gate3();
		gate4();

		

			StdDraw.picture(Arena.x, Arena.y, "gif/teleport.gif");

		

		if (levelswitch == 0) {
			mainlevel();
		} else {
			setuplevel();
		}

	}

	private static void setuplevel() {

	

		StdDraw.picture(0, 0, "jpg/boden.jpg", w*3 , h*3 );

		StdDraw.setPenColor(color);
		String message = "Setup Level: Ausrüstung / PowerUps";
		StdDraw.text(0, -h, message);

	}

	private static void levelbrand() {
		StdDraw.picture(-w, h, "gif/unicorn.gif", 6, 6);
		StdDraw.picture(w - 1, h - 1, "gif/schlumpf.gif", 4, 4, 30);

	}

	private static void unilogo() {
		StdDraw.picture(-w + 2, -h + 4, "gif/uni_duesseldorf_logo.gif", 10, 7,
				-75);

	}

	private static void mainlevel() {

		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.filledSquare(0, 0, w);
		StdDraw.setPenColor(color);
		StdDraw.filledSquare(0, 0, w - 1);
		StdDraw.picture(w - 7, -h + 7, "gif/Jabba_Granate.gif", 15, 15);
		levelbrand();

		String message = "Kampflevel: Erprobe deine Skills";
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(0, -h - 1, message);
		action1();

	}

	// ////////////////////////////////////////////////////////////////////////////////
	// /Events
	// ///////////////////////////////////////////////////////////////////////////////

	private static void colorwall() {
		// Rechter Rand Event
		counter++;
		plr.x = 0;
		plr.y--;
		if (counter == 1) {
			plr.x = w / 2;
		}

		StdAudio.play("audio/cool.wav");
		StdAudio.play("audio/right.wav");
		
			double r = (Math.random() * 9);

			int R = (int) (Math.random() * 256);
			int G = (int) (Math.random() * 256);
			int B = (int) (Math.random() * 256);
			Color randomColor = new Color(R, G, B);
			StdDraw.setPenColor(randomColor);

			StdDraw.filledCircle(plr.x, plr.y * r, 5 * r);
			StdDraw.circle(plr.x, plr.y * r, 2 * r);
			color = randomColor;
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.textLeft(-4, -h - 1,
					"Dieser Level scheint gerade verschlossen zu sein.");
			StdDraw.show(30);
		

		counter = 0;
	}

	private static void apfel() {
		// Unterer Rand Event
		counter++;
		apfelcounter++;
n=0;
		if (counter >= 1) {
			plr.y = -h + 3;
		}

		StdAudio.play("audio/center.wav");

		int d = 6;

		StdDraw.picture(plr.x, d, "gif/apple.gif", 3, 3, 45);

		d = d - 8;
		StdDraw.picture(plr.x, d, "gif/apple.gif", 4, 4, 120);

		StdDraw.picture(plr.x, plr.y + 5, "gif/apple.gif", 6, 6, 200);
		StdDraw.picture(plr.x - 1, plr.y + 3, "gif/star.gif", 2, 2, 200);
		StdDraw.picture(plr.x + 1, plr.y + 2, "gif/star.gif", 1, 1, 180);
		StdDraw.text(0, 0, "Obst: " + apfelcounter);
		StdDraw.show(80);
		counter = 0;
	}

	// ////////////////////////////////////////////////////////////////////////////////
	// /Effekte
	//
	// einige levelabhängige Aktionen
	//
	// ///////////////////////////////////////////////////////////////////////////////

	private static void action1() {
		if (space) {


			StdDraw.picture(w-n, -h+n, "gif/star.gif", 3, 3, n*12);
			StdDraw.picture(w-n, h-n, "gif/star.gif", 3, 3,n*-12);
			StdDraw.picture(-w+n, -h+n, "gif/star.gif", 3, 3,n*12);
			StdDraw.picture(-w+n, h-n,  "gif/star.gif", 3, 3, n*-12);


		n++;
	

		}

	}

	// ////////////////////////////////////////////////////////////////////////////////
	// /Teleporter
	//
	// Spezialaktion Gelangt der Spieler in eine Ecke kann er sich in
	// die gegenüberliegende Ecke teleportieren.
	// ///////////////////////////////////////////////////////////////////////////////

	// Schlumpfteleporter

	private static void gate1() {
		if ((plr.x == w) && (plr.y == h)) {

			plr.y = -h;
			plr.x = -w;
			seqence1();
		}
	}

	private static void seqence1() {
		levelswitch = 1;

		if (soundswitch == 1) {
			StdAudio.play("audio/robot3.wav");
			soundswitch = 2;
		}
		if (soundswitch == 2) {
			StdAudio.play("audio/reverse.wav");
			StdAudio.play("audio/cc-warp.wav");
		}

		if (soundswitch == 0) {
			StdAudio.play("audio/horse.wav");
			soundswitch = 3;
		}

	}

	// Kamplevelteleporter

	private static void gate2() {
		if ((plr.x == 0) && (plr.y == h)) {

			plr.y = -h;
			plr.x = 0;
			seqence2();
		}
	}

	private static void seqence2() {
		StdAudio.play("audio/robot.wav");
		soundswitch = 2;
		levelswitch = 0;

	}

	// Unicornteleporter

	private static void gate3() {
		if ((plr.x == -w) && (plr.y == h)) {

			plr.y = -h;
			plr.x = w;
			seqence3();
		}
	}

	private static void seqence3() {
		if (soundswitch == 2) {
			StdAudio.play("audio/pony.wav");
		}
		StdAudio.play("audio/robot2.wav");

		levelswitch = 1;
		// hier könnte ein Bug auftreten wenn das levelswitch fehlt
		// muss nochuntersucht werden

	}

	private static void gate4() {
		if ((plr.x == w - 2) && (plr.y == -h)) {

			plr.y = -h;
			plr.x = w;
			seqence4();
		}
	}

	private static void seqence4() {
		if (soundswitch == 3) {
			StdAudio.play("audio/death_comp.wav");

		}

	}

}
