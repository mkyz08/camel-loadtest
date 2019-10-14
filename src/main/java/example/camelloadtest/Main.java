package example.camelloadtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	static Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws Exception {
		logger.info("start ");
		try (ClassPathXmlApplicationContext applicationContext =
				  new ClassPathXmlApplicationContext("camel-context.xml")) {
			applicationContext.start();
			Thread.sleep(30000);
		}
	}
}
