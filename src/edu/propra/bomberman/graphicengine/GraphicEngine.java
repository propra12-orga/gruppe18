package edu.propra.bomberman.graphicengine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GraphicEngine {

	private SGPanel panel;
	private GraphicsEngineThread geT;

	public GraphicEngine() {
		panel = new SGPanel();
		this.geT=new GraphicsEngineThread(this);
	}

	

	public void startDrawing(){
		if(this.panel.isVisible()){
			Thread t=new Thread(geT);
			t.start();
		}
	}
	

	public SGScene getScene() {
		return panel.getScene();
	}
	
	/**
	 * @return the panel
	 */
	public SGPanel getPanel() {
		return panel;
	}

	/**
	 * @param panel the panel to set
	 */
	public void setPanel(SGPanel panel) {
		this.panel = panel;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame test = new JFrame();
		GraphicEngine ge=new GraphicEngine();
		SGPanel sgp = ge.getPanel();
		
		SGTransform t_area = new SGTransform();
		t_area.getTransform().setToTranslation(300, 300);
		SGTransform t_image = new SGTransform();
		t_image.getTransform().rotate(0.5);
		t_image.getTransform().translate(100, 100);
		SGTransform t_text = new SGTransform();
		t_text.getTransform().setToTranslation(0, 0);
		sgp.getScene().addChild(t_area);
		sgp.getScene().addChild(t_image);
		sgp.getScene().addChild(t_text);

		SGArea area = new SGArea();
		area.setClipArea(new Area(new Rectangle(0, 0, 100, 100)));
		area.setDrawarea(new Area(new Rectangle(40, 0, 20, 100)));
		area.getDrawarea().add(new Area(new Rectangle(0, 40, 100, 20)));
		area.setColor(new Color(0, 0, 255));
		t_area.setChild(area);

		SGText text = new SGText();
		text.setClipArea(new Area(new Rectangle(0, 0, 800, 600)));
		text.setText("Hallo Welt");
		text.setColor(new Color(0, 255, 0));
		t_text.setChild(text);
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("src/strawberry.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SGImage image = new SGImage();
		image.setClipArea(new Area(new Rectangle(0, 0, 80, 80)));
		image.setImage(img);
		t_image.setChild(image);

		test.setPreferredSize(new Dimension(800, 600));
		test.setContentPane(sgp);
		test.pack();
		test.setVisible(true);
		ge.startDrawing();
		
	}

}
