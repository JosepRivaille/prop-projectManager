package edu.upc.fib.prop;

import edu.upc.fib.prop.view.controllers.impl.ViewControllerImpl;
import edu.upc.fib.prop.view.menu.MainMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import static edu.upc.fib.prop.utils.J2JS.connectBackendObject;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        String PAGE = "/view/index.html";
        createWebView(primaryStage, PAGE);
    }

    private void createWebView(Stage primaryStage, String page) {
        final WebView webView = new WebView();

        connectBackendObject(
                webView.getEngine(),
                "viewController", new ViewControllerImpl());

        webView.getEngine().setOnAlert(event -> System.err.println("alertwb1: " + event.getData()));

        webView.getEngine().load(getClass().getResource(page).toExternalForm());

        primaryStage.setScene(new Scene(webView));
        primaryStage.setTitle("Project Manager");
        primaryStage.show();
    }

    public static void main( String[] args ) {
        //launch(args);
        new MainMenu();
    }

}