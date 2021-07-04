import java.io.File;
import java.io.IOException;
import java.util.*;

public class SearchEngine {

    static void showResult(ArrayList<String> links){
        int count = 1;
        Scanner scanner = new Scanner (System.in);
        if(links.size () == 0){
            System.out.println ("Oops! No results found.");
        }
        for (String link: links){
            System.out.println (link);
            if(count % 10 ==0){
                System.out.println ("\nMore Result Available. " +
                        "\nPress 1 to continue. " +
                        "Any other Key to Exit.");
                String ans = scanner.nextLine ();
                if(!ans.equals ("1"))
                    break;
            }
            count++;
        }
        System.out.println ("End of Results.");
    }
    public static void main (String[] args) throws IOException {
        InvertedIndex invertedIndex = new InvertedIndex ();
        File folder = new File("src/pages/");
        for (File file: Objects.requireNonNull (folder.listFiles ())) {
            if(file.isFile ())
                invertedIndex.createFileIndex (file);
        }
        final HashMap<String, List<File>> index = invertedIndex.getIndex ();
        Scanner scanner = new Scanner (System.in);
        String ans="0";
        Search search;
        do{
            search = new Search (index);
            System.out.print ("\n SEARCH ENGINE ");
            System.out.print ("\nEnter your Query: ");
            String query = scanner.nextLine ();
            showResult (search.performSearch (query));
            System.out.println ("\nPress 1 to continue your search. ");
            ans = scanner.next ();
            scanner.nextLine ();
        }while(ans.equals ("1"));
    }
}
