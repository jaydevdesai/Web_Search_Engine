import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

import static org.jsoup.Jsoup.*;

public class WebCrawler {
    HashSet<String> links = new HashSet<>();
    BufferedWriter bufferedWriter;

    public WebCrawler () throws IOException {
        bufferedWriter = new BufferedWriter (new FileWriter ("src\\urlPages.txt"));
    }

    void getPageLinks(String url){

        if(!links.contains (url)){
            try{
                Document document = connect (url).get ();
                Elements hrefLinks = document.select ("a[href^=\"https://ca.news.yahoo.com/\"]");
                for(Element link: hrefLinks){
                    links.add (url);
                    if(links.size ()<100)
                        getPageLinks (link.attr ("abs:href"));
                }
            } catch (IOException e) {
                e.printStackTrace ();
            }
        }
    }

    void downloadPages() throws IOException {
        links.forEach (x->{
            Document document;
            try{
                document = Jsoup.connect (x).maxBodySize (0).get ();
                String fileName = x;
                fileName = fileName.replace ("http://","");
                fileName = fileName.replace ("https://","");
                fileName = fileName.replace (".html",".txt");
                fileName = fileName.replace (".htm",".txt");
                if(fileName.endsWith ("/"))
                    fileName = fileName.replace ("/","");
                else
                    fileName = fileName.replace ("/","_");

                if(!fileName.contains (".pdf")) {
                    if(!fileName.endsWith (".txt"))
                        fileName+=".txt";
                    System.out.println (fileName);
                    FileWriter writer = new FileWriter ("src\\pages\\"+fileName);
                    writer.write (document.text ());
                    writer.close ();
                    File file = new File ("src\\pages\\"+fileName);
                    writeToFile (x,file.getAbsolutePath ());
                }
            } catch (IOException e) {

                e.printStackTrace ();
            }

        });
        bufferedWriter.flush ();
        bufferedWriter.close ();
    }

    void writeToFile(String url, String filePath) throws IOException {
        bufferedWriter.write (filePath+" "+url);
        bufferedWriter.newLine ();
    }

    public static void main (String[] args) throws IOException {
        WebCrawler webCrawler = new WebCrawler ();
        System.out.println ("Web Crawler");
        webCrawler.getPageLinks ("https://ca.news.yahoo.com");
        webCrawler.downloadPages ();
    }
}
