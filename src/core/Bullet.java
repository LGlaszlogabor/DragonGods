package core;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Bullet implements Move{
	int x, y;
	float state;
	int maxstate;
	boolean active;
	ArrayList<BufferedImage> imgs;
	BufferedImage sprite;
	int sx,sy;
	String type;
	Explosion e;
	int dir;
	int spritemode;
	Bullet(String t, int max,int d,int mode){
		active = false;
		x = 0; y = 0;
		type = t;
		dir = d;
		maxstate = max;
		spritemode = mode;
		sx = 0;
		sy = spritemode-1;
		if(spritemode == 0){
			imgs = new ArrayList<BufferedImage>();
			for(int i=0;i<maxstate;i++){
				try {
					imgs.add(ImageIO.read(new File("src\\others\\bullets\\"+type+"\\"+type+i+".png")));
				} catch (IOException e) {
					System.out.println("HIBA!!!Nem lehetett betoltetni "+type+" golyo kepeit!");
				}
			}
		}
		else{
			try {
				sprite = ImageIO.read(new File("src\\others\\bullets\\"+type+"\\"+type+".png"));
			} catch (IOException e) {
				System.out.println("HIBA!!!Nem lehetett betoltetni "+type+" golyo kepeit!");
			}
		}
	}
	public void draw(Graphics g){
		if(e!= null && e.isActive()) e.draw(g);
		if(active){
			if(spritemode == 0){
				g.drawImage(imgs.get((int) Math.floor(state)), x, y, null);
				if(state<maxstate-1) state+=maxstate/10.0;
				else state=0;
			}
			else{
				g.drawImage(sprite, x, y, x+64, y+64, 64*sx, 64*sy, 64*(sx+1), 64*(sy+1), null);
				if(sx<7) sx++;
				else sx = 0;
			}
		}
	}
	public void setPos(int xx,int yy){
		x=xx; y=yy;
	}
	public void checkActivity(){
		if((x > 800 && dir ==1) || (x+getWidth()<0 && dir == -1) || (y+getHeight()<0) || (y-getHeight()>600)) active = false;
	}
	public void E(int dx) {
		
		if(active) {
			x+=dx;
		}
	}
	public void W(int dx) {
		if(active) x-=dx;
	}
	public void N(int dy) {
		if(active) y-=dy;
	}
	public void S(int dy) {
		if(active) y+=dy;
	}
	public void activate(){
		if(!active) active = true;
	}
	public boolean isActive(){
		return active;
	}
	public void resetToDefaults(){
		active = false;
		state = 0;
	}
	public int getWidth(){
		if(spritemode == 0){
			return imgs.get(0).getWidth();
		}
		else return 64;
	}
	public int getHeight(){
		if(spritemode == 0){
			return imgs.get(0).getHeight();
		}
		else return 64;
	}
	public boolean isHit(int xx,int yy,int w,int h){ 
		if(active){
			if(x+getWidth()>xx && x<xx+w && y+getHeight()>yy && y<yy+h){
				e = new Explosion();
				if(dir == 1) e.activate(x+getWidth()/2, y-getHeight()/2);
				else e.activate(x-getWidth()/2, y-getHeight()/2);
				e.start();
				active = false;
				return true;
			}
			else{
				return false;
			}
		}
		else return false;
	}
}
