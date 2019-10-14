package example.camelloadtest;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.PropertyInject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CreateDataSetProcessor implements Processor {

	Logger logger = LoggerFactory.getLogger(CreateDataSetProcessor.class);

    @PropertyInject("NUM_METER")
    private int numMeter;

	@Override
    public void process(Exchange exchange) throws Exception {
        Message message = exchange.getIn();

        logger.info("### CreateDataSetProcessor process");

        List<String> reads = new ArrayList<String>();
        for (int i = 0; i < numMeter; i++) {
        	String meterId = "TEST" + String.format("%06d", i);
        	reads.add(meterId);
        }

        message.setBody(reads);
        exchange.setIn(message);
    }

}