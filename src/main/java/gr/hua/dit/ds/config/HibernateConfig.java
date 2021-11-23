package gr.hua.dit.ds.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource(value= {"classpath:application.properties"})
public class HibernateConfig {

    @Autowired
    private Environment environment;

    @Bean
    public LocalSessionFactoryBean getSessionFactory() throws PropertyVetoException {
        LocalSessionFactoryBean bean = new LocalSessionFactoryBean();

        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        hibernateProperties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));

        bean.setHibernateProperties(hibernateProperties);
        bean.setDataSource(getDataSource());
        bean.setPackagesToScan("gr.hua.dit.ds.entity");
        return bean;
    }

    @Bean
    public ComboPooledDataSource getDataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        dataSource.setDriverClass(environment.getRequiredProperty("jdbc.driver"));
        dataSource.setJdbcUrl(environment.getRequiredProperty("jdbc.url"));
        dataSource.setUser(environment.getRequiredProperty("jdbc.user"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
        dataSource.setAcquireIncrement(10);
        dataSource.setIdleConnectionTestPeriod(0);
        dataSource.setInitialPoolSize(5);
        dataSource.setMaxIdleTime(0);
        dataSource.setMaxPoolSize(50);
        dataSource.setMaxStatements(100);
        dataSource.setMinPoolSize(5);

        return dataSource;
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() throws PropertyVetoException {
        JdbcTemplate template = new JdbcTemplate();
        template.setDataSource(getDataSource());
        return template;
    }

    @Bean
    public HibernateTransactionManager getTransactionManager() throws PropertyVetoException {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(getSessionFactory().getObject());
        return transactionManager;
    }

}