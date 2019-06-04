package kz.greetgo.diploma.controller.register;


import kz.greetgo.diploma.controller.model.Event;

public interface NotificationRegister {

  void register(String personId, String registrationId);

  void unregister(String registrationId);

  void subscribe(String personId, String registrationId, String topic);

  void unsubscribe(String personId, String registrationId, String topic);

  void send(Event event);

}
