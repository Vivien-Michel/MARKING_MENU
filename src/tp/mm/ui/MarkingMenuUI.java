package tp.mm.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tp.mm.test.Action;

public class MarkingMenuUI {

	Map<Integer,Rectangle> items= new HashMap<Integer,Rectangle>();
	private static final int WIDTH = 100;
	private static final int HEIGHT = 100;
	private double ANGLE;
	private static final int RAYON=150;
	private boolean active = false;
	private Action[] actions;
	private Action[] originalActions;
	private List<Point> points = new ArrayList<Point>();
	
	public MarkingMenuUI(Action[] actions){
		this.originalActions=actions;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public void setpoints(List<Point> points){
		this.points=points;
	}
	
	public void paint(Graphics2D g2){
		if(active){
			int i =0;
			for(java.util.Map.Entry<Integer, Rectangle> entry : items.entrySet()){
				g2.setColor(Color.black);
				Rectangle rect= entry.getValue();
				g2.drawRect(rect.x, rect.y, rect.width, rect.height);
				g2.drawString(actions[i].getName(), (int)(rect.getCenterX()-rect.width/4), (int)rect.getCenterY());
				i++;
			}
		}
		for(int i =0; i<points.size()-1;i++){
			if(points.size()>=2){
				g2.setColor(Color.black);
				g2.drawLine(points.get(i).x,points.get(i).y, points.get(i+1).x,points.get(i+1).y);
			}
		}
	}
	
	public void setRectangle(Point p){
		int nbItem=actions.length;
		items.clear();
		for(int i =0;i<nbItem;i++){
			Rectangle rect = new Rectangle (0,0,WIDTH,HEIGHT);
			items.put(i, rect);
		}
		ANGLE=Math.toRadians(360/nbItem);
		double angle = ANGLE;
		for(java.util.Map.Entry<Integer, Rectangle> entry : items.entrySet()){
			Rectangle rect= entry.getValue();
			double x = p.x + Math.cos(angle)*RAYON;
			double y = p.y + Math.sin(angle)*RAYON;
			x = x - rect.width/2;
			y = y - rect.height/2;
			rect.setLocation((int)Math.ceil(x), (int)Math.ceil(y));
			angle+=ANGLE;
		}
	}
	
	public int selectedItem(List<Point> points){
		int index=-1;
		for(Point point : points){
			for(java.util.Map.Entry<Integer, Rectangle> entry : items.entrySet()){
				Rectangle rect= entry.getValue();
				if(rect.contains(point)){
					index=entry.getKey();
				}
			}
		}
		if(index > -1 ){
			if(actions[index].getActions().length == 0){
				actions[index].action();
			}else{
				actions=actions[index].getActions();
				ANGLE=Math.toRadians(360/actions.length);
				setRectangle(points.get(points.size()-1));
			}
		}
		return index;
	}
	
	public void init(){
		actions=originalActions;
	}
}
