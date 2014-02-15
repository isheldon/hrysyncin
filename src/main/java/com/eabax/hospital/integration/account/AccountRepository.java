package com.eabax.hospital.integration.account;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Repository
@Transactional(readOnly = true)
public class AccountRepository {
	
  @Autowired
  JdbcTemplate inteJdbc;
	
	@Inject
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public Account save(Account account) {
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		inteJdbc.update("insert into account (email, password, role) values (?, ?, ?)",
		    new Object[] { account.getEmail(), account.getPassword(), account.getRole() });
		return account;
	}
	
  public Account findByEmail(String email) {
	  Account account = (Account) inteJdbc.queryForObject(
	      "select * from account where email = ?", 
	      new Object[] { email }, new AccountRm());
	  return account;
	}
	
}

class AccountRm implements RowMapper<Account> {
  @Override
  public Account mapRow(ResultSet rs, int num) throws SQLException {
    Account account = new Account();
    account.setId(rs.getLong("id"));
    account.setEmail(rs.getString("email"));
    account.setPassword(rs.getString("password"));
    account.setRole(rs.getString("role"));
    return account;
  }
}
