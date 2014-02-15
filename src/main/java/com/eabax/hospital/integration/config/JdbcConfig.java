package com.eabax.hospital.integration.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class JdbcConfig {
  
  @Autowired
  DataSource eabaxDataSource;

  @Autowired
  DataSource inteDataSource;

  @Bean
  public JdbcTemplate eabaxJdbc() {
    return new JdbcTemplate(eabaxDataSource);
  }

  @Bean
  public JdbcTemplate inteJdbc() {
    return new JdbcTemplate(inteDataSource);
  }
}
