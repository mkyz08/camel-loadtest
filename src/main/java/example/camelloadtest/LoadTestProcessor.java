package example.camelloadtest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.annotation.Resource;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.PropertyInject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariDataSource;

@Component
public class LoadTestProcessor implements Processor {

	Logger logger = LoggerFactory.getLogger(LoadTestProcessor.class);

	@Resource
	private CamelContext context;

	@PropertyInject("sql.selectSql")
    private String selectSql;

	@PropertyInject("sql.insertSql")
    private String insertSql;

	@PropertyInject("SQL_PATTERN")
    private String sqlPattern;

	@Resource
	private HikariDataSource ds;

	@Override
    public void process(Exchange exchange) throws Exception {
        Message message = exchange.getIn();

        //logger.info("### LoadTestProcessor process");
        //HikariDataSource ds = (HikariDataSource)context.getRegistry().lookupByName("dataSource");

		try (Connection con = ds.getConnection();
				PreparedStatement ps = con.prepareStatement(selectSql);
				PreparedStatement ps2 = con.prepareStatement(insertSql);) {

			String meterId = (String)message.getBody();
			String readTime = (String)message.getHeader("READ_TIME");
			LocalDateTime ldTime = CommonUtil.toLocalDateTime(readTime, "yyyy-MM-dd HH:mm:ss");
			Timestamp dateTime = CommonUtil.toTimestamp(ldTime);

			if (sqlPattern.contains("select")) {
				ps.setString(1, meterId);
				ps.setTimestamp(2, dateTime);

				try (ResultSet rs = ps.executeQuery();) {
					if (rs.next()) {
						if (logger.isDebugEnabled()) {
							logger.debug("meter_id: {}, read_time: {}, val: {}", rs.getString("meter_id"), rs.getTimestamp("read_time"), rs.getDouble("val"));
						}
					}
				}
			}

			if (sqlPattern.contains("insert")) {
				ps2.setString(1, meterId);
				ps2.setTimestamp(2, dateTime);
				ps2.setDouble(3, 11);
				int cnt = ps2.executeUpdate();
				con.commit();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
    }

}