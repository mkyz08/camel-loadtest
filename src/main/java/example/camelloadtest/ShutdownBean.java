package example.camelloadtest;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShutdownBean {

    private final static Logger log = LoggerFactory.getLogger(ShutdownBean.class);

    public void process(Exchange exchange) throws Exception {
        final CamelContext camelContext = exchange.getContext();

        Thread shutdownThread = new Thread(() -> {
            Thread.currentThread().setName("ShutdownThread");
            try {
                camelContext.stop();
            } catch (Exception e) {
                log.error("Errore during shutdown", e);
            }
        });

        shutdownThread.start();
    }
}