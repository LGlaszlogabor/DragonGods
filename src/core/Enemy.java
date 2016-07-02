package core;

import java.awt.Graphics;
import java.util.Random;

public class Enemy extends Character{
	boolean active;
	int speed;
	public Enemy(String n, int h, int max, int xx, int yy) {
		super(n, h, max, xx, yy);
		active = true;
		Random rand = new Random();		
		speed = rand.nextInt(5)+3;
		state = rand.nextInt(maxstate);
	}
	public void activate(int h){
		active = true;
		Random rand = new Random();		
		init(800+rand.nextInt(200),rand.nextInt(550),rand.nextInt(5)+3,h);
	}
	public boolean isActive(){
		return active;
	}
	public void draw(Graphics g){
		if(active){
			g.drawImage(imgs.get((int) Math.floor(state)), x, y, null);
			if(state<maxstate-1) state+=maxstate/10.0;
			else state=0;
		}
	}
	public void init(int xx,int yy,int nspeed,int h){
		Random rand = new Random();	
		x = xx; y = yy; state = rand.nextInt(maxstate); speed = nspeed;
		health = h;
	}
	public void move(){
		x -= speed;
		Random rand = new Random();		
		int r = rand.nextInt(100);
		if(r<10 && y>20) y -= 20;
		if(r>90 && y<500) y += 20;
		if(x+getWidth()<0){
			active = false;
		}
	}
	public boolean boom(){
		health--;
		if(health == 0){
			active = false;
			x = 800; y = 0;
		}
		return active;
	}
	public void resetToDefaults(){
		Random rand = new Random();	
		init(800+rand.nextInt(200),rand.nextInt(550),rand.nextInt(5)+3,1);
	}
}
