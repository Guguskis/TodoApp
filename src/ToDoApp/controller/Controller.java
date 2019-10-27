package ToDoApp.controller;

import ToDoApp.ViewManager;
import ToDoApp.model.AppManager;

public class Controller {
    protected final AppManager appManager;
    protected final ViewManager viewManager;

    public Controller() {
        this.viewManager = ViewManager.getInstance();
        this.appManager = viewManager.appManager;
    }

}
