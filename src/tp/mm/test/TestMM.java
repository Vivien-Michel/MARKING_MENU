package tp.mm.test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

import tp.mm.listener.MarkingMenuListener;
import tp.mm.ui.MarkingMenuUI;

public class TestMM {
	
	private static JPanel panel;
	
	public static void main(String[] args){
		
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setMinimumSize(new Dimension(800,600));
				List<String> itemName=new ArrayList<String>();
				itemName.add("Copier");
				itemName.add("Coller");
				itemName.add("Couper");
				itemName.add("Enregistrer");
				final MarkingMenuUI menu = new MarkingMenuUI(4,itemName);
				
				panel = new JPanel(){
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					public void paintComponent(Graphics g){
						super.paintComponent(g);
						Graphics2D g2 = (Graphics2D) g;
						g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						g2.setColor(Color.white);
						g2.fillRect(0, 0, getWidth(), getHeight());
						menu.paint(g2);
					}
				};
				MouseInputAdapter listener = new MarkingMenuListener(menu,panel);
				panel.addMouseListener(listener);
				panel.addMouseMotionListener(listener);
				frame.add(panel);
				frame.setVisible(true);
				frame.pack();
				
			}
		});
	}
}
