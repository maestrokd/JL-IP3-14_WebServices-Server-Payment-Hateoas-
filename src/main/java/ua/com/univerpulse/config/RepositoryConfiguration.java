package ua.com.univerpulse.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Danny Briskin (sql.coach.kiev@gmail.com)
  */
@Configuration
@EnableJpaRepositories(basePackages = {"ua.com.univerpulse.model.repositories"})
@EntityScan(basePackages = {"ua.com.univerpulse.model.entities"})
@EnableTransactionManagement
public class RepositoryConfiguration {
}
