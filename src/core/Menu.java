package core;

import java.util.ArrayList;

public class Menu {
	ArrayList<ArrayList<String>> l;
	public Menu(){
		l = new ArrayList<ArrayList<String>>();
	}
	public void add(String id,int nr){
		if(l.isEmpty()){
			l.add(new ArrayList<String>());
			l.get(0).add(id);
		}
		else{
			if(l.size()>nr){
				l.get(nr).add(id);
			}
			else{
				l.add(new ArrayList<String>());
				l.get(nr).add(id);
			}
		}
	}
	public String get(int fo, int nr){
		return l.get(fo).get(nr);
	}
	public int getMainSize(){
		return l.size();
	}
	public int getSize(int lvl){
		return l.get(lvl).size();
	}
}