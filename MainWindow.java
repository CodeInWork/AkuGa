package org.AkuGanew.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.AkuGanew.*;

public class MainWindow extends JFrame {
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private ForeGroundObserver foreGroundObserver;
	private BackGroundObserver backGroundObserver; 
	private JPanel contentPanel;
		

	public MainWindow() {
		foreGroundObserver = new ForeGroundObserver();
		backGroundObserver = new BackGroundObserver();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		contentPanel = new JPanel();   
		setContentPane(contentPanel);
		setSize(300,300);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	
	
	@Override
	protected void processWindowEvent(WindowEvent e) {
	    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
	      System.out.println(WindowEvent.WINDOW_CLOSING);
	      dispose(); 
	      System.exit(0);
	      foreGroundObserver.shutdown();
	      backGroundObserver.shutdown();
	      
	    }
	    super.processWindowEvent(e); // Pass on the event
	}

	public void drawAction(Place place) {
		Knot knot = place.callPlace();   //get current state of the place
		
		// tests for playing background and introduction
		if (backGroundObserver.hasBackGround(knot.getBackGround())) {
			knot.setPlayBackGround(true);
		}
		if (backGroundObserver.hasForeGround(knot.getForeGround()) && !knot.isIntroduced()) {
			knot.setPlayForeGround(true);
			while (knot.getPlayForeGround()) {
           		try {
					Thread.sleep(20);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
           	}
			knot.setIntroduced(true);
		}		
		
		setTitle(knot.getName());
		ArrayList<Edge> edgeList = knot.getKanten();
		
		for (Edge anEdge : edgeList) {
			
			JButton neuBtn = new JButton(anEdge.getName());					
			neuBtn.addActionListener(new ActionListener() {
		        
		  	
				public void actionPerformed(ActionEvent e) {		               	
					    try {
							foreGroundObserver.testAudio(anEdge.getForeGround());
						} catch (FileNotFoundException e2) {
							e2.printStackTrace();
						}
						
						anEdge.setPlayForeGround(true);   //activates foreground -> noticed by foreGroundObserver -> run audio
						place.updatePlace(anEdge);
//		               	neuBtn.setEnabled(false);         //inhibits button
		               	
						pauseSkip();
						repaint();
//						try {
//							Thread.sleep(1000);
//						} catch (InterruptedException e2) {
//							// TODO Auto-generated catch block
//							e2.printStackTrace();
//						}
		               	while (anEdge.getPlayForeGround()) {
		               		try {
								Thread.sleep(20);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
		               	}
//		            	
		               	if (anEdge.getEnd()) { 
							klickedEnd();
							
		            	} else {
		            		refresh();
							drawAction(anEdge.getDestination());
		            	}
				}
			});
			contentPanel.add(neuBtn);
		}
		this.validate();
		
	}
	
	//Pause/Skip Window
	public void pauseSkip() {
		JButton pauseBtn = new JButton("Pause");
		JButton skipBtn = new JButton("Überspringen");
		
		pauseBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (backGroundObserver.isPlaying()) {
					backGroundObserver.pause(true);
					pauseBtn.setText("Play");
					backGroundObserver.setPlaying(false);
				} else {
					backGroundObserver.pause(false);
					pauseBtn.setText("Pause");
					backGroundObserver.setPlaying(true);
				}
				
			}
		});
		getContentPane().add(pauseBtn);
		this.validate();
		//ToDo: skipBtn action listener
	}
	
	//Method for invoking the game. Knot intro needs an introducing audio file. After it finished the method drawAction 
	//starts the actual game at the place start
	public void startGame(Knot intro, Place start) {
		
		if (foreGroundObserver.hasForeGround(intro.getForeGround())) {
			intro.setPlayForeGround(true);
			while (intro.getPlayForeGround()) {
           		try {
					Thread.sleep(20);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
           	}
			drawAction(start);
		} else {
			System.out.println("Intro nicht gesetzt");
		}
	}

	private void refresh() {
		this.getContentPane().removeAll();
		this.validate();
		this.repaint();
	}
	
	private void klickedEnd() {
		dispose();
	    foreGroundObserver.shutdown();
	    backGroundObserver.shutdown();
		System.exit(0);
	}
	
	public ForeGroundObserver getForeGroundObserver() {
		return foreGroundObserver;
	}

	public BackGroundObserver getBackGroundObserver() {
		return backGroundObserver;
	}
	
}
