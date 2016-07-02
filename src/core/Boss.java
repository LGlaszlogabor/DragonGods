package core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class Boss extends Character{
	ArrayList<BufferedImage> deadimgs;
	ArrayList<Bullet> bullets;
	ArrayList<Bullet> superbullets;
	ArrayList<Bullet> upper,lower;
	ArrayList<BufferedImage> shooting;
	ArrayList<BufferedImage> deadshooting;
	float shootState;
	boolean isShooting;
	int loadnr;
	int bulletnr;
	int bulletspeed;
	int dy;
	int lvl;
	boolean deadStage;
	public Boss(String n, int h, int max,int maxload, int xx, int yy,int l) {
		super(n, h, max, xx, yy);
		deadStage = false;
		bulletnr = 20;
		bulletspeed = 5;
		dy = 5;
		lvl = l;
		if(lvl == 3) superbullets = new ArrayList<Bullet>();
		else superbullets = null;
		bullets = new ArrayList<Bullet>();
		for(int i=0; i<bulletnr;i++)
			bullets.add(new Bullet("boss",2,-1,2));
		if(lvl == 3){
			superbullets.add(new Bullet("mainboss",4,-1,0));
			superbullets.add(new Bullet("mainboss",4,-1,0));
			superbullets.add(new Bullet("mainboss",4,-1,0));
			superbullets.add(new Bullet("mainboss",4,-1,0));
			superbullets.add(new Bullet("mainboss",4,-1,0));
		}
		if(lvl == 1) upper = lower = null;
		else{
			upper = new ArrayList<Bullet>();
			lower = new ArrayList<Bullet>();
			for(int i=0; i<bulletnr;i++){
				lower.add(new Bullet("boss",2,-1,3));
				upper.add(new Bullet("boss",2,-1,1));
			}
		}
		deadimgs = null;
		if(lvl == 3){
			deadimgs = new ArrayList<BufferedImage>();
			for(int i=0;i<maxstate;i++){
				try {
					deadimgs.add(ImageIO.read(new File("src\\characters\\"+name+"\\"+name+"dead"+i+".png")));
				} catch (IOException e) {
					System.out.println("HIBA!!!Nem lehetett betoltetni "+name+" kepeit!");
				}
			}
			loadnr = maxload;
			shooting = new ArrayList<BufferedImage>();
			deadshooting = new ArrayList<BufferedImage>();
			for(int i=0;i<loadnr;i++)
				try {
					shooting.add(ImageIO.read(new File("src\\characters\\"+name+"\\shoot"+i+".png")));
					deadshooting.add(ImageIO.read(new File("src\\characters\\"+name+"\\shootdead"+i+".png")));
				} catch (IOException e) {
					System.out.println("HIBA! Nem lehet betolteni az lovesi animaciot!");
				}
			isShooting = false;
		}
	}
	public void shoot(int x, int y) {
		if(lvl == 3 || lvl == 2){
			if(!isShooting || lvl == 2){
				int i = 0;
				do{
					if(!bullets.get(i).isActive()){
						bullets.get(i).setPos(x, y);
						break;
					}
					i++;
				}while(i<bullets.size()-1);
				bullets.get(i).activate();
				i = 0;
				do{
					if(!upper.get(i).isActive()){
						upper.get(i).setPos(x, y);
						break;
					}
					i++;
				}while(i<upper.size()-1);
				upper.get(i).activate();
				i = 0;
				do{
					if(!lower.get(i).isActive()){
						lower.get(i).setPos(x, y);
						break;
					}
					i++;
				}while(i<lower.size()-1);
				lower.get(i).activate();
				if(lvl == 3) isShooting = true;
			}
		}
		else{
			int i = 0;
			do{
				if(!bullets.get(i).isActive()){
					bullets.get(i).setPos(x, y);
					break;
				}
				i++;
			}while(i<bullets.size()-1);
			bullets.get(i).activate();
		}
	}
	public void supershoot(int x,int y){
		int i = 0;
		do{
			if(!superbullets.get(i).isActive()){
				superbullets.get(i).setPos(x, y);
				break;
			}
			i++;
		}while(i<superbullets.size()-1);
		superbullets.get(i).activate();
	}
	public void draw(Graphics g){
		if(isShooting && lvl == 3){
			if(!deadStage) g.drawImage(shooting.get((int) Math.floor(shootState)), x, y, null);
			else g.drawImage(deadshooting.get((int) Math.floor(shootState)), x, y, null);
		}
		else{
			if(!deadStage) g.drawImage(imgs.get((int) Math.floor(state)), x, y, null);
			else g.drawImage(deadimgs.get((int) Math.floor(state)), x, y, null);
		}
		if(health > 0) {
			g.setColor(Color.WHITE);
			g.drawChars(h, 0, 7, 10, 25);
			g.setColor(Color.RED);
			g.fill3DRect(450, 550, health, 10, true);
		}
		for(int i=0;i<bullets.size();i++){
			bullets.get(i).checkActivity();	
			if(lvl != 1){
				upper.get(i).checkActivity();
				if(upper.get(i).isActive()){
					upper.get(i).N((int) Math.floor(bulletspeed*0.7));
					upper.get(i).W((int) Math.floor(bulletspeed*0.7));
				}
				lower.get(i).checkActivity();
				if(lower.get(i).isActive()){
					lower.get(i).S((int) Math.floor(bulletspeed*0.7));
					lower.get(i).W((int) Math.floor(bulletspeed*0.7));
				}
			}
			if(bullets.get(i).isActive()){
				bullets.get(i).W(bulletspeed);
			}
			if(lvl != 1){
				upper.get(i).draw(g);
				lower.get(i).draw(g);
			}
			bullets.get(i).draw(g);
		}
		if(lvl == 3){
			for(int i=0;i<superbullets.size();i++){
				superbullets.get(i).checkActivity();
				if(superbullets.get(i).isActive())
					superbullets.get(i).W(2*bulletspeed);
				superbullets.get(i).draw(g);
			}
		}
		if(isShooting && lvl == 3){
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
	public void move(){
		if(lvl == 1){
			if(x>500) x -= 5;
			if(y<0 || y+getHeight()>600) dy = -dy;
			y +=dy;
			if(y % 40 <= Math.abs(dy)) shoot(x,y+getHeight()/2);
		}
		if(lvl == 2){
			if(x>400) x -= 5;
			if(y<0 || y+getHeight()>600) dy = -dy;
			y +=dy;
			if(y % 40 <= Math.abs(dy)) shoot(x+200,y+getHeight()/2);
		}
		if(lvl == 3){
			if(x>600) x -= 5;
			if(y<0 || y+getHeight()>600) dy = -dy;
			y +=dy;
			Random rand = new Random();
			int k = rand.nextInt(10);
			if(y % 40 <= Math.abs(dy)){
				if(k == 0) supershoot(x-50,y+15);
				else shoot(x,y+15);
			}
		}
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
		if(lvl != 1){
			for(int i=0;i<bulletnr;i++){
				tmp = upper.get(i).isHit(xx,yy,w,h);
				hit = hit || tmp;
			}
			for(int i=0;i<bulletnr;i++){
				tmp = lower.get(i).isHit(xx,yy,w,h);
				hit = hit || tmp;
			}
		}
		
		return hit;
	}
	public boolean superbulletCollision(int xx,int yy,int w, int h){
		boolean hit = false, tmp;
		if(lvl == 3){
			for(int i=0;i<superbullets.size();i++){
				tmp = superbullets.get(i).isHit(xx,yy,w,h);
				hit = hit || tmp;
			}
		}
		return hit;
	}
	public void boom(int diff){
		if(diff == 1) health -= 10;
		if(diff == 2) health -= 5;
		if(diff == 3) health -= 1;
		if(health <= 0 && lvl == 3 && deadStage == false){
			deadStage = true;
			health = 300;
		}
	}
	public void resetToDefauts(){
		dy = 5;
		lvl = 1;
		Random rand = new Random();
		x = 800+rand.nextInt(600); y = rand.nextInt(250);
	}
	public boolean isAlive(){
		return health>0;
	}
}
