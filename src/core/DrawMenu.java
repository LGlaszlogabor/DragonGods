package core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DrawMenu{
	Character demo;
	ArrayList<ArrayList<BufferedImage>> images;
	ArrayList<ArrayList<BufferedImage>> imageson;
	ArrayList<BufferedImage> menuBackgrounds;
	ArrayList<ArrayList<JButton>> buttons;
	JRadioButton easy,medium, hard;
	ButtonGroup difficulty;
	int difficult;
	boolean finished;
	int menulevel;
	public DrawMenu(Menu m,JPanel f) {
		finished = false;
		demo = new Character("goku",0,4,200,200);
		difficult = 1;
		easy = new JRadioButton();
		medium = new JRadioButton();
		hard = new JRadioButton();
		easy.setBackground(Color.blue);
		medium.setBackground(Color.blue);
		hard.setBackground(Color.blue);
		difficulty = new ButtonGroup();
		difficulty.add(easy);
		difficulty.add(medium);
		difficulty.add(hard);
		easy.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent arg0) {
				if(((JRadioButton)arg0.getSource()).isSelected()){
					difficult=1;
				}
			}
		});
		medium.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent arg0) {
				if(((JRadioButton)arg0.getSource()).isSelected()){
					difficult=2;
				}
			}
		});
		hard.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent arg0) {
				if(((JRadioButton)arg0.getSource()).isSelected()){
					difficult=3;
				}
			}
		});
		easy.setSelected(true);
		images = new ArrayList<ArrayList<BufferedImage>>();
		imageson = new ArrayList<ArrayList<BufferedImage>>();
		buttons = new ArrayList<ArrayList<JButton>>();
		menuBackgrounds = new ArrayList<BufferedImage>();
		for(int i=0;i<m.getMainSize();i++){
			try {
				menuBackgrounds.add(ImageIO.read(new File("src\\menu\\backgrounds\\"+i+".png")));
			} catch (IOException e) {
				System.out.println("HIBA!!!Nem lehet betolteni a hatterkepeket!");
				e.printStackTrace();
			}
			for(int j=0;j<m.getSize(i);j++){
				addImage(m.get(i,j),i);
			}
		}
		for(int i=0;i<m.getMainSize();i++){
			for(int j=0;j<buttons.get(i).size();j++){
				f.add(buttons.get(i).get(j));
			}
		}
		f.add(easy);
		f.add(medium);
		f.add(hard);
		menulevel = 0;
		for(int i=0;i<buttons.size();i++){
			for(int j=0;j<buttons.get(i).size();j++){
				if(j==0) 	buttons.get(i).get(j).setVisible(true);
				else buttons.get(i).get(j).setVisible(false);
			}
		}
		
		easy.setVisible(false);
		medium.setVisible(false);
		hard.setVisible(false);
	}
	void addImage(String s,final int nr){
		try{
			if(images.isEmpty()){
				images.add(new ArrayList<BufferedImage>());
				images.get(0).add(ImageIO.read(new File("src\\menu\\"+s+".png")));
				imageson.add(new ArrayList<BufferedImage>());
				imageson.get(0).add(ImageIO.read(new File("src\\menu\\"+s+"c.png")));
				buttons.add(new ArrayList<JButton>());
				JButton tmp = new JButton();
				tmp.setIcon(new ImageIcon(images.get(0).get(0)));
				tmp.setPreferredSize(new Dimension(180,30));
				tmp.addMouseListener(new MouseAdapter(){
					public void mouseEntered(MouseEvent arg0) {
						((JButton)arg0.getSource()).setIcon(new ImageIcon(imageson.get(0).get(0)));
					}
					public void mouseExited(MouseEvent arg0) {
						((JButton)arg0.getSource()).setIcon(new ImageIcon(images.get(0).get(0)));
					}
					public void mousePressed(MouseEvent arg0){
						menuCommand(nr+buttons.get(nr).indexOf((JButton)arg0.getSource()));
					}
				});
				buttons.get(0).add(tmp);
			}
			else{
				if(images.size()>nr){
					images.get(nr).add(ImageIO.read(new File("src\\menu\\"+s+".png")));
					imageson.get(nr).add(ImageIO.read(new File("src\\menu\\"+s+"c.png")));
					JButton tmp = new JButton();
					tmp.setIcon(new ImageIcon(images.get(nr).get(images.get(nr).size()-1)));
					tmp.setPreferredSize(new Dimension(180,30));
					tmp.addMouseListener(new MouseAdapter(){
						public void mouseEntered(MouseEvent arg0) {
							((JButton)arg0.getSource()).setIcon(new ImageIcon(imageson.get(nr).get(buttons.get(nr).indexOf((JButton)arg0.getSource()))));
						}
						public void mouseExited(MouseEvent arg0) {
							((JButton)arg0.getSource()).setIcon(new ImageIcon(images.get(nr).get(buttons.get(nr).indexOf((JButton)arg0.getSource()))));
						}
						public void mousePressed(MouseEvent arg0){
							menuCommand(nr+buttons.get(nr).indexOf((JButton)arg0.getSource()));
						}
					});
					buttons.get(nr).add(tmp);
				}
				else{
					images.add(new ArrayList<BufferedImage>());
					images.get(nr).add(ImageIO.read(new File("src\\menu\\"+s+".png")));
					imageson.add(new ArrayList<BufferedImage>());
					imageson.get(nr).add(ImageIO.read(new File("src\\menu\\"+s+"c.png")));
					buttons.add(new ArrayList<JButton>());
					JButton tmp = new JButton();
					tmp.setIcon(new ImageIcon(images.get(nr).get(images.get(nr).size()-1)));
					tmp.setPreferredSize(new Dimension(180,30));
					tmp.addMouseListener(new MouseAdapter(){
						public void mouseEntered(MouseEvent arg0) {
							((JButton)arg0.getSource()).setIcon(new ImageIcon(imageson.get(nr).get(buttons.get(nr).indexOf((JButton)arg0.getSource()))));
						}
						public void mouseExited(MouseEvent arg0) {
							((JButton)arg0.getSource()).setIcon(new ImageIcon(images.get(nr).get(buttons.get(nr).indexOf((JButton)arg0.getSource()))));
						}
						public void mousePressed(MouseEvent arg0){
							menuCommand(nr+buttons.get(nr).indexOf((JButton)arg0.getSource()));
						}
					});
					buttons.get(nr).add(tmp);
				}
			}
		} catch (IOException e) {
			System.out.println("HIBA!!!Nem lehet betolteni a kepeket!");
		}
	}
	public void draw(Graphics g){
		Random rand = new Random();		
		g.drawImage(menuBackgrounds.get(menulevel), 0, 0, null); 
		switch(menulevel){
			case 0:
				for(int i=0;i<buttons.size();i++){
					buttons.get(i).get(0).setLocation(550, 150+50*i);	
				}
				demo.draw(g);
				demo.setPosition(demo.getX()+rand.nextInt(3)-1,demo.getY()+rand.nextInt(3)-1);
				break;
			case 1:
				buttons.get(0).get(1).setLocation(80,140);
				buttons.get(0).get(2).setLocation(80,440);
				break;
			case 2:
				buttons.get(1).get(1).setLocation(80,440);
				break;
			case 3:
				buttons.get(2).get(1).setLocation(80,440);
				break;
			case 4:
				buttons.get(3).get(1).setLocation(300,440);
				easy.setLocation(80, 220);
				medium.setLocation(380, 220);
				hard.setLocation(660, 220);
				break;
		}
	}
	void menuCommand(int index){
		if(menulevel==0){
			switch(index){
				case 0:
					for(int i=0;i<buttons.size();i++){
						for(int j=0;j<buttons.get(i).size();j++){
							if((j==1 || j==2) && i==0) 	buttons.get(i).get(j).setVisible(true);
							else buttons.get(i).get(j).setVisible(false);
						}
					}
					menulevel = 1;
					break;
				case 1:
					for(int i=0;i<buttons.size();i++){
						for(int j=0;j<buttons.get(i).size();j++){
							if(j==1 && i==1) 	buttons.get(i).get(j).setVisible(true);
							else buttons.get(i).get(j).setVisible(false);
						}
					}
					menulevel = 2;
					break;
				case 2:
					for(int i=0;i<buttons.size();i++){
						for(int j=0;j<buttons.get(i).size();j++){
							if(j==1 && i==2) 	buttons.get(i).get(j).setVisible(true);
							else buttons.get(i).get(j).setVisible(false);
						}
					}
					menulevel = 3;
					break;
				case 3:
					for(int i=0;i<buttons.size();i++){
						for(int j=0;j<buttons.get(i).size();j++){
							if(j==1 && i==3) 	buttons.get(i).get(j).setVisible(true);
							else buttons.get(i).get(j).setVisible(false);
						}
					}
					menulevel = 4;
					easy.setVisible(true);
					medium.setVisible(true);
					hard.setVisible(true);
					break;
				case 4:
					System.exit(0);
					break;
			}
		}
		else if(menulevel==1){
			switch(1-(index-1)){
				case 0:
				for(int i=0;i<buttons.size();i++){
					for(int j=0;j<buttons.get(i).size();j++){
						if(j==0) 	buttons.get(i).get(j).setVisible(true);
						else buttons.get(i).get(j).setVisible(false);
					}
				}
				easy.setVisible(false);
				medium.setVisible(false);
				hard.setVisible(false);
				menulevel=0;
				break;
				case 1:
					for(int i=0;i<buttons.size();i++){
						for(int j=0;j<buttons.get(i).size();j++){
							buttons.get(i).get(j).setVisible(false);
						}
					}
					finished = true;
					break;
			}
		}else if(menulevel==2){
			for(int i=0;i<buttons.size();i++){
				for(int j=0;j<buttons.get(i).size();j++){
					if(j==0) 	buttons.get(i).get(j).setVisible(true);
					else buttons.get(i).get(j).setVisible(false);
				}
			}
			easy.setVisible(false);
			medium.setVisible(false);
			hard.setVisible(false);
			menulevel=0;
		}else if(menulevel==3){
			for(int i=0;i<buttons.size();i++){
				for(int j=0;j<buttons.get(i).size();j++){
					if(j==0) 	buttons.get(i).get(j).setVisible(true);
					else buttons.get(i).get(j).setVisible(false);
				}
			}
			easy.setVisible(false);
			medium.setVisible(false);
			hard.setVisible(false);
			menulevel=0;
		}else if(menulevel==4){
			for(int i=0;i<buttons.size();i++){
				for(int j=0;j<buttons.get(i).size();j++){
					if(j==0) 	buttons.get(i).get(j).setVisible(true);
					else buttons.get(i).get(j).setVisible(false);
				}
			}
			easy.setVisible(false);
			medium.setVisible(false);
			hard.setVisible(false);
			menulevel=0;
		}
	}
	public int getDifficulty(){
		return difficult;
	}
	public boolean isFinished(){
		return finished;
	}
	public void activate(PauseMenu pm){
		for(int i=0;i<buttons.size();i++){
			for(int j=0;j<buttons.get(i).size();j++){
				if((j==1 || j==2) && i==0) 	buttons.get(i).get(j).setVisible(true);
				else buttons.get(i).get(j).setVisible(false);
			}
		}
		menulevel = 1;
		pm.noMoreActivation();
		finished = false;
	}
}