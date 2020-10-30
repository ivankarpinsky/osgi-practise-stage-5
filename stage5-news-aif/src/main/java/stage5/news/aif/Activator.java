package stage5.news.aif;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import stage5.news.service.News;

public class Activator implements BundleActivator {
    public void start(BundleContext context) throws Exception {
        context.registerService(News.class.getName(), new Aif(), null);
    }

    public void stop(BundleContext context) throws Exception {

    }
}
