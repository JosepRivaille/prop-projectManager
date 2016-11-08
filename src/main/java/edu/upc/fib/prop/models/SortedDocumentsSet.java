package edu.upc.fib.prop.models;

import java.util.*;

public class SortedDocumentsSet{

    private NavigableMap<Double, List<Document>> docs;

    private boolean limited;
    private int limit;
    private int cont;

    //TODO: Remove if unused
    public SortedDocumentsSet(){
        limited = false;
        docs = new TreeMap<>();
        cont = 0;
    }

    /**
     * permite definir un tamaño maximo
     * @param max tamaño maximo de la lista
     */
    public SortedDocumentsSet(int max){
        limited = true;
        limit = max;
        docs = new TreeMap<>();
        cont = 0;
    }

    /**
     * Devuelve el tamaño de la lista
     * @return tamaño de la lista
     */
    public int getSize(){
        return docs.size();
    }

    /**
     * Añade un elemento a la lista. Si está limitada y llena, se eliminará el documento con valor menor para hacer hueco.
     * @param doc
     * @param weight
     */
    public void add(Document doc, Double weight){
        if(limited && cont>=limit){
            putElement(doc, weight);
            removeLessWeighted();
        }
        else{
            putElement(doc, weight);
            cont++;
        }
    }

    /**
     * Devuelve el documento en la posicion indicada
     * @param index posicion a la que se accede
     * @return
     */
    public Document getDocument(int index){
        //TODO por implementar
        return null;
    }

    /**
     * Devuelve el valor que acompaña al documento en la posicion indicada
     * @param index posicion a la que se accede
     * @return
     */
    public Double getValue(int index){
        //TODO por implementar
        return null;
    }

    /**
     * Elimina el ultimo documento
     */
    private void removeLessWeighted() {
        Map.Entry<Double, List<Document>> lastEntry = docs.lastEntry();
        if(lastEntry.getValue().size()==1){
            docs.remove(lastEntry.getKey());
        }
        else {
            docs.get(lastEntry.getKey()).remove(lastEntry.getValue().size()-1);
        }
    }

    /**
     * Añade un elemento a la lista.
     * @param doc   documento a añadir
     * @param weight    peso del documento
     */
    private void putElement(Document doc, Double weight) {
        if(docs.containsKey(weight)){
            docs.get(weight).add(doc);
        }
        else{
            List<Document> l = new ArrayList<>();
            l.add(doc);
            docs.put(weight, l);
        }
        Comparator<Document> comp = (Document doc1, Document doc2) -> {
            if(doc1.getAuthor().toLowerCase().compareTo(doc2.getAuthor().toLowerCase()) > 0) return 1;
            else if(doc1.getAuthor().toLowerCase().equals(doc2.getAuthor().toLowerCase()) &&
                    doc1.getTitle().toLowerCase().compareTo(doc2.getTitle().toLowerCase()) > 0) return 1;
            else return 0;
        };

        Collections.sort(docs.get(weight), comp);
    }

























    /*
    //TODO implementar clase
    private boolean limited;
    private int limit;

    List< Pair<Document,Double> > docs;

    public SortedDocumentsSet(){
        limited = false;
        docs = new ArrayList<>();

    }

    public SortedDocumentsSet(int max){
        limited = true;
        limit = max;
        docs = new ArrayList<>();
    }

    public int size(){
        return docs.size();
    }

    public Document getDocument(int index){
        return docs.get(index).getKey();
    }

    public Double getValue(int index){
        return docs.get(index).getValue();
    }

    public void add(Document key, Double value) {
        if(limited && docs.size()>=limit){
            if(value>docs.get(docs.size()-1).getValue()){
                docs.remove(docs.size()-1);
                docs.add(new Pair<>(key, value));
            }
        }
        else {
            docs.add(new Pair<>(key, value));
        }
        Collections.sort(docs, new Comparator<Pair<Document, Double>>() {
            @Override
            public int compare(Pair<Document, Double> o1, Pair<Document, Double> o2) {
                if(o1.getValue()>o2.getValue()) return 0;
                else if(o1.getValue().equals(o2.getValue()) && o1.getKey().getAuthor().toLowerCase().compareTo(o2.getKey().getAuthor().toLowerCase())>0) return 0;
                else if(o1.getValue().equals(o2.getValue()) && o1.getKey().getAuthor().toLowerCase().equals(o2.getKey().getAuthor().toLowerCase()) && (o1.getKey().getTitle().toLowerCase().compareTo(o2.getKey().getTitle().toLowerCase())>0)) return 0;
                else return 1;
            }
        });

    }*/


}
