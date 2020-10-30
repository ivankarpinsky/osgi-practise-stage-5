package stage5.news.aif;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import stage5.news.service.News;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Aif implements News {
    public final String url = "https://aif.ru/rss/news.php";
    public final String name = "RSS spb aif";

    public String[] getNews() throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements items = doc.getElementsByTag("item");
        ArrayList<String> allWords = new ArrayList<String>();

        for (Element item : items) {
            Elements newsHeadlines = item.getElementsByTag("turbo:content");
            String text = newsHeadlines.outerHtml();
            int start = text.indexOf("<h1>");
            int end = text.indexOf("</h1>");
            String title = text.substring(start + 4, end);
            String[] words = title.split(" ");
            allWords.addAll(Arrays.asList(words));
        }

        return allWords.toArray(new String[0]);
    }

    public String getSourceName() {
        return name;
    }
}
