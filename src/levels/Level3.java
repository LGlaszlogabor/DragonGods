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

public class Level3 {
	int timer,ftime;
	Character[] enemies;
	int enemyNr;
	BufferedImage[] layer1;
	BufferedImage layer2,intro,outro;
	int ligcount;
	boolean isLightning;
	int points;
	char[] p;
	int mouseX, mouseY;
	boolean finished;
	MainCharacter m;
	MiniBoss[] mb;
	Boss b;	
	int difficulty;
	boolean isSetDiff;
	public Level3(MainCharacter mm,int p,int diff){
		difficulty = diff;
		isSetDiff = true;
		finished = false;
		points = p;
		timer = 0;
		ftime = 0;
		m = mm;
		ligcount = 0;
		isLightning = false;
		layer1 = new BufferedImage[7];
		try {
			for(int i=0;i<7;i++){
				layer1[i] = ImageIO.read(new File("src\\game\\level3\\layer1"+i+".png"));
			}
			layer2 = ImageIO.read(new File("src\\game\\level3\\layer2.png"));
			intro = ImageIO.read(new File("src\\game\\level2\\intro.png"));
			outro = ImageIO.read(new File("src\\game\\level2\\outro.png"));
		} catch (IOException e) {
			System.out.println("Nem lehet betolteni valamelyik layert");
		}
		enemyNr = 23;
		enemies = new Character[enemyNr];
		Random rand = new Random();		
		for(int i=0;i<enemyNr;i++){
			int k = rand.nextInt(4);
			if(k==0)enemies[i] = new Enemy("eagle",difficulty+1,8,800+rand.nextInt(600),rand.nextInt(550));
			else if(k==1) enemies[i] = new Enemy("redbird",difficulty+1,8,800+rand.nextInt(600),rand.nextInt(550));
			else if(k==2) enemies[i] = new Enemy("bat",difficulty+1,9,800+rand.nextInt(600),rand.nextInt(550));
			else if(k==3) enemies[i] = new Enemy("duck",difficulty+1,4,800+rand.nextInt(600),rand.nextInt(550));
		}
		mb = new MiniBoss[3];
		mb[0] = new MiniBoss("bird",5*difficulty+3,8,800+rand.nextInt(600),rand.nextInt(250));
		mb[1] = new MiniBoss("bird",5*difficulty+3,8,800+rand.nextInt(600),rand.nextInt(250));
		mb[2] = new MiniBoss("bird",5*difficulty+3,8,800+rand.nextInt(600),rand.nextInt(250));
		b = new Boss("bosslvl3",300,6,6,800+rand.nextInt(200),rand.nextInt(250),3);
	}
	public void draw(Graphics g){
		if(timer < 70){
			g.drawImage(intro, 0, 0, null); 
		}else{
			Random rand = new Random();
			g.drawImage(layer1[ligcount], 0, 0, null); 
			g.drawImage(layer2, 0, 0, null);
			if(!isLightning){
				int k= rand.nextInt(100);
				if(k < 20){
					isLightning = true;
				}
			}
			else{
				if(ligcount < 6){
					ligcount++;
				}
				else{
					ligcount = 0;
					isLightning = false;
				}
			}
			if(timer<170){
				for(int i=0;i<enemyNr;i++){
					if(!((Enemy)enemies[i]).isActive()) ((Enemy)enemies[i]).activate(difficulty+1);
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
				else if(timer<670 && (mb[0].isAlive()||mb[1].isAlive())){
					for(int i=0;i<3;i++){
						mb[i].draw(g);
						if(mb[i].isActive()){
							m.isHit(mb[i].getX(), mb[i].getY(), mb[i].getWidth(), mb[i].getHeight());
							if(m.bulletCollision(mb[i].getX(),mb[i].getY(),mb[i].getWidth(),mb[i].getHeight())){
								if(!mb[i].boom()) points += 1000;
							}
							mb[i].move();
						}
					}
				}
				else if(timer<870){
					for(int i=0;i<enemyNr;i++){
						if(!((Enemy)enemies[i]).isActive()) ((Enemy)enemies[i]).activate(difficulty+1);
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
					if(b.superbulletCollision(m.getX(), m.getY(), m.getWidth(), m.getHeight()))
						m.boom(3*difficulty);
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
		isLightning = false;
		ligcount = 0;
		isSetDiff = false;
		for(int i=0;i<enemyNr;i++){
			((Enemy)enemies[i]).resetToDefaults();
		}
		mb[0].resetToDefaults();
		mb[1].resetToDefaults();
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
