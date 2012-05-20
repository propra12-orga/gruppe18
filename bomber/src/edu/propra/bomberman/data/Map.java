package edu.propra.bomberman.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import edu.propra.bomberman.objects.Mauer;

public class Map {
	private ArrayList<GameObject> go;
	
	public Map() {
		go=new ArrayList<>();
		

		go.add(new Mauer(50, 50));
		go.add(new Mauer(150, 50));
		go.add(new Mauer(50, 150));
		go.add(new Mauer(150, 150));
	}
	
	public void addObject(GameObject obj){
		this.go.add(obj);
		Collections.sort(this.go);
	}
	public Iterator<GameObject> getObjects(){
		return go.iterator();
	} 
}
