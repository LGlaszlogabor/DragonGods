package core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Character{
	int health;
	int x,y;
	int maxstate;
	float state;
	ArrayList<BufferedImage> imgs;
	String name;
	char[] h;
	Character(String n,int h, int max, int xx, int yy){
		this.h = new String("Health:").toCharArray();
		name = n;
		health = h;
		maxstate = max;
		x = xx;
		y = yy;
		state = 0;
		imgs = new ArrayList<BufferedImage>();
		for(int i=0;i<maxstate;i++){
			try {
				imgs.add(ImageIO.read(new File("src\\characters\\"+name+"\\"+name+i+".png")));
			} catch (IOException e) {
				System.out.println("HIBA!!!Nem lehetett betoltetni "+name+" kepeit!");
			}
		}
	}
	public void draw(Graphics g){
		g.drawImage(imgs.get((int) Math.floor(state)), x, y, null);
		if(health > 0) {
			g.setColor(Color.WHITE);
			g.drawChars(h, 0, 7, 10, 25);
			g.setColor(Color.RED);
			g.fill3DRect(50, 10, 10+health, 20, true);
		}
		
		if(state<maxstate-1) state+=maxstate/10.0;
		else state=0;
	}
	public void setPosition(int xx, int yy){
		x=xx; y=yy;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getWidth(){
		return imgs.get(0).getWidth();
	}
	public int getHeight(){
		return imgs.get(0).getHeight();
	}
}
