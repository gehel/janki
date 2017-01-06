package ch.ledcom.janki.model;

import ch.ledcom.janki.Deck;
import com.google.common.collect.ImmutableMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import static org.sqlite.SQLiteConfig.Encoding.UTF8;

@Configuration
@ComponentScan(basePackageClasses = Deck.class)
@EnableJpaRepositories(basePackageClasses = Deck.class)
public class JankiConfiguration {
    @Bean
    public DataSource dataSource() {
        SQLiteConfig config = new SQLiteConfig();
        config.setEncoding(UTF8);
        config.enforceForeignKeys(true);
        SQLiteDataSource dataSource = new SQLiteDataSource(config);
        dataSource.setUrl("jdbc:sqlite::memory:");
        return dataSource;
    }

    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, HibernateJpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactory.setPackagesToScan(Card.class.getPackage().getName());
        entityManagerFactory.setJpaPropertyMap(
                ImmutableMap.of(
                        "hibernate.dialect", "org.hibernate.dialect.SQLiteDialect",
                        "hibernate.hbm2ddl.auto", "create"
                )
        );
        return entityManagerFactory;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
