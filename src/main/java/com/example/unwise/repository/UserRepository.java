package com.example.unwise.repository;

import com.example.unwise.domain.User;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {

  private static final String SQL_CREATE_USER = """
      INSERT INTO users (username, password)
      VALUES (:username, :password)
      """;
  private static final String SQL_SELECT_USER_BY_USERNAME = """
      SELECT username, password
      FROM users
      WHERE username = :username
      """;

  private final NamedParameterJdbcTemplate jdbc;

  public void create(User user) {
    jdbc.update(SQL_CREATE_USER, Map.of(
        "username", user.getUsername(),
        "password", user.getPassword()
    ));
  }

  public Optional<User> findByUsername(String username) {
    try {
      User user = jdbc.queryForObject(SQL_SELECT_USER_BY_USERNAME, Map.of("username", username),
          (rs, rowNum) ->
              User.builder()
                  .username(rs.getString("username"))
                  .password(rs.getString("password"))
                  .build()
      );
      return Optional.ofNullable(user);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }
}
