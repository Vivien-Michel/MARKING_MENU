package tp.mm.listener;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
	private final int TIMER=500;
	private Timer timer = new Timer();
	
	private TimerTask tt = new TimerTask() {
		@Override
		public void run() {
			//Affichage menu
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
					state=State.PRESS;
					markingMenuUI.setRectangle(origin);
					System.out.println("PRESSED");
					panel.repaint();
					//timer.schedule(tt, TIMER);
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
				state=State.DRAG;
				break;
			case DRAG:
				tt.cancel();
				timer.purge();
				buffer.add(current);
				state=State.DRAG;
				timer.schedule(tt, TIMER);
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
	public void mouseReleased(MouseEvent event){
		current=event.getPoint();
		switch(state){
			case IDLE:break;
			case PRESS:
				tt.cancel();
				timer.purge();
				state=State.IDLE;
				buffer.clear();
				break;
			case DRAG:
				tt.cancel();
				timer.purge();
				buffer.add(current);
				
				buffer.clear();
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
