package edu.upc.fib.prop;

import edu.upc.fib.prop.view.controllers.impl.ViewGraphicControllerImpl;
import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

import java.net.URL;


public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("PROP Library");
        Scene scene = new Scene(new Browser(stage), 900, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main( String[] args ) {
        launch(args);
    }
}

class Browser extends Region {

    private final WebView browser = new WebView();
    private final WebEngine webEngine = browser.getEngine();

    Browser(Stage stage) {
        //apply the styles
        getStyleClass().add("browser");

        // load the web page
        URL url = getClass().getResource("/view/index.html");
        webEngine.load(url.toExternalForm());
        //Font.loadFont(getClass().getResource("www/fonts/ionicons.ttf").toExternalForm(),10);

        browser.setContextMenuEnabled(false);

        webEngine.getLoadWorker().stateProperty()
                .addListener((obs, oldValue, newValue) -> {
                    if (newValue == Worker.State.SUCCEEDED) {

                        JSObject jsobj = (JSObject) webEngine.executeScript("window");
                        jsobj.setMember("backend", new ViewGraphicControllerImpl(webEngine, stage));
                    }
                });

        webEngine.setOnAlert(arg0 -> System.out.println("alert: " + arg0.getData()));

        //add the web view to the scene
        getChildren().add(browser);
    }

    private Node createSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    @Override protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
    }

    @Override protected double computePrefWidth(double height) {
        return 1080;
    }

    @Override protected double computePrefHeight(double width) {
        return 768;
    }

}