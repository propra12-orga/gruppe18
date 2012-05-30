import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import javax.swing.*;

public class Mauer extends Graphics {

	//public static int RECT;
	boolean raised = true;
	protected final Mauer collisionrectangle;
	protected final Mauer breakablerectangle;
	protected final Mauer fieldrectangle;

	
	
	//Rechteck für die Kollisionsbrechnung, zum Einfügen auf die jeweiligen Objekte
	/*public Mauer() {
		super();
		collisionrectangle = new Mauer();
		collisionrectangle.drawRect(0, 0, 2, 2);
		breakablerectangle = new Mauer();
		breakablerectangle.draw3DRect(0, 0, 2, 2, raised);
		fieldrectangle = new Mauer();
		fieldrectangle.drawRect(0, 0, 2, 2);	
		}*/

protected void paintBR ( Graphics breakablerectangle) {
	//super.paintComponent(breakablerectangle); Brauch das, dass steht so in der Java Insel und macht nur Fehlermeldungen
	breakablerectangle.setColor( new Color(150, 0, 0));
	breakablerectangle.fill3DRect(0, 0, 2, 2, raised);
	breakablerectangle.drawRect(0, 0, 2, 2);
	
}
protected void paintFR ( Graphics fieldrectangle) {
	//super.paintComponent(fieldrectangle); s. o.
	fieldrectangle.setColor( new Color(0, 150, 0));
	fieldrectangle.fillRect(0, 0, 2, 2);
	fieldrectangle.drawRect(0, 0, 2, 2);
	
}	
protected void paintCR ( Graphics collisionrectangle) {
	//super.paintComponent(collisionrectangle); s. o.
	collisionrectangle.setColor( new Color(0, 0, 150));
	collisionrectangle.fillRect(0, 0, 2, 2);
	collisionrectangle.drawRect(0, 0, 2, 2);	
}
	
	public Mauer(boolean raised, Mauer collisionrectangle,
			Mauer breakablerectangle, Mauer fieldrectangle) {
		super();
		this.raised = raised;
		this.collisionrectangle = collisionrectangle;
		this.breakablerectangle = breakablerectangle;
		this.fieldrectangle = fieldrectangle;
	}

	/*Diese ganzen @Override-geschichten hat Eclipse eingefügt und ich habe keine Ahnung weshalb.
	Nehme ich die raus, gibt das nur Fehlermeldungen, so nicht.
	Ich versuche mal herauszufinden, ob man die braucht, oder nicht.
	*/
	@Override
	public void clearRect(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void clipRect(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void copyArea(int x, int y, int width, int height, int dx, int dy) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Graphics create() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void drawArc(int x, int y, int width, int height, int startAngle,
			int arcAngle) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean drawImage(Image img, int x, int y, Color bgcolor,
			ImageObserver observer) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean drawImage(Image img, int x, int y, int width, int height,
			ImageObserver observer) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean drawImage(Image img, int x, int y, int width, int height,
			Color bgcolor, ImageObserver observer) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2,
			int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2,
			int sx1, int sy1, int sx2, int sy2, Color bgcolor,
			ImageObserver observer) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void drawLine(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void drawOval(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void drawRoundRect(int x, int y, int width, int height,
			int arcWidth, int arcHeight) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void drawString(String str, int x, int y) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void drawString(AttributedCharacterIterator iterator, int x, int y) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void fillArc(int x, int y, int width, int height, int startAngle,
			int arcAngle) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void fillOval(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void fillRect(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void fillRoundRect(int x, int y, int width, int height,
			int arcWidth, int arcHeight) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Shape getClip() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Rectangle getClipBounds() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Font getFont() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public FontMetrics getFontMetrics(Font f) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setClip(Shape clip) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setClip(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setColor(Color c) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setFont(Font font) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setPaintMode() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setXORMode(Color c1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void translate(int x, int y) {
		// TODO Auto-generated method stub
		
	}
	
		
}
