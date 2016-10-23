package edu.upc.fib.prop.utils;

import java.util.ArrayList;
import java.util.List;

public class MenuTree {

    private String headerText;
    private List<String> options;
    private List<MenuTree> children;
    private Boolean functionalNode;

    public MenuTree(String headerText, List<String> options, Boolean functional) {
        this.options = options;
        this.children = new ArrayList<>();
        this.headerText = headerText;
        this.functionalNode = functional;
    }

    public String getHeaderText() {
        return headerText;
    }

    public List<String> getOptions() {
        return options;
    }

    public List<MenuTree> getChildren() {
        return children;
    }

    public Boolean isFunctionalNode() {
        return functionalNode;
    }

    public void addChild(MenuTree child) {
        this.children.add(child);
    }
}
