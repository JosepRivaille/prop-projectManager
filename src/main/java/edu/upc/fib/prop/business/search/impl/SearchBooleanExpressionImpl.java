package edu.upc.fib.prop.business.search.impl;

import edu.upc.fib.prop.business.search.SearchBooleanExpression;
import edu.upc.fib.prop.exceptions.InvalidQueryException;
import edu.upc.fib.prop.models.*;
import edu.upc.fib.prop.utils.Constants;

import java.util.*;


public class SearchBooleanExpressionImpl implements SearchBooleanExpression {

    @Override
    public boolean checkValidBooleanExpression(String booleanExpression) {
        Stack<Character> correctness = new Stack<>();
        boolean isLiteral = false;
        boolean expectsLogicalOperator = false;
        for (char c : booleanExpression.toCharArray()) {
            if (!isLiteral && (c == '{' || c == '(' || c == '[')) {
                correctness.push(c);
            } else if (!isLiteral && (c == '}' || c == ')' || c == ']')) {
                if (c == '}' && correctness.firstElement() == '{'
                        || c == ')' && correctness.firstElement() == '('
                        || c == ']' && correctness.firstElement() == '[') {
                    correctness.pop();
                } else {
                    return false;
                }
            } else if (c == '&' || c == '|') {
                if (expectsLogicalOperator) expectsLogicalOperator = false;
                else return false;
            } else if (c == '"') {
                isLiteral ^= true;
            } else {
                expectsLogicalOperator = true;
            }
        }
        return correctness.isEmpty() && expectsLogicalOperator && !isLiteral;
    }

    @Override
    public DocumentsSet searchDocumentsByBooleanExpression(String booleanExpression, DocumentsCollection allDocuments)
            throws InvalidQueryException {
        if (!checkValidBooleanExpression(booleanExpression)) {
            throw new InvalidQueryException();
        } else {
            Node node = new Node();
            node.generarArbre(booleanExpression);
            DocumentsSet matchingDocuments = new DocumentsSet();
            for (Document document : allDocuments.getDocuments()) {
                Integer numFrases = document.getContent().split(Constants.SENTENCE_SEPARATION_REGEX).length;
                document.updatePositions();
                boolean documentMatches = checkDocumentMatchesExpression(document.getTermPositions(), node,numFrases);
                if (documentMatches) {
                    matchingDocuments.add(document);
                }
            }
            return matchingDocuments;
        }
    }

    //TODO: Apply boolean expression for each document.
    private boolean checkDocumentMatchesExpression(Map<String, Map<Integer, Set<Integer>>> termPositions, Node node, Integer numFrases) {
        Set<Integer> totalFrases = new TreeSet<>();
        for(Integer i = 0; i < numFrases; ++i){
            totalFrases.add(i);
        }
        Set<Integer> result = compleixen(termPositions, node,totalFrases);
        return !(result.isEmpty());
    }
    private Set<Integer> compleixen(Map<String, Map<Integer, Set<Integer>>> termPositions, Node node, Set<Integer> totalFrases){
        char op = node.getOperator();
        Set<Integer> bones = new TreeSet<>();
        ArrayList<Node> fills = node.getChildrens();
        if(op == '&'){
            bones = compleixen(termPositions, fills.get(0),totalFrases);
            for(int i = 1; i < fills.size(); ++i){
                if(bones.isEmpty()) break;
                bones.retainAll(compleixen(termPositions, fills.get(i),totalFrases));
            }
        }
        else if(op == '|'){
            for(Node fill : node.getChildrens()){
                bones.addAll(compleixen(termPositions, fill,totalFrases));
            }
        }
        else if (op == 'p'){
            if(termPositions.containsKey(node.getParaula())) {
                for (Integer frase : termPositions.get(node.getParaula()).keySet()) {
                    bones.add(frase);
                }
            }
        }
        else if (op == '!'){
            bones = totalFrases;
            bones.removeAll(compleixen(termPositions, node.getChildrens().get(0),totalFrases));
        }
        else if (op == 'f'){
            String[] paraules = node.getParaula().split(" ");
            Boolean ExisteixCadenaParaules;
            ExisteixCadenaParaules = false;
            if(termPositions.containsKey(paraules[1])) {
                for (Map.Entry<Integer, Set<Integer>> frase : termPositions.get(paraules[1]).entrySet()) {
                    for (Integer posicioPrimera : frase.getValue()) {
                        ExisteixCadenaParaules = true;
                        for (int i = 1; i < paraules.length; ++i) {
                            if (!termPositions.get(paraules[i]).get(frase.getKey()).contains(i + posicioPrimera)) {
                                ExisteixCadenaParaules = false;
                                break;
                            }
                        }
                        if (ExisteixCadenaParaules) break;
                    }
                    if (ExisteixCadenaParaules) bones.add(frase.getKey());
                }
            }
        }
        return bones;
    }
}
