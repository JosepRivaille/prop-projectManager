package edu.upc.fib.prop.models;

import edu.upc.fib.prop.utils.Constants;

import java.util.ArrayList;

public class Node {

    private Operator operator;
    private NodeType nodeType;
    private String word;
    private ArrayList<Node> children;

    public Node(){
        this.children = new ArrayList<>();
    }

    private Node(NodeType nodeType, String word) {
        this.nodeType = nodeType;
        this.word = word;
        this.children = new ArrayList<>();
    }

    public Operator getOperator() {
        return operator;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public String getWord() {
        return word;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void generateTree(String expression) {
        Node child;
        int open, start;
        for (int i = 0; i < expression.length(); ++i) {
            if (expression.charAt(i) == '(') {
                child = new Node();
                ++i;
                start = i;
                open = 1;
                while (i < expression.length() && open != 0) {
                    if (expression.charAt(i) == '(') {
                        ++open;
                    } else if (expression.charAt(i) == ')') {
                        --open;
                    }
                    ++i;
                }
                child.generateTree(expression.substring(start, i - 1));
                this.children.add(child);
            } else if (expression.charAt(i) == '{') {
                start = ++i;
                while (i < expression.length() && expression.charAt(i) != '}') {
                    ++i;
                }
                String[] words = expression.substring(start, i).split(Constants.WORD_SEPARATION_REGEX);
                child = new Node();
                child.operator = Operator.AND;
                for (String word : words) { // Add words
                    child.children.add(new Node(NodeType.WORD, word));
                }
                this.children.add(child);
            } else if (expression.charAt(i) == '&' || expression.charAt(i) == '|') {
                this.operator = (expression.charAt(i) == '&') ? Operator.AND : Operator.OR;
            } else if (expression.charAt(i) == '"') {
                start = ++i;
                while (i < expression.length() && expression.charAt(i) != '"') {
                    ++i;
                }
                String word = expression.substring(start, i);
                this.children.add(new Node(NodeType.SENTENCE, word));
            } else if (expression.charAt(i) == '!'){
                child = new Node();
                Node grandchild = new Node();
                child.operator = Operator.NOT;
                start = ++i;
                char end;
                switch (expression.charAt(i)){
                    case '{' : end = '}'; break;
                    case '"' : end = '"'; break;
                    case '(' : end = ')'; break;
                    default : end = ' ';
                }
                ++i;
                while(i < expression.length() && expression.charAt(i) != end) {
                    ++i;
                }
                if (end == ' ' && i >= expression.length()) --i;
                grandchild.generateTree(expression.substring(start,i+1));
                if (end == ' ' && i >= expression.length()) ++i;
                child.children.add(grandchild);
                this.children.add(child);
            } else if (expression.charAt(i) != ' ') {
                start = i++;
                while (i < expression.length() && expression.charAt(i) != ' ') {
                    ++i;
                }
                String word = expression.substring(start, i);
                this.children.add(new Node(NodeType.WORD, word));
            }

        }

        if (this.children.size() == 1 && this.operator != Operator.NOT) {
            this.operator  = this.children.get(0).operator;
            this.nodeType = this.children.get(0).nodeType;
            this.word = this.children.get(0).word;
            this.children = this.children.get(0).children;
        }

    }

}