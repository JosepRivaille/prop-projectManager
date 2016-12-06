package edu.upc.fib.prop;

import edu.upc.fib.prop.view.controllers.impl.ViewGraphicControllerImpl;
import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

import java.net.URL;

import static edu.upc.fib.prop.utils.Java2JavascriptUtils.connectBackendObject;


public class App extends Application {

    private final String PAGE = "/view/index.html";

    @Override
    public void start(Stage stage) throws Exception {
        createWebView(stage, PAGE);
    }

    private void createWebView(Stage primaryStage, String page) {

        // create the JavaFX webview
        final WebView webView = new WebView();

        // connect the FruitsService instance as "fruitsService"
        // javascript variable
        connectBackendObject(
                webView.getEngine(),
                "backendService", new ViewGraphicControllerImpl());

        // show "alert" Javascript messages in stdout (useful to debug)
        webView.getEngine().setOnAlert(new EventHandler<WebEvent<String>>(){
            @Override
            public void handle(WebEvent<String> arg0) {
                System.err.println("ALERT::  " + arg0.getData());
            }
        });

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
