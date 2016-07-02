package core;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PauseMenu {
	BufferedImage background,backImg,saveImg,exitImg,backImgc,saveImgc,exitImgc;
	JButton back,save,exit;
	boolean active;
	boolean activateMainMenu;
	public PauseMenu(JPanel p){
		try {
			background = ImageIO.read(new File("src\\menu\\backgrounds\\pause.png"));
			backImg = ImageIO.read(new File("src\\menu\\back.png"));
			saveImg = ImageIO.read(new File("src\\menu\\save_game.png"));
			exitImg = ImageIO.read(new File("src\\menu\\exit_game.png"));
			backImgc = ImageIO.read(new File("src\\menu\\backc.png"));
			saveImgc = ImageIO.read(new File("src\\menu\\save_gamec.png"));
			exitImgc = ImageIO.read(new File("src\\menu\\exit_gamec.png"));
		} catch (IOException e) {
			System.out.println("HIBA! Nem lehet betolteni a SzunetMenu hatteret.");
		}
		back = new JButton(); save = new JButton(); exit = new JButton();
		back.setIcon(new ImageIcon(backImg));
		back.setPreferredSize(new Dimension(180,30));
		back.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent arg0) {
				((JButton)arg0.getSource()).setIcon(new ImageIcon(backImgc));
			}
			public void mouseExited(MouseEvent arg0) {
				((JButton)arg0.getSource()).setIcon(new ImageIcon(backImg));
			}
			public void mousePressed(MouseEvent arg0){
				active = false;
				back.setVisible(false);
				save.setVisible(false);
				exit.setVisible(false);
			}
		});
		save.setIcon(new ImageIcon(saveImg));
		save.setPreferredSize(new Dimension(180,30));
		save.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent arg0) {
				((JButton)arg0.getSource()).setIcon(new ImageIcon(saveImgc));
			}
			public void mouseExited(MouseEvent arg0) {
				((JButton)arg0.getSource()).setIcon(new ImageIcon(saveImg));
			}
			public void mousePressed(MouseEvent arg0){
				
			}
		});
		exit.setIcon(new ImageIcon(exitImg));
		exit.setPreferredSize(new Dimension(180,30));
		exit.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent arg0) {
				((JButton)arg0.getSource()).setIcon(new ImageIcon(exitImgc));
			}
			public void mouseExited(MouseEvent arg0) {
				((JButton)arg0.getSource()).setIcon(new ImageIcon(exitImg));
			}
			public void mousePressed(MouseEvent arg0){
				activateMainMenu = true;
				back.setVisible(false);
				save.setVisible(false);
				exit.setVisible(false);
				active = false;
			}
		});
		p.add(back);
		p.add(save);
		p.add(exit);
		back.setVisible(false);
		save.setVisible(false);
		exit.setVisible(false);
		active = false;
		activateMainMenu = false;
	}
	public void draw(Graphics g){
		g.drawImage(background, 0, 0, null); 
		back.setLocation(320, 150);
		save.setLocation(320, 200);
		exit.setLocation(320, 250);
	}
	public void activate(){
		active = true;
		back.setVisible(true);
		save.setVisible(true);
		exit.setVisible(true);
	}
	public boolean isActive(){
		return active;
	}
	public boolean activateMainMenu(){
		return activateMainMenu;
	}
	public void noMoreActivation(){
		activateMainMenu = false;
	}
}
