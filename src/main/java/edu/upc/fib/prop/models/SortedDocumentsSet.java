package edu.upc.fib.prop.models;

import javafx.util.Pair;

import java.util.*;

public class SortedDocumentsSet{

    //TODO implementar clase
    private boolean limited;
    private int limit;

    ArrayList< Pair<Document,Double> > docs;

    public SortedDocumentsSet(){
        limited = false;
        docs = new ArrayList<>();

    }

    public SortedDocumentsSet(int max){
        limited = false;
        limit = max;
        docs = new ArrayList<>();
    }

    public int size(){
        return docs.size();
    }

    public Document get(int index){
        return docs.get(index).getKey();
    }

    public void add(Document key, Double value) {
        if(limited && docs.size()>=limit){
            if(value>docs.get(docs.size()-1).getValue()){
                docs.remove(docs.size()-1);
                docs.add(new Pair(key, value));
            }
        }
        else {
            docs.add(new Pair(key, value));
        }
        docs.sort(new Comparator<Pair<Document, Double>>() {
            @Override
            public int compare(Pair<Document, Double> o1, Pair<Document, Double> o2) {
                if(o1.getValue()>o2.getValue()) return 1;
                else return 0;
            }
        });


    }

}
