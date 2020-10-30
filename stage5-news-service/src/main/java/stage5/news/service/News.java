package stage5.news.service;

import java.io.IOException;

public interface News {
    public String[] getNews() throws IOException;

    public String getSourceName();
}
