package com.susocode.inditextest.price.infrastructure.config.initialization;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Configuration
public class DatabaseInitializationConfig {

  @Bean
  public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
    var initializer = new ConnectionFactoryInitializer();
    initializer.setConnectionFactory(connectionFactory);

    var data = new ResourceDatabasePopulator(
        new ClassPathResource("schema.sql"),
        new ClassPathResource("data.sql")
    );

    initializer.setDatabasePopulator(data);
    return initializer;
  }
}
