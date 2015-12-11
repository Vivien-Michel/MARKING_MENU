package tp.mm.listener;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

import tp.mm.ui.MarkingMenuUI;

public class MarkingMenuListener extends MouseInputAdapter{
	
	private enum State{IDLE,PRESS,DRAG};
	State state=State.IDLE;
	Point current;
	Point origin;
	private JPanel panel;
	MarkingMenuUI markingMenuUI;
	List<Point> buffer = new ArrayList<Point>();
	private final int TIMER_AFF=670;
	private final int TIMER_SELECT=400;
	ScheduledFuture<?> futur=null;
	private ScheduledFuture<?> taskAfficher=null;
	private static double ANGLE_MIN=0.44;//Entre 0 et 1 car valeur du cos de l'angle
	
	
	private ScheduledExecutorService task = Executors.newSingleThreadScheduledExecutor();
	
	private TimerTask afficher = new TimerTask() {
		@Override
		public void run() {
			//Affichage menu
			markingMenuUI.setActive(true);
			panel.repaint();
		}
	};
	
	private TimerTask selection = new TimerTask() {
		
		@Override
		public void run() {
			if(origin.distance(current)>45){
				System.out.println("timer: "+markingMenuUI.selectedItem(buffer));
				origin=current;
			}
			panel.repaint();
			state=State.PRESS;
		}
	};
	
	public MarkingMenuListener(MarkingMenuUI markingMenuUI, JPanel panel){
		this.markingMenuUI = markingMenuUI;
		this.panel=panel;
	}
	
	@Override
	public void mousePressed(MouseEvent event){
		
		switch(state){
		case IDLE:
				if(SwingUtilities.isRightMouseButton(event)){
					origin=event.getPoint();
					markingMenuUI.init();
					markingMenuUI.setRectangle(origin);
					state=State.PRESS;
					taskAfficher=task.schedule(afficher, TIMER_AFF, TimeUnit.MILLISECONDS);
				}
				break;
		case PRESS:state=State.IDLE;break;
		case DRAG:state=State.IDLE;break;
		default:
			try {
				throw new Exception ("L'automate est dans un état incoherent");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent event){
		current = event.getPoint();
		switch(state){
			case IDLE:break;
			case PRESS:
				futur=task.schedule(selection, TIMER_SELECT, TimeUnit.MILLISECONDS);
				buffer.add(current);
				markingMenuUI.setpoints(buffer);
				panel.repaint();
				state=State.DRAG;
				break;
			case DRAG:
				futur.cancel(false);
				taskAfficher.cancel(false);
				buffer.add(current);
				markingMenuUI.setpoints(buffer);
				double cosAngle=cosAngleCalcul(buffer);
				if(origin.distance(current)>45 && cosAngle<ANGLE_MIN && cosAngle>(-ANGLE_MIN)){
					origin=current;
					System.out.println("cos angle: "+cosAngle);
					System.out.println("cos angle: "+markingMenuUI.selectedItem(buffer));
				}
				state=State.DRAG;
				panel.repaint();
				taskAfficher=task.schedule(afficher, TIMER_AFF, TimeUnit.MILLISECONDS);
				futur=task.schedule(selection, TIMER_SELECT, TimeUnit.MILLISECONDS);
				break;
			default:
				try {
					throw new Exception ("L'automate est dans un état incoherent");
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}
	
	private double cosAngleCalcul(List<Point> buffer){
		int size=buffer.size();
		double cosAngle=1;
		//Calcul de l'angle
		if(size>=8){
			Point p1 = buffer.get(size-8);
			Point p2 = buffer.get(size-5);
			double x1 = p2.x - p1.x;
			double y1 = p2.y - p1.y;
			
			double norme = Math.sqrt(Math.pow(x1, 2)+Math.pow(y1, 2));
			x1=x1/(norme);
			y1=y1/(norme);

			//Vect1
			p1 = buffer.get(size-4);
			p2 = buffer.get(size-1);
			double x2 = p2.x - p1.x;
			double y2 = p2.y - p1.y;
			
			norme = Math.sqrt(Math.pow(x2, 2)+Math.pow(y2, 2));
			x2=x2/(norme);
			y2=y2/(norme);
			cosAngle=dot(x1,y1,x2,y2);
		}
		return cosAngle;
	}
	
	private double dot(double x1, double y1, double x2, double y2) {
		return ((x1 * x2) + (y1 * y2));
	}

	@Override
	public void mouseReleased(MouseEvent event){
		current=event.getPoint();
		switch(state){
			case IDLE:break;
			case PRESS:
				taskAfficher.cancel(false);
				markingMenuUI.setActive(false);
				buffer.clear();
				panel.repaint();
				state=State.IDLE;
				break;
			case DRAG:
				taskAfficher.cancel(false);
				futur.cancel(false);
				markingMenuUI.setActive(false);
				if(origin.distance(current)>40){
					System.out.println("release: "+markingMenuUI.selectedItem(buffer));
				}
				buffer.clear();
				panel.repaint();
				state=State.IDLE;
				break;
			default:
				try {
					throw new Exception ("L'automate est dans un état incoherent");
				} catch (Exception e) {
					e.printStackTrace();
				}
	}
	}
	
	@Override
	public void mouseEntered(MouseEvent event){
	}
	
}
