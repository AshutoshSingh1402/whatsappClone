package com.example.whatsappcone.Models;

import java.util.Date;

public class Message {
  String message, senderId, receiverId, uniqueId;
  Date timeStamp;

  public Message() {}

  public Message(String senderId, String message, String receiverId, Date timeStamp) {
    this.message = message;
    this.senderId = senderId;
    this.timeStamp = timeStamp;
    this.receiverId = receiverId;
  }

  public String getSenderId() {
    return senderId;
  }

  public void setSenderId(String senderId) {
    this.senderId = senderId;
  }

  public String getReceiverId() {
    return receiverId;
  }

  public void setReceiverId(String receiverId) {
    this.receiverId = receiverId;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Date getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(Date timeStamp) {
    this.timeStamp = timeStamp;
  }

  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  public String getUniqueId() {
    return this.uniqueId;
  }
}
