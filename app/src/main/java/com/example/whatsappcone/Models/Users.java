package com.example.whatsappcone.Models;

public class Users {
  String profilePic, username, mailId, password, userId, lastMessage, status, about;

  public Users() {
  }

  public Users(String username, String mailId, String password) {
    this.username = username;
    this.mailId = mailId;
    this.password = password;
  }

  public Users(String profilePic, String username, String mailId, String password, String userId, String lastMessage, String status) {
    this.profilePic = profilePic;
    this.username = username;
    this.mailId = mailId;
    this.password = password;
    this.userId = userId;
    this.lastMessage = lastMessage;
    this.status = status;
  }

  public String getProfilePic() {
    return profilePic;
  }

  public void setProfilePic(String profilePic) {
    this.profilePic = profilePic;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getMailId() {
    return mailId;
  }

  public void setMailId(String mailId) {
    this.mailId = mailId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getLastMessage() {
    return lastMessage;
  }

  public void setLastMessage(String lastMessage) {
    this.lastMessage = lastMessage;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getAbout() {
    return about;
  }

  public void setAbout(String about) {
    this.about = about;
  }
}
