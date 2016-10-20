package edu.upc.fib.prop.business.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Guillermo on 18/10/2016.
 */
public class Document {
    private String title;
    private Author author;
    private String content;
    private Map<String, Float> words;
    private Map<String, Integer> wordsfreq;
    private Integer wordCount;
    private Integer maxWordFreq;

    public Document(String title, Author author, String content) {
        this.title = title;
        this.author = author;
        this.content = content;
        words = new HashMap<String, Float>();
        wordsfreq = new HashMap<String, Integer>();
        wordCount = 0;
        maxWordFreq = 0;
        loadWords();
        calculateWeights();
    }

    private void loadWords(){
        //Separamos el contenido del documento en palabras que añadimos al map words. En wordsfreq vamos guardando las
        //repeticiones de cada una
        String[] splitedContent=content.split("[ .,]");
        for(String w :splitedContent){
            if(w.length()>0) {
                ++this.wordCount;
                w = w.toUpperCase();
                Integer value;
                value = wordsfreq.get(w);
                if (value == null) {
                    DocumentsInfo.addWord(w); //Incrementamos en 1 el numero de documentos que contienen la palabra w
                    words.put(w, 0f);
                    wordsfreq.put(w, 1);
                } else {
                    wordsfreq.put(w, value + 1);
                    if(value + 1 > maxWordFreq) maxWordFreq = value + 1;
                }
            }
        }

    }


    private void calculateWeights(){
        for (Map.Entry<String, Float> entry : words.entrySet()) {
            words.put(entry.getKey(), tfidf(entry.getKey()));
        }
    }

    /**
     * Pre: La palabra w se encuentra en el documento
     * @param w
     * @return
     */
    private float tfidf(String w){
        try{
            return tf(w) * idf(w);
        }catch (NullPointerException e){
            e.printStackTrace();
            return 0f;
        }
    }

    /**
     * Pre: El String w se encuentra en el documento
     * @param w
     * @return
     */
    private float tf(String w) throws NullPointerException{
        Float freq = (float)wordsfreq.get(w)/wordCount;
        return 0.5f + 0.5f*(freq/maxWordFreq);
    }
    private float idf(String w) throws NullPointerException{
        return (float)(1 + Math.log(DocumentsInfo.getTotalDocuments()/(1+DocumentsInfo.getDocsWithWord(w))));
    }

    public void printWords(){
        for (Map.Entry<String, Float> entry : words.entrySet())
            System.out.println(entry.getKey());
    }
    public void printFreqs(){
        for (Map.Entry<String, Integer> entry : wordsfreq.entrySet())
            System.out.println(entry.getKey() + ":" + entry.getValue());
    }

}
