package commands;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import stage5.news.service.News;

import java.util.Hashtable;

public class Activator implements BundleActivator {
    private BundleContext bundleContext = null;
    private ServiceReference<News> serviceReference = null;


    public void start(BundleContext context) throws Exception {
        Hashtable props = new Hashtable();
        props.put("osgi.command.scope", "news");
        props.put("osgi.command.function", new String[]{"stats", "check"});

        this.bundleContext = context;

        context.registerService(Commands.class.getName(), new Commands(context), props);
    }

    public void stop(BundleContext context) throws Exception {
    }
}
