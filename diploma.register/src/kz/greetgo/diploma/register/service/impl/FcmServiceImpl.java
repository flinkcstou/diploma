package kz.greetgo.diploma.register.service.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import kz.greetgo.diploma.controller.model.NotificationEvent;
import kz.greetgo.diploma.register.service.FcmService;
import kz.greetgo.diploma.register.service.FcmTopic;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class FcmServiceImpl implements FcmService {

  final Logger logger = Logger.getLogger(getClass());

  public FcmServiceImpl(String path) {

    Path p = Paths.get(path);

    try (InputStream stream = Files.newInputStream(p)) {
      FirebaseOptions options = new FirebaseOptions.Builder()
        .setCredentials(GoogleCredentials.fromStream(stream)).build();

      FirebaseApp.initializeApp(options);

    } catch (IOException e) {
      logger.error("init fcm", e);
    }
  }


  @Override
  public void sendDirectNotification(String token, Map<String, String> data, NotificationEvent event) throws
    ExecutionException,
    InterruptedException {

    /*AndroidConfig androidConfig = AndroidConfig.builder()
      .setTtl(Duration.ofMinutes(2).toMillis())
      .setCollapseKey(event.cardNumber)
      .setPriority(AndroidConfig.Priority.HIGH)
      //.setNotification(AndroidNotification.builder().setTag(event.action).build())
      .build();
    */

    WebpushConfig webpushConfig = WebpushConfig.builder()
      .putHeader("ttl", "300")
      .setNotification(WebpushNotification.builder().setIcon("assets/img/food.png").build())
      .build();


    Message message = Message.builder()
      .putAllData(data)
      .setToken(token)
      .setWebpushConfig(webpushConfig)
      .setNotification(new Notification(event.title, event.body))
      .build();

    String response = FirebaseMessaging.getInstance().sendAsync(message).get();
    logger.info("Sent message: " + response);
  }


  @Override
  public void sendTopicBasedNotifications(FcmTopic topic, Map<String, String> data, NotificationEvent event) throws
    ExecutionException,
    InterruptedException {

    Message message = Message.builder()
      .putAllData(data)
      .setTopic(topic.name())
      .setNotification(
        new Notification(event.title, event.body))
      .build();

    String response = FirebaseMessaging.getInstance().sendAsync(message).get();
    logger.info("Sent message: " + response);
  }

  @Override
  public void subscribeToTopic(List<String> registrationTokens, String topic) throws FirebaseMessagingException {
    TopicManagementResponse response =
      FirebaseMessaging.getInstance().subscribeToTopic(registrationTokens, topic);

    logger.info("Sent subscribeToTopic: " + response.getErrors());
  }

  @Override
  public void unsubscribeToTopic(List<String> registrationTokens, String topic) throws FirebaseMessagingException {
    TopicManagementResponse response =
      FirebaseMessaging.getInstance().unsubscribeFromTopic(registrationTokens, topic);

    logger.info("Sent unsubscribeToTopic: " + response.getErrors());
  }

}
