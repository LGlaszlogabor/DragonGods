package core;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class MiniBoss extends Enemy{
	boolean movestg1,movestg2,movestg3,movestg4;
	Explosion e1,e2,e3,e4;
	public MiniBoss(String n, int h, int max, int xx, int yy) {
		super(n, h, max, xx, yy);
		speed = 7;
		movestg1 = true;
		movestg2 = movestg3 = movestg4 = false;
	}
	public void draw(Graphics g){
		if(e1 != null && e1.isActive()) e1.draw(g);
		if(e2 != null && e2.isActive()) e2.draw(g);
		if(e3 != null && e3.isActive()) e3.draw(g);
		if(e4 != null && e4.isActive()) e4.draw(g);
		if(active){
			g.drawImage(imgs.get((int) Math.floor(state)), x, y, null);
			if(state<maxstate-1) state+=maxstate/10.0;
			else state=0;
		}
	}
	public void move(){
		if(movestg1){
			x -= 3*speed;
			if(x<0){
				movestg1 = false;
				movestg2 = true;
			}
		}else if(movestg2){
			x += speed;
			if(x+getWidth()>800){
				movestg2 = false;
				movestg3 = true;
			}
		}else if(movestg3){
			if(y>300) y += speed;
			else y -= speed;
			if(y+getHeight()>600 || y<0){
				movestg3 = false;
				movestg4 = true;
			}
		}else if(movestg4){
			x -= 2*speed;
			if(x+getWidth()<0){
				movestg4 = false;
				active = false;
			}
		}
	}
	public void activate(){
		active = true;
		movestg1 = true;
		Random rand = new Random();		
		init(800+rand.nextInt(200),rand.nextInt(550),rand.nextInt(5)+3,health);
	}
	public boolean boom(){
		health--;
		if(health == 0){ 
			e1 = new Explosion();
			e2 = new Explosion();
			e3 = new Explosion();
			e4 = new Explosion();
			e1.activate(x+getWidth()/2, y+getHeight()/2);
			e1.start();
			e2.activate(x, y);
			e2.start();
			e3.activate(x+getWidth()/4, y+getHeight()/4);
			e3.start();
			e4.activate(x+getWidth(), y+getHeight());
			e4.start();
			active = false;
			x = 800; y = 0;
		}
		return active;
	}
	public void resetToDefaults(){
		health = 5;
		movestg1 = true;
		movestg2 = movestg3 = movestg4 = false;
		active = true;
	}
	public boolean isAlive(){
		return health > 0;
	}
}
