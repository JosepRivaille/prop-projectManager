package edu.upc.fib.prop.models;

import java.util.ArrayList;
import java.util.Set;

public class Node {
    private char operator;
    private String paraula;
    private ArrayList<Node> childrens;

    public Node(){
        this.childrens = new ArrayList<>();
    }


    public char getOperator() {
        return operator;
    }

    public void setOperator(char operator) {
        this.operator = operator;
    }

    public void addChildren(Node node){
        this.childrens.add(node);
    }

    public String getParaula() {
        return paraula;
    }

    public void addFulla(String paraula){

        Node node = new Node();
        node.operator = 'p';
        node.paraula = paraula;
        this.childrens.add(node);
    }

    public void addFrase(String paraula){

        Node node = new Node();
        node.operator = 'f';
        node.paraula = paraula;
        this.childrens.add(node);
    }

    public ArrayList<Node> getChildrens() {
        return childrens;
    }

    public void clear(){
        this.childrens.clear();
    }

    public void generarArbre(String expressio) {
        Node fill;
        int oberts, inici;
        for(int i = 0; i < expressio.length(); ++i) {
            if (i < expressio.length() && expressio.charAt(i) == '(') {
                fill = new Node();
                ++i;
                inici = i;
                oberts = 1;
                while (i < expressio.length() && oberts != 0) {
                    if (expressio.charAt(i) == '(') {
                        ++oberts;
                    } else if (expressio.charAt(i) == ')') {
                        --oberts;
                    }
                    ++i;
                }
                fill.generarArbre(expressio.substring(inici, i - 1));
                this.addChildren(fill);
            }
            else if (i < expressio.length() && expressio.charAt(i) == '{') {
                ++i;
                inici = i;
                while (i < expressio.length() && expressio.charAt(i) != '}') {
                    ++i;
                }
                String[] paraules = expressio.substring(inici, i).split(" ");
                fill = new Node();
                fill.operator = '&';
                for (String paraula : paraules) {
                    fill.addFulla(paraula);
                }
                this.addChildren(fill);
            }
            else if(i < expressio.length() && (expressio.charAt(i) == '&' || expressio.charAt(i) == '|')){
                this.setOperator(expressio.charAt(i));
            }
            else if (i < expressio.length() && expressio.charAt(i) == '"') {
                ++i;
                inici = i;
                while (i < expressio.length() && expressio.charAt(i) != '"') {
                    ++i;
                }
                this.addFrase(expressio.substring(inici, i));
            }
            else if (i < expressio.length() && expressio.charAt(i) == '!'){
                fill = new Node();
                Node net = new Node();
                fill.operator = '!';
                ++i;
                inici = i;
                char tancament;
                switch (expressio.charAt(i)){
                    case '{' : tancament = '}'; break;
                    case '"' : tancament = '"'; break;
                    case '(' : tancament = ')'; break;
                    default :
                        tancament = ' ';
                        break;
                }
                ++i;
                while(i < expressio.length() && expressio.charAt(i) != tancament) {
                    ++i;
                }
                if(tancament == ' ' && i >= expressio.length()) --i;
                net.generarArbre(expressio.substring(inici,i+1));
                if(tancament == ' ' && i >= expressio.length()) ++i;
                fill.addChildren(net);
                this.addChildren(fill);
            }
            else if (i < expressio.length() && expressio.charAt(i) > 'A' & expressio.charAt(i) < 'z') {
                inici = i;
                ++i;
                while (i < expressio.length() && expressio.charAt(i) != ' ') {
                    ++i;
                }
                this.addFulla(expressio.substring(inici, i));
            }

        }
        if(this.childrens.size() == 1 && this.operator != '!') {
            this.operator  = this.childrens.get(0).operator;
            this.paraula = this.childrens.get(0).paraula;
            this.childrens = this.childrens.get(0).childrens;
        }
    }
}