package ToDoApp;

import ToDoApp.model.AppManager;
import ToDoApp.model.DataStorageManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ViewManager extends Application {
	private static final int SCREEN_WIDTH = 800;
	private static final int SCREEN_HEIGHT = 600;
	private static final String DATA_FILE_NAME = "data.dat";
	private static final String FXML_Extension = ".fxml";
	private static ViewManager instance;
	public AppManager appManager;
	private Stage primaryStage;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static ViewManager getInstance() {
		return instance;
	}
	
	@Override
	public void init() {
		instance = this;
		
		DataStorageManager dataStorage = new DataStorageManager();
		this.appManager = dataStorage.importData(DATA_FILE_NAME);
	}
	
	@Override
	public void start(Stage primaryStage) throws FileNotFoundException {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("ToDo App");
		changeScene("TestComponent");
		
		primaryStage.show();
	}
	
	public void changeScene(String filePathRelativeToView) throws FileNotFoundException {
		
		var root = getComponent(filePathRelativeToView);
		var sceneComponent = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		primaryStage.setScene(sceneComponent);
	}
	
	public Parent getComponent(String relativeFilePath) throws FileNotFoundException {
		FXMLLoader loader = new FXMLLoader();
		
		String filePath = "view/" + relativeFilePath + FXML_Extension;
		
		return handleLoading(loader, filePath);
	}
	
	private Parent handleLoading(FXMLLoader loader, String filePath) throws FileNotFoundException {
		try {
			loader.setLocation(getClass().getResource(filePath));
			return loader.load();
		} catch (IOException e) {
			System.out.println("Component \"" + filePath + "\" path is wrong.");
			throw new FileNotFoundException();
		}
	}
	
	@Override
	public void stop() throws IOException {
		var dataStorage = new DataStorageManager();
		dataStorage.exportData("data.dat", appManager);
	}
}
