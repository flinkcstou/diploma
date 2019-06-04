package kz.greetgo.diploma.register.service.impl;

import com.google.common.collect.Maps;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.diploma.controller.model.Event;
import kz.greetgo.diploma.controller.model.NotificationEvent;
import kz.greetgo.diploma.register.service.FcmService;
import kz.greetgo.diploma.register.test.util.ParentTestNg;
import kz.greetgo.util.RND;
import org.testng.annotations.Test;

import java.util.Map;

public class FcmServiceImplTest extends ParentTestNg {

  public BeanGetter<FcmService> service;

  @Test
  public void testSendDirectNotification() throws Exception {
    String token =
      "c-Y_Zktjawc" +
        ":APA91bFm69kxBn4hdQUcI4yIqKnk55W58C10_kzLugBSYMsYiV1WSsPyLupuMLRZblVJXVwcSj4Bi_H1KeLuyBgpSLoA27KYyqzu3Xv0txSqxykbjO5c2SkZRfvlkLe6oGwTxXIFyYJJ";

    for (int i = 0; i < 2; i++) {
      Event event = new Event();
      event.orderNo = RND.intStr(10);
      event.orderStatus = 2;

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