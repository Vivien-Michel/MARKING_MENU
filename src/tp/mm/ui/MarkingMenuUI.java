package tp.mm.ui;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class MarkingMenuUI {

	List<Rectangle> items= new ArrayList<Rectangle>();
	private static final int WIDTH = 200;
	private static final int HEIGHT = 200;
	private double ANGLE;
	
	public MarkingMenuUI(int nbItem){
		for(int i =0;i<nbItem;i++){
			Rectangle rect = new Rectangle (0,0,WIDTH,HEIGHT);
			items.add(rect);
		}
		ANGLE=Math.toRadians(360/nbItem);
	}
	
	public void paint(){
		
	}
	
	public void setRectangle(Point p,List<String> namesItem){
		for(String itemName : namesItem){
			
		}
	}
}
