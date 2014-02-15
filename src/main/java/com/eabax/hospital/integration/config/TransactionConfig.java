package com.eabax.hospital.integration.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@Configuration
@EnableTransactionManagement
public class TransactionConfig implements TransactionManagementConfigurer {
  
  @Autowired
  DataSource eabaxDataSource;

  @Bean
  public PlatformTransactionManager eabaxTxManager() {
    return new DataSourceTransactionManager(eabaxDataSource);
  }

  @Override
  public PlatformTransactionManager annotationDrivenTransactionManager() {
    return eabaxTxManager();
  }

}
