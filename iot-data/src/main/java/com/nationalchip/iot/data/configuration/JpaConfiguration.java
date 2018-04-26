package com.nationalchip.iot.data.configuration;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/26/18 8:35 PM
 * @Modified:
 */
@Configuration
@ComponentScan(basePackages = "com.nationalchip.iot.data")
@PropertySource("classpath:iot-data-default.properties")
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = { "com.nationalchip.iot.data" })
@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class JpaConfiguration extends JpaBaseConfiguration {


    @Autowired
    protected JpaConfiguration(DataSource dataSource, JpaProperties properties, ObjectProvider<JtaTransactionManager> jtaTransactionManager, ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
        super(dataSource, properties, jtaTransactionManager, transactionManagerCustomizers);
    }

    @Bean
    @Override
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }

    @Override
    protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
        return new JpaVendorAdapter(true);
    }

    @Override
    protected Map<String, Object> getVendorProperties() {
        final Map<String, Object> properties = new HashMap<>();
        // Turn off dynamic weaving to disable LTW lookup in static weaving mode
        properties.put(PersistenceUnitProperties.WEAVING, "false");

        // needed for reports
        properties.put(PersistenceUnitProperties.ALLOW_NATIVE_SQL_QUERIES, "true");

        // flyway
        //properties.put(PersistenceUnitProperties.DDL_GENERATION, "none");
        properties.put(PersistenceUnitProperties.DDL_GENERATION, "create-tables");

        /**
         * 取消以下注释可以关闭控制台输出sql语句
         */

//        // Embeed into logging
//        properties.put(PersistenceUnitProperties.LOGGING_LOGGER, "JavaLogger");

        // Ensure that we flush only at the end of the transaction
        properties.put(PersistenceUnitProperties.PERSISTENCE_CONTEXT_FLUSH_MODE, "COMMIT");

        // Enable batch writing
        properties.put(PersistenceUnitProperties.BATCH_WRITING, "JDBC");
        // Batch size
        properties.put(PersistenceUnitProperties.BATCH_WRITING_SIZE, "500");

        properties.put(PersistenceUnitProperties.LOGGING_LEVEL,"FINE");

        properties.put(PersistenceUnitProperties.LOGGING_PARAMETERS,"true");

        return properties;
    }
}
