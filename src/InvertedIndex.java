import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class InvertedIndex {

    HashMap<String, List<File>> index;
    List<File> files;
    StopWords stopWords;
    Stemmer stemmer;

    InvertedIndex(){
        index = new HashMap<String, List<File>> ();
        files = new ArrayList<File> ();
        stopWords = new StopWords ();
        stemmer = new Stemmer ();
    }

    void createFileIndex(File file) throws IOException {
        if(!files.contains (file)){
            files.add (file);
            Scanner scanner = new Scanner (file);
            while (scanner.hasNext ()){
                String word = stemmer.stem (scanner.next ()).toLowerCase ();
                if(!stopWords.contains (word)){
                    if(index.containsKey (word)){
                        if(!index.get (word).contains (file))
                            index.get (word).add (file);
                    } else {
                        List<File> fileList = new ArrayList<File> ();
                        fileList.add (file);
                        index.put (word, fileList);
                    }
                }
            }
        }
    }

    public HashMap<String, List<File>> getIndex () {
        return index;
    }
}
