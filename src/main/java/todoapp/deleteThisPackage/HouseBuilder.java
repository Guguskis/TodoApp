package main.java.todoapp.deleteThisPackage;

public interface HouseBuilder {
	HouseBuilder buildBasement();
	
	HouseBuilder buildRoom(String roomName);
	
	HouseBuilder buildFloor();
	
	HouseBuilder buildRoof();
	
	void printHouse();
}
