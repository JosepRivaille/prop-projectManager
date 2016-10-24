package edu.upc.fib.prop.persistence.dao.files;

import edu.upc.fib.prop.utils.FileUtils;

import java.util.Set;
import java.util.TreeSet;

public class DaoFiles {

    public Set<String> getExcludedWords(String lang) {
        Set<String> excludedWords = new TreeSet<>();
        FileUtils.readFile("src/main/resources/words/" + lang + ".txt");
        return excludedWords;
    }
}
