import javax.swing.JFrame;

public class Viewer extends JFrame {

	public Viewer() {
		add(new Brett());

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setLocationRelativeTo(null);
		setTitle("Bomberman Collision Tester");
		setResizable(false);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Viewer();
	}
}