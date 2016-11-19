package edu.upc.fib.prop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        String PAGE = "/view/index.html";
        createWebView(primaryStage, PAGE);
    }

    private void createWebView(Stage primaryStage, String page) {
        final WebView webView = new WebView();

        webView.getEngine().load(getClass().getResource(page).toExternalForm());

        primaryStage.setScene(new Scene(webView));
        primaryStage.setTitle("PROP - Project Manager");
        primaryStage.show();
    }

    public static void main( String[] args ) {
        launch(args);
        //new ViewControllerImpl();
    }

}