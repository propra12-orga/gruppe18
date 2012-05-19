
public class Umgebung extends Mauer { 
	
	
	public Umgebung(boolean raised, Mauer collisionrectangle,
			Mauer breakablerectangle, Mauer fieldrectangle) {
		super(raised, collisionrectangle, breakablerectangle, fieldrectangle);
	}


	public String raumname; //Als Variable, die eventuelle Levelnamen aufnehmen und in der GUI ausgeben soll
	public static int i = 10; //Arrayspalten
	public static int j = 10; //Arrayzeilen
	public int a = 0;
	public int b = 0; 
	public int c = 0; //Zählervariablen
	public static Mauer[][] LevelMauer = new Mauer[i][j];
	
	/*Die Schleife soll das Array füllen, was dann später über die JFrame Elemente ausgegeben wird
	-> if (a % 3 != 0) Die drei habe ich als möglichen Parameter zur Berechnung der Position auf dem Spielfeld 
	genommen.
	
	*/
	public void LevelArray (Mauer[][] LevelMauer){
	
	while (c <= LevelMauer.length) {
		
		for (a = 0; a <= i; a++) {
			for (b = 0; b <= j; b++){
				
				LevelMauer[i][j] = collisionrectangle;
				
					if (a % 3 != 0) {
						
						LevelMauer[i][j] = breakablerectangle;
						
					}
				
					else { LevelMauer[i][j] = fieldrectangle; }
			
			}
			
		}
		c++;
	}
}
	
}	
