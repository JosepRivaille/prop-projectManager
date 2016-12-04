package edu.upc.fib.prop.business.search.impl;

import edu.upc.fib.prop.business.search.SearchBooleanExpression;
import edu.upc.fib.prop.exceptions.InvalidQueryException;
import edu.upc.fib.prop.models.*;

import java.util.*;


public class SearchBooleanExpressionImpl implements SearchBooleanExpression {

    @Override
    public boolean checkValidBooleanExpression(String booleanExpression) {
        boolean isLiteral = false;
        Stack<Character> correctness = new Stack<>();
        for (char c : booleanExpression.toCharArray()) {
            if (!isLiteral && (c == '{' || c == '(' || c == '[')) {
                correctness.push(c);
            } else if (!isLiteral && (c == '}' || c == ')' || c == ']')) {
                if (correctness.firstElement() == c) {
                    correctness.pop();
                } else {
                    return false;
                }
            } else if (c == '"') {
                if (!isLiteral) {
                    correctness.push(c);
                    isLiteral = true;
                } else {
                    if (correctness.firstElement() == '"') {
                        correctness.pop();
                        isLiteral = false;
                    } else {
                        return false;
                    }
                }
            }
        }
        return correctness.isEmpty();
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
                boolean documentMatches = checkDocumentMatchesExpression(document.getTermPositions(), node);
                if (documentMatches) {
                    matchingDocuments.add(document);
                }
            }
            return matchingDocuments;
        }
    }

    //TODO: Apply boolean expression for each document.
    private boolean checkDocumentMatchesExpression(Map<String, Map<Integer, Set<Integer>>> termPositions, Node node) {
        return true;
    }

}
