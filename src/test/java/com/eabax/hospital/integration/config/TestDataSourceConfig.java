package com.eabax.hospital.integration.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * The data source config that can be used in integration tests.
 */
@Configuration
@Profile("test")
public class TestDataSourceConfig implements DataSourceConfig {

  @Value("${dataSource.driverClassName}")
  private String driver;
  @Value("${dataSource.url}")
  private String url;
  @Value("${dataSource.username}")
  private String username;
  @Value("${dataSource.password}")
  private String password;

  @Value("${dataSource2.driverClassName}")
  private String driver2;
  @Value("${dataSource2.url}")
  private String url2;
  @Value("${dataSource2.username}")
  private String username2;
  @Value("${dataSource2.password}")
  private String password2;

  @Bean
  @Override
  public DataSource eabaxDataSource() {
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName(driver2);
      dataSource.setUrl(url2);
      dataSource.setUsername(username2);
      dataSource.setPassword(password2);
      return dataSource;
  }

  @Bean
  @Override
  public DataSource inteDataSource() {
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName(driver);
      dataSource.setUrl(url);
      dataSource.setUsername(username);
      dataSource.setPassword(password);
      return dataSource;
  }
}
