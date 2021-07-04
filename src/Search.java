import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Search {
    HashMap<String, List<File>> index;
    HashMap<File, Integer> result;
    Stemmer stemmer;
    Links links;
    Search(HashMap<String, List<File>> index) throws IOException {
        stemmer = new Stemmer ();
        links = new Links ();
        result = new HashMap<File, Integer> ();
        this.index = index;
    }
    ArrayList<String> performSearch(String query){
        List<String> querySplit = removeStopWords (query.split (" "));
        for(String str: querySplit){
            String word = stemmer.stem (str);
            if(index.containsKey (word)){
                for(File f: index.get (word)){
                    int count = 1;
                    if(result.containsKey (f)){
                        count += result.get (f) ;
                    }
                    result.put (f,count);
                }
            }
        }
        return rankPages (querySplit.size ());
    }

    ArrayList<String> rankPages(int queryLength){
        result = result.entrySet ().stream ().sorted (Map.Entry.comparingByValue (Comparator.reverseOrder())).collect (Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        ArrayList<String> resultLinks = new ArrayList<> ();
        result.forEach ((k,v)->{
            //System.out.println (k+" : "+v);
            if(v==queryLength)
                resultLinks.add (links.get (new File (k.getAbsolutePath ())));
        });
        return resultLinks;
    }

    List<String> removeStopWords(String[] querySplit){
        StopWords stopWords = new StopWords ();
        List<String> temp = new ArrayList<> ();
        for(String s: querySplit){
            if(!stopWords.contains (s))
                temp.add (s);
        }
        return temp;
    }

}
