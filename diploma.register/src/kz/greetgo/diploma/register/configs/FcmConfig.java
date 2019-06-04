package kz.greetgo.diploma.register.configs;

import kz.greetgo.conf.hot.DefaultStrValue;
import kz.greetgo.conf.hot.Description;

@Description("Параметры доступа к FCM")
public interface FcmConfig {

  @Description("Папка для отправляемых писем (относительно ~/diploma.d/)")
  @DefaultStrValue("fcm_config/e-order-firebase.json")
  String filePath();
}
