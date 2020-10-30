package commands;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import stage5.news.service.News;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Commands {
    BundleContext bundleContext;
    private ServiceReference<News>[] serviceReferences = null;


    public Commands(BundleContext bundleContext) throws InvalidSyntaxException {
        this.bundleContext = bundleContext;
        this.serviceReferences = (ServiceReference<News>[]) bundleContext.getAllServiceReferences(News.class.getName(), null);
    }

    public void stats(int num) throws IOException {
        int number = num - 1;

        if (serviceReferences == null || serviceReferences.length == 0) {
            System.out.println("No sources availible");
            return;
        }

        if (number < 0 || number > serviceReferences.length) {
            System.out.println("selected source is not available");
            return;
        }

        News source;
        Map<String, Integer> allWords = new HashMap<String, Integer>();

        if (number == serviceReferences.length) {
            for (ServiceReference<News> serviceReference : serviceReferences) {
                source = (News) bundleContext.getService(serviceReference);
                if (source != null) {
                    String[] words = source.getNews();
                    for (String word : words) {
                        if (!allWords.containsKey(word)) {
                            allWords.put(word, 1);
                        } else {
                            allWords.put(word, allWords.get(word) + 1);
                        }
                    }
                }
            }
        } else {
            source = (News) bundleContext.getService(serviceReferences[number]);
            String[] words = source.getNews();
            for (String word : words) {
                if (!allWords.containsKey(word)) {
                    allWords.put(word, 1);
                } else {
                    allWords.put(word, allWords.get(word) + 1);
                }
            }
        }
        List<String> res = allWords.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        int i = 0;
        for (String item : res) {
            if (i == 10) break;
            System.out.println(item);
            i++;
        }

    }

    public void stats() {
        System.out.println("select the source number from the list:");
        News source;
        int i = 0;
        if (serviceReferences != null) {
            for (i = 0; i < serviceReferences.length; i++) {
                source = (News) bundleContext.getService(serviceReferences[i]);
                if (source != null) {
                    System.out.println(i + 1 + ": " + source.getSourceName());
                }
            }
        }
        System.out.println(i + 1 + ": all");
    }


    public void check() {
        System.out.println("ok");
    }

}
