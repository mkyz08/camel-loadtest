package example.camelloadtest;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TranslateMeterIdProcessor implements Processor {

	Logger logger = LoggerFactory.getLogger(TranslateMeterIdProcessor.class);

	@Override
    public void process(Exchange exchange) throws Exception {
        Message message = exchange.getIn();

        logger.info("### TranslateMeterIdProcessor process");

        Long id = (Long)message.getBody();

        String meterId = "TEST" + String.format("%06d", id);
        logger.info("TranslateMeterIdProcessor meterId: {}", meterId);
        message.setHeader("METER_ID", meterId);

        exchange.setIn(message);
    }

}