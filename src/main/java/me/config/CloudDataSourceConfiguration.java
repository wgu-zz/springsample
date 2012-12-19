package me.config;

import java.util.Collection;

import javax.sql.DataSource;

import org.cloudfoundry.runtime.env.CloudEnvironment;
import org.cloudfoundry.runtime.env.RdbmsServiceInfo;
import org.cloudfoundry.runtime.service.relational.RdbmsServiceCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("cloud")
public class CloudDataSourceConfiguration implements IDataSourceConfiguration {

	private CloudEnvironment cloudEnvironment = new CloudEnvironment();

	@Bean
	public DataSource dataSource() {
		Collection<RdbmsServiceInfo> psqlServiceInfo = cloudEnvironment
				.getServiceInfos(RdbmsServiceInfo.class);
		RdbmsServiceCreator dataSourceCreator = new RdbmsServiceCreator();
		return dataSourceCreator.createService(psqlServiceInfo.iterator()
				.next());
	}

	@Bean
	public String string() {
		return "*** this is from cloud ***";
	}

}
