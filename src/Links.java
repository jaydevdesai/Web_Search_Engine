import java.io.*;
import java.util.HashMap;

public class Links extends HashMap<File, String> {
    BufferedReader bufferedReader;
    Links() throws IOException {
        bufferedReader = new BufferedReader (new FileReader("src\\urlPages.txt"));
        retrieveFile ();
    }

    void retrieveFile() throws IOException {
        String str;
        while ((str = bufferedReader.readLine ())!=null){
            String[] split = str.split (" ");
            File f = new File (split[0]);
            if(f.exists ())
                this.put (new File(split[0]),split[1]);
        }
    }
}
