package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import levels.Level2;
import levels.Level3;
import levels.level1;
import core.Enemy;
import core.Character;
import core.MainCharacter;

public class Game {
	MainCharacter m;
	int difficulty;
	int points;
	int mouseX, mouseY;
	level1 L1;
	Level2 L2;
	Level3 L3;
	int actlvl;
	Game(){
		points = 0;
		actlvl = 1;
		difficulty = 1;
		m = new MainCharacter("goku", 200, 4, 6, 100, 100);
		L1 = null;
		L2 = null;
		L3 = null;
	}
	public void setDifficulty(int diff){
		difficulty=diff;
	}
	public void draw(Graphics g){
		if(L1 == null) L1 = new level1(m,points,difficulty);
		if(!L1.isFinished()){
			if(!L1.isSetDiff()) L1.setDifficulty(difficulty);
			L1.draw(g);
		}else{
			if(actlvl == 1){
				actlvl++;
				points = L1.getPoints();
			}
			if(L2 == null) L2 = new Level2(m,points,difficulty);
			if(!L2.isFinished()){
				if(!L2.isSetDiff()) L2.setDifficulty(difficulty);
				L2.draw(g);
			}
			else{
				if(actlvl == 2){
					actlvl++;
					points = L2.getPoints();
				}
				if(L3 == null) L3 = new Level3(m,points,difficulty);
				if(!L3.isFinished()){
					if(!L3.isSetDiff()) L3.setDifficulty(difficulty);
					L3.draw(g);
				}
			}
		}
	}
	public void mouseClickCommand(){
		if(L1 != null && actlvl == 1) L1.mouseClickCommand();
		if(L2 != null && actlvl == 2) L2.mouseClickCommand();
		if(L3 != null && actlvl == 3) L3.mouseClickCommand();
	}
	public void updateMousePos(int x,int y){
		if(L1 != null && actlvl == 1) L1.updateMousePos(x, y);
		if(L2 != null && actlvl == 2) L2.updateMousePos(x, y);
		if(L3 != null && actlvl == 3) L3.updateMousePos(x, y);
	}
	public void resetToDefaults(){
		points = 0;
		actlvl = 1;
		m.resetToDefaults();
		L1.resetToDefaults();
	}
}
