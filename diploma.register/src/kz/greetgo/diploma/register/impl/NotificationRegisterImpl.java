package kz.greetgo.diploma.register.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.firebase.messaging.FirebaseMessagingException;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.diploma.controller.model.Event;
import kz.greetgo.diploma.controller.model.NotificationEvent;
import kz.greetgo.diploma.controller.register.NotificationRegister;
import kz.greetgo.diploma.register.dao.NotificationDao;
import kz.greetgo.diploma.register.service.FcmService;
import kz.greetgo.diploma.register.service.FcmTopic;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Bean
public class NotificationRegisterImpl implements NotificationRegister {

  final Logger logger = Logger.getLogger(getClass());

  private static SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm dd/mm/yyyy");

  public BeanGetter<FcmService> service;
  public BeanGetter<NotificationDao> notificationDao;

  @Override
  public void register(String personId, String registrationId) {
    notificationDao.get().registerDevice(personId, registrationId);
    logger.info("personId: " + personId + ", register.registrationId: " + registrationId);
  }

  @Override
  public void unregister(String registrationId) {
    notificationDao.get().unregisterDevice(registrationId);
    logger.info("unregister.registrationId: " + registrationId);
  }

  @Override
  public void subscribe(String personId, String registrationId, String topic) {
    try {
      List<String> list = getTokenList(personId, registrationId);

      if (list != null && !list.isEmpty()) service.get().subscribeToTopic(list, topic);
    } catch (FirebaseMessagingException e) {
      logger.error(e);
    }

  }

  @Override
  public void unsubscribe(String personId, String registrationId, String topic) {
    try {
      List<String> list = getTokenList(personId, registrationId);

      if (list != null && !list.isEmpty()) service.get().unsubscribeToTopic(list, topic);
    } catch (FirebaseMessagingException e) {
      logger.error(e);
    }
  }

  private List<String> getTokenList(String personId, String registrationId) {
    Set<String> tokens = notificationDao.get().getRegisterTokensByParentId(personId);
    if (!Strings.isNullOrEmpty(registrationId)) tokens.add(registrationId);

    return new ArrayList<>(tokens);
  }

  @Override
  public void send(Event event) {
    Map<String, String> data = Maps.newHashMap();
    data.put("id", nullToEmpty(event.id + ""));
    data.put("action", nullToEmpty(event.action));
    data.put("img", nullToEmpty(event.img));
    data.put("date", nullToEmpty(event.getNotificationTime()));


    sendTopicBasedNotifications(data, event.toNotificationEvent());

    notificationDao.get().getParentTokensByChild(event.childId)
      .stream()
      .forEach(token -> sendDirectNotification(token, data, event.toNotificationEvent()));
  }

  private void sendTopicBasedNotifications(Map<String, String> data, NotificationEvent event) {
    try {
      service.get().sendTopicBasedNotifications(FcmTopic.TRACKING, data, event);
    } catch (ExecutionException | InterruptedException | FirebaseMessagingException e) {
      logger.error(e);
    }
  }

  private void sendDirectNotification(String token, Map<String, String> data, NotificationEvent event) {
    try {
      service.get().sendDirectNotification(token, data, event);
    } catch (ExecutionException | InterruptedException e) {
      notificationDao.get().unregisterDevice(token);
      logger.error(e);
    }
  }

  private String nullToEmpty(String str) {
    return Strings.nullToEmpty(str);
  }
}
