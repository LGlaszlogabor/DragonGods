package core;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Explosion extends Thread{
	int x,y;
	int sx,sy;
	BufferedImage sprite;
	boolean active;
	Explosion(){
		active = false;
		sx = 0; sy = 0;
		try {
			sprite = ImageIO.read(new File("src\\others\\explosions\\explosion0.png"));
		} catch (IOException e) {
			System.out.println("HIBA!!!Nem lehet betolteni a robbanas spriteot");
		}
	}
	public void activate(int xx,int yy){
		active = true;
		x = xx; y = yy;
		sx = 0; sy = 0;
	}
	public boolean isActive(){
		return active;
	}
	public void draw(Graphics g){
		if(active){
			g.drawImage(sprite, x, y, x+100, y+100, 100*sx, 100*sy, 100*(sx+1), 100*(sy+1), null);
		}
	}
	public void run(){
		while(sx*sy<64){
			if(sx<8){
				sx++;
			}
			else{
				sx = 0;
				sy++;
			}
			try {
	          	sleep((int)(20));
		   } catch (InterruptedException e) {}
		}
		active = false;
	}
}
