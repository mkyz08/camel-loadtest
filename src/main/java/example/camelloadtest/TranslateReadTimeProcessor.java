package example.camelloadtest;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.PropertyInject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TranslateReadTimeProcessor implements Processor {

	Logger logger = LoggerFactory.getLogger(TranslateReadTimeProcessor.class);

	private AtomicInteger count = new AtomicInteger(0);

    @PropertyInject("DEFAULT_READ_TIME")
    private String defaultReadTime;

    @PropertyInject("MAX_REPEAT")
    private int maxRepeat;

    @Override
    public void process(Exchange exchange) throws Exception {
        Message message = exchange.getIn();

        logger.info("### TranslateReadTimeProcessor process");

        logger.info("defaultReadTime, {}", defaultReadTime);
        LocalDateTime time = CommonUtil.toLocalDateTime(defaultReadTime, "yyyy-MM-dd HH:mm:ss");
        time = time.plusMinutes(15L * count.getAndIncrement());
        logger.info("defaultReadTime, {}", time);
        message.setHeader("READ_TIME", CommonUtil.toStr(time, "yyyy-MM-dd HH:mm:ss"));

        exchange.setOut(message);
        exchange.setIn(message);
    }

    public int getCount() {
    	return count.intValue();
    }

    public boolean isMaxRepeat(Exchange exchange) {
    	logger.info("end repeat count: {}", getCount());
    	return getCount() == maxRepeat;
    }
}