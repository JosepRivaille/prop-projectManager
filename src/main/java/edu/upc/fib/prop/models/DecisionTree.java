package edu.upc.fib.prop.models;

import edu.upc.fib.prop.utils.IOUtils;

import java.util.ArrayList;

/*public class DecisionTree {
    private Node root;

    private ArrayList<Node> fulles = new ArrayList<Node>();

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node node) {
        this.root = node;
    }

    public DecisionTree() {
        root = new Node();
    }

    public boolean ContrastaArbre(String frase){
        return Boolean.FALSE;
    }*/

    public class Node {
        private Boolean bool;
        private char operator;
        private String paraula;
        private ArrayList<Node> childrens;

        public Node(){
            this.childrens = new ArrayList<>();
        }

        public Boolean getBool() {
            return bool;
        }

        public char getOperator() {
            return operator;
        }


        public void setBool(Boolean bool) {
            this.bool = bool;
        }

        public void setOperator(char operator) {
            this.operator = operator;
        }

        public void addChildren(Node node){
            this.childrens.add(node);
        }

        public void addFulla(String paraula){
            Node node = new Node();
            node.paraula = paraula;
            this.childrens.add(node);
        }

        public void clear(){
            this.childrens.clear();
        }

        public void generarArbre(String expressio) {
            int oberts, inici;
            Node fill = new Node();
            for(int i = 0; i < expressio.length(); ++i) {
                if (expressio.charAt(i) == '(') {
                    ++i;
                    inici = i;
                    oberts = 1;
                    while (oberts != 0) {
                        if (expressio.charAt(i) == '(') {
                            ++oberts;
                        } else if (expressio.charAt(i) == ')') {
                            --oberts;
                        }
                        ++i;
                    }
                    fill.generarArbre(expressio.substring(inici, i - 2));
                    this.addChildren(fill);
                    fill.clear();
                }
                if (expressio.charAt(i) == '{') {
                    ++i;
                    inici = i;
                    while (expressio.charAt(i) != '}') {
                        ++i;
                    }
                    String[] paraules = expressio.substring(inici, i - 1).split(" ");
                    for (String paraula : paraules) {
                        this.addFulla(paraula);
                    }
                }
                if(expressio.charAt(i) == '&' || expressio.charAt(i) == '|'){
                    this.setOperator(expressio.charAt(i));
                }
                if (expressio.charAt(i) == '"') {
                    ++i;
                    inici = i;
                    while (expressio.charAt(i) != '"') {
                        ++i;
                    }
                    this.addFulla(expressio.substring(inici, i - 1));
                }
                if (expressio.charAt(i) == '!'){
                    Node net = new Node();
                    ++i;
                    fill.operator = '!';
                    if(expressio.charAt(i) == '(') {
                        oberts = 1;
                        ++i;
                        inici = i;
                        while (oberts != 0) {
                            if (expressio.charAt(i) == '(') {
                                ++oberts;
                            } else if (expressio.charAt(i) == ')') {
                                --oberts;
                            }
                            ++i;
                        }
                    }
                    else if (expressio.charAt(i) == '{') {
                        ++i;
                        inici = i;
                        while (expressio.charAt(i) != '}') {
                            ++i;
                        }
                        String[] paraules = expressio.substring(inici, i - 1).split(" ");
                        for (String paraula : paraules) {
                            fill.addFulla(paraula);
                        }
                        this.addChildren(fill);
                    }
                    else if (expressio.charAt(i) == '"'){
                        ++i;
                        inici = i;
                        while (expressio.charAt(i) != '"') {
                            ++i;
                        }
                        this.addFulla(expressio.substring(inici, i - 1));

                    }
                    else {
                        ++i;
                        inici = i;
                        while (expressio.charAt(i) != ' ') {
                            ++i;
                        }
                        this.addFulla(expressio.substring(inici, i - 1));

                    }
                }
                if (expressio.charAt(i) > 'A' & expressio.charAt(i) < 'z') {
                    ++i;
                    inici = i;
                    while (expressio.charAt(i) != ' ') {
                        ++i;
                    }
                    this.addFulla(expressio.substring(inici, i - 1));
                }

            }
        }
        public static void main(String[] args) {
            Node arrel = new Node();
            String expressio = IOUtils.askForString("introdueix la expressio");
        }
    }