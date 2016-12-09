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
            node.generateTree(booleanExpression.toLowerCase());
            DocumentsSet matchingDocuments = new DocumentsSet();
            for (Document document : allDocuments.getDocuments()) {
                //TODO: Optimitzar
                Integer numFrases = document.getContent().split(Constants.SENTENCE_SEPARATION_REGEX).length;
                document.updatePositions();
                boolean documentMatches = checkDocumentMatchesExpression(document.getTermPositions(), node, numFrases);
                if (documentMatches) {
                    matchingDocuments.add(document);
                }
            }
            return matchingDocuments;
        }
    }

    private boolean checkDocumentMatchesExpression
            (Map<String, Map<Integer, Set<Integer>>> termPositions, Node node, Integer numFrases) {
        Set<Integer> totalFrases = new TreeSet<>();
        for (int i = 0; i < numFrases; ++i) {
            totalFrases.add(i);
        }
        Set<Integer> result = satisfy(termPositions, node, totalFrases);
        return !(result.isEmpty());
    }

    private Set<Integer> satisfy(Map<String, Map<Integer, Set<Integer>>> termPositions, Node node, Set<Integer> totalFrases){
        Operator operator = node.getOperator();
        NodeType nodeType = node.getNodeType();
        Set<Integer> bones = new TreeSet<>();
        ArrayList<Node> fills = node.getChildren();
        if ((Operator.AND).equals(operator)) {
            bones = satisfy(termPositions, fills.get(0),totalFrases);
            for (int i = 1; i < fills.size(); ++i) {
                if(bones.isEmpty()) break;
                bones.retainAll(satisfy(termPositions, fills.get(i),totalFrases));
            }
        } else if ((Operator.OR).equals(operator)) {
            for (Node fill : node.getChildren()) {
                bones.addAll(satisfy(termPositions, fill,totalFrases));
            }
        } else if ((NodeType.WORD).equals(nodeType)) {
            if(termPositions.containsKey(node.getWord())) {
                for (Integer sentence : termPositions.get(node.getWord()).keySet()) {
                    bones.add(sentence);
                }
            }
        } else if ((Operator.NOT).equals(operator)) {
            bones = totalFrases;
            bones.removeAll(satisfy(termPositions, node.getChildren().get(0),totalFrases));
        } else if ((NodeType.SENTENCE).equals(nodeType)) {
            String[] words = node.getWord().split(Constants.WORD_SEPARATION_REGEX);
            Boolean existsString;
            existsString = false;
            if (termPositions.containsKey(words[0])) {
                for (Map.Entry<Integer, Set<Integer>> sentence : termPositions.get(words[0]).entrySet()) {
                    for (Integer firstPosition : sentence.getValue()) {
                        existsString = true;
                        for (int i = 1; i < words.length; ++i) {
                            if (!termPositions.get(words[i]).get(sentence.getKey()).contains(i + firstPosition)) {
                                existsString = false;
                                break;
                            }
                        }
                        if (existsString) break;
                    }
                    if (existsString) bones.add(sentence.getKey());
                }
            }
        }
        return bones;
    }

}
