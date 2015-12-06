package tp.mm.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.TextArea;

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
				
				TextArea t = new TextArea();
				frame.add(t,BorderLayout.NORTH);
				
				Action[] actions={ new Action(t,"Salutation"){
										Action[] actions={
												new Action(getComponent(), "Hello"){
													public void action(){
														((TextArea) getComponent()).append("Hello ");
													}
												},
												new Action(getComponent(), "Bonjour"){
													public void action(){
														((TextArea) getComponent()).append("Bonjour ");
													}
												},
												new Action(getComponent(), "Salut"){
													public void action(){
														((TextArea) getComponent()).append("Salut ");
													}
												},
												new Action(getComponent(), "Hi"){
													public void action(){
														((TextArea) getComponent()).append("Hi ");
													}
												}
											
										};
										public Action[] getActions() {
											return actions;
										} 
									},
									new Action(t,"World"){
										public void action(){
											((TextArea) getComponent()).append("World ");
										}
									},
									new Action(t,"I'm"){
										public void action(){
											((TextArea) getComponent()).append("I'm ");
										}
									},
									new Action(t,"Happy"){
										public void action(){
											((TextArea) getComponent()).append("Happy ");
										}
									}
						
								};
				
				final MarkingMenuUI menu = new MarkingMenuUI(actions);
				
				panel = new JPanel(){
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
