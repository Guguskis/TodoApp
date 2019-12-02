package main.java.todoapp.deleteThisPackage;


public class Main {
	public static void main(String[] args) {
		var house = new DefaultHouseBuilder()
				.buildBasement()
				.buildRoom("A gaming pool room")
				.buildFloor()
				.buildRoom("A gaming kitchen")
				.buildFloor()
				.buildRoom("A gaming bedroom")
				.buildRoof()
				.buildRoof()
				.buildRoof();
		house.printHouse();
	}
}
