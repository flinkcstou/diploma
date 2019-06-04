package kz.greetgo.diploma.controller.model;

public class NotificationEvent {
  public String title;
  public String body;

  public NotificationEvent() {
  }

  @Override
  public String toString() {
    return "NotificationEvent{" +
      ", title='" + title + '\'' +
      ", body='" + body + '\'' +
      '}';
  }
}
