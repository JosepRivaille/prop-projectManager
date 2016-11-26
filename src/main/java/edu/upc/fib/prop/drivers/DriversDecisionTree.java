package edu.upc.fib.prop.drivers;

import edu.upc.fib.prop.models.Node;
import edu.upc.fib.prop.utils.IOUtils;

import java.util.ArrayList;

/**
 * Created by Gabri on 25/11/2016.
 */

public class DriversDecisionTree {
    private static void escriu(Node node){

        ArrayList<Node> fills = node.getChildrens();
        if(node.getOperator() != 'p') System.out.print("fulla" + node.getParaula());
        else System.out.print("operand" + node.getOperator());
        for(Node fill: fills){
            escriu(fill);
        }
        System.out.print("//");
    }
    public static void main(String[] args) {
        Node arrel = new Node();
        String expressio = IOUtils.askForString("introdueix la expressio");
        arrel.generarArbre(expressio);
        escriu(arrel);
    }
}
