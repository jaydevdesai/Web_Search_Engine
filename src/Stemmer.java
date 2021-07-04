import org.tartarus.snowball.ext.EnglishStemmer;
import org.tartarus.snowball.ext.PorterStemmer;

public class Stemmer {
    EnglishStemmer stemmer;
    Stemmer(){
        stemmer = new EnglishStemmer ();
    }
    public String stem(String word){
        stemmer.setCurrent (word);
        stemmer.stem ();
        return stemmer.getCurrent ();
    }
}
