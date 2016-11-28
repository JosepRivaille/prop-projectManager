package edu.upc.fib.prop.business.search.impl;

import edu.upc.fib.prop.business.search.SearchBooleanExpression;
import edu.upc.fib.prop.exceptions.InvalidQueryException;
import edu.upc.fib.prop.models.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;


public class SearchBooleanExpressionImpl implements SearchBooleanExpression {


    /*public DocumentsSet SearchDocumentsByBooleanExp(String expressio, TOTS DOCUMENTS) {
        DecisionTree arbre = new DecisionTree;
        arbre.OmplenarArbre(expressio);
        DocumentsSet result = new DocumentsSet();
        for(Document doc :){
            String content = doc.getContent();
            for(String frase : content.split(Constants.WORD_SEPARATION_REGEX)) {
                if (arbre.ContrastaArbre(frase)) {
                    result.add(doc);
                    break;
                }
            }
        }
    }
}*/

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
            DocumentsSet matchingDocuments = new DocumentsSet();
            for (Document document : allDocuments.getDocuments()) {
                boolean documentMatches = checkDocumentMatchesQuery(document.getTermPositions(), booleanExpression);
                if (documentMatches) {
                    matchingDocuments.add(document);
                }
            }
            return matchingDocuments;
        }
    }

    private boolean checkDocumentMatchesQuery(Map<String, Map<Integer, Set<Integer>>> termPositions,
                                              String booleanExpression) {
        Set<Integer> possibleSentences = new HashSet<Integer>();
        checkWordExists(termPositions, "FAKE WORD");
        return 0 == 0;
    }

    private Set<Integer> getWordSentences(Map<String, Map<Integer,
            Set<Integer>>> termPositions, String word) {
        return termPositions.get(word).keySet();
    }

    private InDocumentPosition checkWordExists(Map<String, Map<Integer, Set<Integer>>> termPositions, String word) {
        return new InDocumentPosition(false, null, null);
    }

    private class InDocumentPosition {
        private boolean found;
        private Integer sentence;
        private Integer offset;

        InDocumentPosition(boolean found, Integer sentence, Integer offset) {
            this.found = found;
            this.sentence = sentence;
            this.offset = offset;
        }

        public boolean isFound() {
            return found;
        }

        public Integer getSentence() {
            return sentence;
        }

        public Integer getOffset() {
            return offset;
        }
    }

}
