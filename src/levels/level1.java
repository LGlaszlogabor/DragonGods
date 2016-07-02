package levels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import core.Boss;
import core.Character;
import core.Enemy;
import core.MainCharacter;
import core.MiniBoss;

public class level1 {
	int timer,ftime;
	Character[] enemies;
	int enemyNr;
	BufferedImage layer1,intro,outro;
	int offsetX;
	int points;
	char[] p;
	int mouseX, mouseY;
	boolean finished;
	MainCharacter m;
	MiniBoss mb;
	Boss b;	
	int difficulty;
	boolean isSetDiff;
	public level1(MainCharacter mm,int p,int diff){
		difficulty = diff;
		isSetDiff = true;
		finished = false;
		points = p;
		timer = 0;
		ftime = 0;
		m = mm;
		offsetX = 0;
		try {
			layer1 = ImageIO.read(new File("src\\game\\level1\\layer1.png"));
			intro = ImageIO.read(new File("src\\game\\level1\\intro.png"));
			outro = ImageIO.read(new File("src\\game\\level1\\outro.png"));
		} catch (IOException e) {
			System.out.println("Nem lehet betolteni valamelyik layert");
		}
		enemyNr = 14;
		enemies = new Character[enemyNr];
		Random rand = new Random();		
		for(int i=0;i<enemyNr;i++){
			if(rand.nextInt(2)==0)
				enemies[i] = new Enemy("bat",difficulty,9,800+rand.nextInt(600),rand.nextInt(550));
			else enemies[i] = new Enemy("duck",difficulty,4,800+rand.nextInt(600),rand.nextInt(550));
		}
		//enemies[13] = new Enemy("redbird",1,8,800+rand.nextInt(600),rand.nextInt(550));
		mb = new MiniBoss("bird",5*difficulty,8,800+rand.nextInt(600),rand.nextInt(250));
		b = new Boss("bosslvl1",100,9,5,800+rand.nextInt(200),rand.nextInt(250),1);
	}
	public void draw(Graphics g){
		if(timer < 70){
			g.drawImage(intro, 0, 0, null); 
		}else{
			g.drawImage(layer1, offsetX, 0, null); 
			offsetX-=5;
			g.drawImage(layer1, offsetX+layer1.getWidth(), 0, null);
			if(offsetX<-layer1.getWidth()) offsetX=-5;
			if(timer<170){
				for(int i=0;i<enemyNr;i++){
					if(!((Enemy)enemies[i]).isActive()) ((Enemy)enemies[i]).activate(difficulty);
					enemies[i].draw(g);
					m.isHit(enemies[i].getX(),enemies[i].getY(),enemies[i].getWidth(),enemies[i].getHeight());
					if(m.bulletCollision(enemies[i].getX(),enemies[i].getY(),enemies[i].getWidth(),enemies[i].getHeight())){
						if(!((Enemy)enemies[i]).boom()) points += 100;
					}
					((Enemy)enemies[i]).move();
				}
			}
			else{
				if(timer < 470){
					for(int i=0;i<enemyNr;i++){
						if(((Enemy)enemies[i]).isActive()){
							enemies[i].draw(g);
							m.isHit(enemies[i].getX(),enemies[i].getY(),enemies[i].getWidth(),enemies[i].getHeight());
							if(m.bulletCollision(enemies[i].getX(),enemies[i].getY(),enemies[i].getWidth(),enemies[i].getHeight())){
								if(!((Enemy)enemies[i]).boom()) points += 100;
							}
							((Enemy)enemies[i]).move();
						}
					}
				}
				else if(timer<670 && mb.isAlive()){
					mb.draw(g);
					if(mb.isActive()){
						m.isHit(mb.getX(), mb.getY(), mb.getWidth(), mb.getHeight());
						if(m.bulletCollision(mb.getX(),mb.getY(),mb.getWidth(),mb.getHeight())){
							if(!mb.boom()) points += 1000;
						}
						mb.move();
					}
				}
				else if(timer<870){
					for(int i=0;i<enemyNr;i++){
						if(!((Enemy)enemies[i]).isActive()) ((Enemy)enemies[i]).activate(difficulty);
						enemies[i].draw(g);
						m.isHit(enemies[i].getX(),enemies[i].getY(),enemies[i].getWidth(),enemies[i].getHeight());
						if(m.bulletCollision(enemies[i].getX(),enemies[i].getY(),enemies[i].getWidth(),enemies[i].getHeight())){
							if(!((Enemy)enemies[i]).boom()) points += 100;
						}
						((Enemy)enemies[i]).move();
					}
				} else if(timer<1070){
					for(int i=0;i<enemyNr;i++){
						if(((Enemy)enemies[i]).isActive()){
							enemies[i].draw(g);
							m.isHit(enemies[i].getX(),enemies[i].getY(),enemies[i].getWidth(),enemies[i].getHeight());
							if(m.bulletCollision(enemies[i].getX(),enemies[i].getY(),enemies[i].getWidth(),enemies[i].getHeight())){
								if(!((Enemy)enemies[i]).boom()) points += 100;
							}
							((Enemy)enemies[i]).move();
						}
					}
				}	else if(b.isAlive()){
					b.draw(g);
					if(b.bulletCollision(m.getX(), m.getY(), m.getWidth(), m.getHeight()))
						m.boom(difficulty);
					if(m.bulletCollision(b.getX(), b.getY(), b.getWidth(), b.getHeight()))
						b.boom(difficulty);
					b.move();
				}	else if(ftime < 100){
					g.drawImage(outro, 0, 0, null);
					ftime++;
				}
				else{
					finished = true;
				}
			}
			if(!finished){
				p = new String("Points: "+points+" Time:"+timer+"       ").toCharArray();
				m.move(mouseX,mouseY);
				m.draw(g);
				g.setColor(Color.WHITE);
				g.drawChars(p, 0, 23, 10, 50);
			}
		}
		timer++;
	}
	public void resetToDefaults(){
		timer = 0;
		offsetX = 0;
		isSetDiff = false;
		for(int i=0;i<enemyNr;i++){
			((Enemy)enemies[i]).resetToDefaults();
		}
		mb.resetToDefaults();
	}
	public void updateMousePos(int x,int y){
		mouseX = x;
		mouseY = y;
	}
	public void mouseClickCommand(){
		m.shoot(m.getX()+50, m.getY()+10);
	}
	public int getPoints(){
		return points;
	}
	public boolean isFinished(){
		return finished;
	}
	public void setDifficulty(int diff){
		isSetDiff = true;
		difficulty = diff;
	}
	public boolean isSetDiff(){
		return isSetDiff;
	}
}
