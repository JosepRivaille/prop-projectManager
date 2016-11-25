package edu.upc.fib.prop.business.search.impl;
import edu.upc.fib.prop.business.search.BooleanExp;
import edu.upc.fib.prop.models.DecisionTree;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsSet;
import edu.upc.fib.prop.utils.Constants;


public class BooleanExpImpl implements BooleanExp {
    @Override


    public DocumentsSet SearchDocumentsByBooleanExp(String expressio, TOTS ELS                       DOCUMENTS) {
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
    }
}
