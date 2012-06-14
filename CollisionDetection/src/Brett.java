
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Brett extends JPanel implements ActionListener {

	private Timer timer;
	private Player player;
	private ArrayList block;
	private boolean ingame;
	private int B_WIDTH;
	private int B_HEIGHT;

	// 2 Bloecke zum Testen 

	private int[][] pos = { { 200, 200 }, { 270, 5 }, { 300, 45 }};

	public Brett() {

		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		ingame = true;

		setSize(400, 300);

		player = new Player();

		initBlocks();

		timer = new Timer(5, this);
		timer.start();
	}

	public void addNotify() {
		super.addNotify();
		B_WIDTH = getWidth();
		B_HEIGHT = getHeight();
	}

	public void initBlocks() {
		block = new ArrayList();

		for (int i = 0; i < pos.length; i++) {
			block.add(new Block(pos[i][0], pos[i][1]));
		}
	}

	public void paint(Graphics g) {
		super.paint(g);

		if (ingame) {

			Graphics2D g2d = (Graphics2D) g;

			if (player.isVisible())
				g2d.drawImage(player.getImage(), player.getX(), player.getY(),
						this);

			ArrayList ms = player.getBombs();
			// Zeichnet Objekte aus der ArrayListe

			for (int i = 0; i < ms.size(); i++) {
				Bomb m = (Bomb) ms.get(i);
				g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
			}

			for (int i = 0; i < block.size(); i++) {
				Block a = (Block) block.get(i);
				if (a.isVisible())
					g2d.drawImage(a.getImage(), a.getX(), a.getY(), this);
			}

			g2d.setColor(Color.WHITE);
			g2d.drawString("Blocks left: " + block.size(), 5, 15);

		} else {
			String msg = "Game Over";
			Font small = new Font("Helvetica", Font.BOLD, 14);
			FontMetrics metr = this.getFontMetrics(small);

			g.setColor(Color.white);
			g.setFont(small);
			g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2,
					B_HEIGHT / 2);
		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void actionPerformed(ActionEvent e) {

		// Wenn alle Bloecke zersoert sind ist das Spiel beendet

		if (block.size() == 0) {
			ingame = false;
		}

		ArrayList ms = player.getBombs();

		for (int i = 0; i < ms.size(); i++) {
			Bomb m = (Bomb) ms.get(i);
			if (m.isVisible())
				foo();// Hier wird die Methode ausgeloest um die Bombe zu
						// zuenden
			else
				ms.remove(i);
		}

		for (int i = 0; i < block.size(); i++) {
			Block a = (Block) block.get(i);
			if (a.isVisible())
				foo();// Aktion von Bloecken
			else
				block.remove(i);
		}

		player.move();
		checkCollisions();
		repaint();
	}

	private void foo() {
		// TODO Auto-generated method stub

	}

	public void checkCollisions() {

		Rectangle r3 = player.getBounds();

		for (int j = 0; j < block.size(); j++) {
			Block a = (Block) block.get(j);
			Rectangle r2 = a.getBounds();

			if (r3.intersects(r2)) {
				player.setVisible(false);
				a.setVisible(false);
				ingame = false;
			}
		}

		// Es wird geprueft ob die Bombenrechtecke sich mit den Bloecken
		// ueberschneiden.
		// Wenn sie es tun, werden beide zerstört

		ArrayList ms = player.getBombs();

		for (int i = 0; i < ms.size(); i++) {
			Bomb m = (Bomb) ms.get(i);

			Rectangle r1 = m.getBounds();

			for (int j = 0; j < block.size(); j++) {
				Block a = (Block) block.get(j);
				Rectangle r2 = a.getBounds();

				if (r1.intersects(r2)) {
					m.setVisible(false);
					a.setVisible(false);
				}
			}
		}

	}

	private class TAdapter extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			player.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			player.keyPressed(e);
		}
	}
}