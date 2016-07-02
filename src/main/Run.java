package main;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;

import core.DrawMenu;
import core.Menu;
import core.PauseMenu;

public class Run extends JFrame implements MouseMotionListener, MouseInputListener, KeyListener{
	int width,height;
	private JPanel panel = new Rajzol();
	Timer t;
	Menu m;
	DrawMenu md;
	PauseMenu pauseM;
	Game g;
	int difficulty;
	public Run(){
		addMouseMotionListener(this);
		addMouseListener(this);
		addKeyListener(this);
		setFocusable(true);
		m = new Menu();
		m.add("start_game", 0);
		m.add("start_game", 0);
		m.add("back",0);
		m.add("load_game",1);
		m.add("back", 1);
		m.add("instructions",2);
		m.add("back", 2);
		m.add("settings",3);
		m.add("back", 3);
		m.add("exit_game",4);
		md = new DrawMenu(m,panel);
		pauseM = new PauseMenu(panel);
		width=800;
		height=598;
		setBounds(0,0,width,height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(panel);
		setVisible(true);
		setResizable(false);
		g = new Game();
		t = new Timer(50, new ActionListener() {
	          public void actionPerformed(ActionEvent e) {
	              panel.repaint();
	          }
	      });
		t.start();
	}
	public class Rajzol extends JPanel{
		Rajzol(){
			setBounds(0,0,width,height);
			setPreferredSize(new Dimension(width,height));
		}
		public void paintComponent(Graphics gr){
			if(pauseM.activateMainMenu() && md.isFinished()) {
				g.resetToDefaults();
				md.activate(pauseM);
				g.setDifficulty(md.getDifficulty());
			}
			if(!md.isFinished()) {
				if(g != null) g.setDifficulty(md.getDifficulty());
				md.draw(gr);
			}
			else if(!pauseM.isActive()){
					g.draw(gr);
				}
				else{
					pauseM.draw(gr);
				}
		}
	}
	public static void main(String[] args){
		Run jatek=new Run();
	}
	public void mouseDragged(MouseEvent e) {
	}
	public void mouseMoved(MouseEvent e) {
		if(g != null) g.updateMousePos(e.getX(), e.getY());
	}
	public void mouseEntered(MouseEvent e) {
	
	}
	public void mouseExited(MouseEvent e) {
		
	}
	public void mousePressed(MouseEvent e) {
		if(g != null && md.isFinished()) g.mouseClickCommand();
	}
	public void mouseReleased(MouseEvent e) {

	}
	public void mouseClicked(MouseEvent e) {
		
	}
	public void keyPressed(KeyEvent e) {
		if(g != null && md.isFinished() && e.getKeyCode()==KeyEvent.VK_ESCAPE){
			pauseM.activate();
		}
	}
	public void keyReleased(KeyEvent e) {
	}
	public void keyTyped(KeyEvent e) {
	}
}
