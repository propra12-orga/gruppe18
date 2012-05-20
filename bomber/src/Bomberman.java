import edu.propra.bomberman.Singletons.SGameFrame;
import edu.propra.bomberman.ui.GameFrame;



public class Bomberman {

	private static GameFrame gf;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		gf=SGameFrame.get();
	}

}
