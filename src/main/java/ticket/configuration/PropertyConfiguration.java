package ticket.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Load the common properties
 */
@Configuration
@PropertySource({"classpath:common.properties"})
public class PropertyConfiguration {
}
