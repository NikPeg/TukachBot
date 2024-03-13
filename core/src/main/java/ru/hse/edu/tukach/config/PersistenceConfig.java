package ru.hse.edu.tukach.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.sql.DataSource;

@Configuration
@EnableJpaAuditing
public class PersistenceConfig {

    @Bean
    @ConfigurationProperties(prefix = "database.tukach.datasource")
    public DataSource tukachDataSource() {
        return new HikariDataSource();
    }
}
