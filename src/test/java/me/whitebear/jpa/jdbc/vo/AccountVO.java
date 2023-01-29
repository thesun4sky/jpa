package me.whitebear.jpa.jdbc.vo;

public class AccountVO {

    private Integer id;

    private String username;

    private String password;

  public AccountVO(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public AccountVO() {

  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }
}
