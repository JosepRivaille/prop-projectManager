package edu.upc.fib.prop;

import edu.upc.fib.prop.view.controllers.impl.ViewGraphicControllerImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import static edu.upc.fib.prop.utils.Java2JavascriptUtils.connectBackendObject;


public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        String PAGE = "/view/index.html";
        createWebView(stage, PAGE);
    }

    private void createWebView(Stage primaryStage, String page) {

        // create the JavaFX webView
        final WebView webView = new WebView();

        // connect the FruitsService instance as "fruitsService"
        // javascript variable
        connectBackendObject(
                webView.getEngine(),
                "backendService", new ViewGraphicControllerImpl());

        // show "alert" Javascript messages in stdout (useful to debug)
        webView.getEngine().setOnAlert(arg0 -> System.err.println("ALERT::  " + arg0.getData()));

        // load index.html
        webView.getEngine().load(
                getClass().getResource(page).
                        toExternalForm());

        primaryStage.setScene(new Scene(webView, 1080, 608));
        primaryStage.setTitle("PROP Library");
        primaryStage.show();
    }

    public static void main( String[] args ) {
        System.setProperty("prism.lcdtext", "false");
        launch(args);
    }
}
