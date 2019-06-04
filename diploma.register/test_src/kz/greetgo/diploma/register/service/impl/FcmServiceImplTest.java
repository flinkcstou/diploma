package kz.greetgo.diploma.register.service.impl;

import com.google.common.collect.Maps;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.diploma.controller.model.Event;
import kz.greetgo.diploma.controller.model.NotificationEvent;
import kz.greetgo.diploma.register.service.FcmService;
import kz.greetgo.diploma.register.test.util.ParentTestNg;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.Map;

public class FcmServiceImplTest extends ParentTestNg {

  public BeanGetter<FcmService> service;

  @Test
  public void testSendDirectNotification() throws Exception {
    String token =
      "fqzIg6V_cms:APA91bGTLmNOGlyprD0mxwYrtdupqV6SyT2ICEFMc1D2goZlR7_ZYNyPtTZC-SI_2LMj2zy0H300" +
        "--mr1kr2luCx6rRzW3iL61E0Hf66xOnz7rYdBczQWlUZ-wugdt5IPIHh0CASQMRJ";

    for (int i = 0; i < 10; i++) {

      Event event = new Event();
      event.fio = "Vasya Pupkin";
      event.cardNumber = "1000000";
      event.action = (i % 2 == 0) ? "in" : "out";
      event.entrance = "public gate";
      event.date = new Date();

      Map<String, String> data = Maps.newHashMap();
      data.put("id", String.valueOf((int) (Math.random() * 1000)));
      data.put("text", "AAA: " + String.valueOf((int) (Math.random() * 1000)));
/*
      data.put("tag", (i%2 == 0) ? "in": "out");
      data.put("foreground", "true");

      System.out.println("tag: "+ "tag_"+(i%2));*/
      NotificationEvent notificationEvent = event.toNotificationEvent();
      service.get().sendDirectNotification(token, data, notificationEvent);
    }
  }
}