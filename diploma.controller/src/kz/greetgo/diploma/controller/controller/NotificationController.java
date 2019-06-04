package kz.greetgo.diploma.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.diploma.controller.register.NotificationRegister;
import kz.greetgo.diploma.controller.util.Controller;
import static kz.greetgo.diploma.controller.util.ParSessionNames.PERSON_ID;
import kz.greetgo.mvc.annotations.Par;
import kz.greetgo.mvc.annotations.ParSession;
import kz.greetgo.mvc.annotations.on_methods.ControllerPrefix;
import kz.greetgo.mvc.annotations.on_methods.OnGet;
import kz.greetgo.mvc.annotations.on_methods.OnPost;

@Bean
@ControllerPrefix("/notification")
public class NotificationController implements Controller {

  public BeanGetter<NotificationRegister> register;

  @OnPost("/register")
  public void register(@ParSession(PERSON_ID) String personId, @Par("registrationId") String registrationId) {
    register.get().register(personId, registrationId);
  }

  @OnGet("/unregister")
  public void unregister(@ParSession(PERSON_ID) String personId, @Par("registrationId") String registrationId) {
    register.get().unregister(registrationId);
  }

  @OnPost("/subscribe")
  public void subscribe(
    @ParSession(PERSON_ID) String personId,
    @Par("registrationId") String registrationId,
    @Par("topic") String topic) {

    register.get().subscribe(personId, registrationId, topic);
  }

  @OnGet("/unsubscribe")
  public void unsubscribe(
    @ParSession(PERSON_ID) String personId,
    @Par("registrationId") String registrationId,
    @Par("topic") String topic) {

    register.get().unsubscribe(personId, registrationId, topic);
  }

}
