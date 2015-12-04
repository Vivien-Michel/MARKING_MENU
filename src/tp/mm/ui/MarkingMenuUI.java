package tp.mm.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class MarkingMenuUI {

	List<Rectangle> items= new ArrayList<Rectangle>();
	private static final int WIDTH = 100;
	private static final int HEIGHT = 100;
	private double ANGLE;
	private static final int RAYON=300;
	
	
	public MarkingMenuUI(int nbItem){
		for(int i =0;i<nbItem;i++){
			Rectangle rect = new Rectangle (0,0,WIDTH,HEIGHT);
			items.add(rect);
		}
		ANGLE=Math.toRadians(360/nbItem);
		
	}
	
	public void paint(Graphics2D g2){
		for(Rectangle rect : items){
			g2.setColor(Color.black);
			g2.drawRect(rect.x, rect.y, rect.width, rect.height);
			g2.drawString("blabla", (int)rect.getCenterX(), (int)rect.getCenterY());
		}
	}
	
	public void setRectangle(Point p){
		double angle = ANGLE;
		for(Rectangle rect : items){
			double x = p.x + Math.cos(angle)*RAYON;
			double y = p.y + Math.sin(angle)*RAYON;
			x = x - rect.width/2;
			y = y + rect.height/2;
			rect.setLocation((int)Math.ceil(x), (int)Math.ceil(y));
			angle+=ANGLE;
		}
	}
}
