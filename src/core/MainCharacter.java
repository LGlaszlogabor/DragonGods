package core;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class MainCharacter extends Character implements Move,Shoot{
	ArrayList<Bullet> bullets;
	ArrayList<BufferedImage> shooting;
	float shootState;
	boolean isShooting;
	int loadnr;
	int bulletnr;
	int bulletspeed;
	public MainCharacter(String n, int h, int max,int maxload, int xx, int yy) {
		super(n, h, max, xx, yy);
		bulletnr = 16;
		bulletspeed = 5;
		bullets = new ArrayList<Bullet>();
		for(int i=0; i<bulletnr;i++)
			bullets.add(new Bullet("goku",2,1,0));
		loadnr = maxload;
		shooting = new ArrayList<BufferedImage>();
		for(int i=0;i<loadnr;i++)
			try {
				shooting.add(ImageIO.read(new File("src\\characters\\"+name+"\\shoot"+i+".png")));
			} catch (IOException e) {
				System.out.println("HIBA! Nem lehet betolteni az lovesi animaciot!");
			}
		isShooting = false;
	}
	public void shoot(int x, int y) {
		if(!isShooting){
			int i = 0;
			do{
				if(!bullets.get(i).isActive()){
					bullets.get(i).setPos(x, y);
					break;
				}
				i++;
			}while(i<bullets.size());
			bullets.get(i).activate();
			isShooting = true;
		}
	}
	public void draw(Graphics g){
		if(isShooting){
			g.drawImage(shooting.get((int) Math.floor(shootState)), x, y, null);
		}
		else{
			g.drawImage(imgs.get((int) Math.floor(state)), x, y, null);
		}
		if(health > 0) {
			g.setColor(Color.WHITE);
			g.drawChars(h, 0, 7, 10, 25);
			g.setColor(Color.RED);
			g.fill3DRect(50, 10, 10+health, 20, true);
		}
		for(int i=0;i<bullets.size();i++){
			bullets.get(i).checkActivity();
			if(bullets.get(i).isActive()){
				bullets.get(i).E(bulletspeed);
			}
			bullets.get(i).draw(g);
		}
		if(isShooting){
			if(shootState<loadnr-1) shootState+=loadnr/10.0;
			else {
				shootState = 0;
				isShooting = false;
			}
		}
		else{
			if(state<maxstate-1) state+=maxstate/10.0;
			else state=0;
		}
	}
	public void move(int mx,int my){
		if(my-25+imgs.get(0).getHeight()<600)y = my-25;
		x = mx/2;
	}
	public void E(int dx) {
		x+=dx;
	}
	public void W(int dx) {
		x-=dx;
	}
	public void N(int dy) {
		y-=dy;
	}
	public void S(int dy) {
		y+=dy;
	}
	public void resetToDefaults(){
		health = 200;
		isShooting = false;
		state = 0;
		shootState = 0;
		for(int i=0;i<bulletnr;i++){
			bullets.get(i).resetToDefaults();
		}
	}
	public void isHit(int xx,int yy,int w,int h){ 
		if(x+getWidth()-20>xx && x<xx+w-20 && y+getHeight()-20>yy && y<yy+h-20)
			health--;
	}
	public boolean bulletCollision(int xx,int yy,int w,int h){
		boolean hit = false, tmp;
		for(int i=0;i<bulletnr;i++){
			tmp = bullets.get(i).isHit(xx,yy,w,h);
			hit = hit || tmp;
		}
		return hit;
	}
	public void boom(int diff){
		if(diff == 1) health -= 2;
		if(diff == 2) health -= 10;
		if(diff == 3) health -= 50;
	}
}
