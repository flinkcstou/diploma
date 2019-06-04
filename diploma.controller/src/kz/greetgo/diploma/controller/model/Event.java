package kz.greetgo.diploma.controller.model;

public class Event {
  public String personId;
  public String orderNo;
  public Integer orderStatus;


  public Event() {}

  public Event(String personId, String orderNo, Integer orderStatus) {
    this.personId = personId;
    this.orderNo = orderNo;
    this.orderStatus = orderStatus;
  }

  public NotificationEvent toNotificationEvent() {
    NotificationEvent e = new NotificationEvent();
    String status = orderStatus == 2 ? "'В процессе'" : "'Завершен'";

    e.title = "Заказ";
//    e.body = "Был изменен заказ с номером:" + this.orderNo + ", на статус: " + status;
    e.body = "Cтатус: " + status + "\nНомер заказа: " + this.orderNo;
    return e;
  }
}
