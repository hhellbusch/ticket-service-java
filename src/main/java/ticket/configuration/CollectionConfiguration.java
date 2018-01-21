package ticket.configuration;

import ticket.collection.CustomerCollection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CollectionConfiguration
{

	@Bean
	public CustomerCollection customerCollection() {
		return new CustomerCollection();
	}
}
