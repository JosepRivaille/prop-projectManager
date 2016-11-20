package edu.upc.fib.prop.utils;

import javafx.event.EventHandler;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import netscape.javascript.JSObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class J2JS {

    private static Map<WebEngine, Map<String, Object>> javaObjects = new HashMap<>();
    private static Set<WebEngine> webEnginesWithAlertChangeListener = new HashSet<>();

    private static boolean changing = false;

    /**
     * Registers a backend Java object as a Javascript variable.
     * Connect with "alert('__CONNECT__BACKEND__varName')" where varName is the javascript variable.
     * @param webEngine The webEngine to register the new variable.
     * @param varName The name of the variable in javascript.
     * @param backend The Java backend object.
     */
    public static void connectBackendObject(final WebEngine webEngine, final String varName, final Object backend) {
        registerBackendObject(webEngine, varName, backend);
        if (!webEnginesWithAlertChangeListener.contains(webEngine)) {
            if (webEngine.getOnAlert() == null) {
                webEngine.setOnAlert(new AlertEventHandlerWrapper(webEngine, null));
            }

            webEngine.onAlertProperty().addListener(
                    (arg0, previous, newHandler) -> {
                        if (!changing) {
                            changing = true;
                            webEngine.setOnAlert(new AlertEventHandlerWrapper(webEngine, newHandler));
                            changing = false;
                        }
                    });
        }
        webEnginesWithAlertChangeListener.add(webEngine);
    }

    private static void registerBackendObject(final WebEngine webEngine, final String varName, final Object backend) {
        Map<String, Object> webEngineBridges = javaObjects.get(webEngine);
        if (webEngineBridges == null){
            webEngineBridges = new HashMap<>();
            javaObjects.put(webEngine, webEngineBridges);
        }
        webEngineBridges.put(varName, backend);

    }

    private static void connectToWebEngine(WebEngine engine, String varName) {
        if (javaObjects.containsKey(engine) && javaObjects.get(engine).containsKey(varName)) {
            JSObject window = (JSObject) engine.executeScript("window");
            window.setMember(varName, javaObjects.get(engine).get(varName));
        }
    }

    public static void call(Object callback, Object argument) {
        ((JSObject)callback).eval("this("+argument.toString()+")");
    }

    private final static class AlertEventHandlerWrapper implements EventHandler<WebEvent<String>> {

        private final EventHandler<WebEvent<String>> wrappedHandler;
        private WebEngine engine;

        private AlertEventHandlerWrapper(WebEngine engine, EventHandler<WebEvent<String>> wrappedHandler) {
            this.engine = engine;
            this.wrappedHandler = wrappedHandler;
        }

        @Override
        public void handle(WebEvent<String> event) {
            if (event.getData().contains(Constants.J2JS_LINKER)) {
                String varName = event.getData().substring(Constants.J2JS_LINKER.length());
                connectToWebEngine(engine, varName);
            } else if (wrappedHandler != null) {
                wrappedHandler.handle(event);
            }
        }
    }
}