package main.java.todoapp.deleteThisPackage;

import java.util.ArrayList;
import java.util.List;

public class DefaultHouseBuilder implements HouseBuilder {
	private List<String> structure = new ArrayList<String>();
	
	public DefaultHouseBuilder() {
	}
	
	@Override
	public HouseBuilder buildBasement() {
		structure.add("Basement");
		return this;
	}
	
	@Override
	public HouseBuilder buildRoom(String roomName) {
		structure.add(roomName);
		return this;
	}
	
	@Override
	public HouseBuilder buildFloor() {
		structure.add("Floor");
		return this;
	}
	
	@Override
	public HouseBuilder buildRoof() {
		structure.add("Roof");
		return this;
	}
	
	@Override
	public void printHouse() {
		for (int i = structure.size() - 1; i >= 0; i--) {
			System.out.println(structure.get(i));
		}
	}
	
	
}
