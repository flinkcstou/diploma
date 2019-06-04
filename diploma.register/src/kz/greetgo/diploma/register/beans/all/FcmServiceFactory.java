package kz.greetgo.diploma.register.beans.all;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.diploma.register.configs.FcmConfig;
import kz.greetgo.diploma.register.service.FcmService;
import kz.greetgo.diploma.register.service.impl.FcmServiceImpl;
import kz.greetgo.diploma.register.util.App;

@Bean
public class FcmServiceFactory {

  public BeanGetter<FcmConfig> config;

  @Bean
  public FcmService createFcmService() {
    return new FcmServiceImpl(App.appDir() + "/" + config.get().filePath());
  }

}
