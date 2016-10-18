package edu.upc.fib.prop.business.models;

import edu.upc.fib.prop.business.controllers.SearchEngine;

import java.util.ArrayList;
import java.util.List;

public class DocumentsSet {

    private List<Document> documents;

    public List<Document> getDocumentList(SearchEngine searchCriteria) {
        return new ArrayList<Document>();
    }

}
