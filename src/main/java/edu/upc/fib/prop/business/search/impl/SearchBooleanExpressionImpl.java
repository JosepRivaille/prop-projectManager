package edu.upc.fib.prop.business.search.impl;

import edu.upc.fib.prop.business.search.SearchBooleanExpression;
import edu.upc.fib.prop.exceptions.InvalidQueryException;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import edu.upc.fib.prop.models.DocumentsSet;

import java.util.Map;
import java.util.Set;


public class SearchBooleanExpressionImpl implements SearchBooleanExpression {


    /*public DocumentsSet SearchDocumentsByBooleanExp(String expressio, TOTS ELS                       DOCUMENTS) {
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
    public void checkValidBooleanExpression(String booleanExpression) throws InvalidQueryException {
        //TODO: Implement validation.
    }

    @Override
    public DocumentsSet searchDocumentsByBooleanExpression(String booleanExpression,
                                                           DocumentsCollection documentsCollection) {
        DocumentsSet matchingDocuments = new DocumentsSet();
        for (Document document : documentsCollection.getDocuments()) {
            boolean documentMatches = checkDocumentMatchesExpression(document.getTermPositions(), booleanExpression);
            if (documentMatches) {
                matchingDocuments.add(document);
            }
        }
        return matchingDocuments;
    }

    private boolean checkDocumentMatchesExpression(Map<String, Map<Integer, Set<Integer>>> termPositions,
                                              String booleanExpression) {
        //TODO: Implement for each query element.
        checkWordExists(termPositions, "FAKE WORD");
        return 0 == 0;
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
