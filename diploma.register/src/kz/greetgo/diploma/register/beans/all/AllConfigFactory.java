package kz.greetgo.diploma.register.beans.all;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.diploma.register.configs.DbConfig;
import kz.greetgo.diploma.register.configs.FcmConfig;
import kz.greetgo.diploma.register.util.LocalConfigFactory;

@Bean
public class AllConfigFactory extends LocalConfigFactory {

  @Bean
  public DbConfig createPostgresDbConfig() {
    return createConfig(DbConfig.class);
  }

  @Bean
  public FcmConfig createFcmConfig() {
    return createConfig(FcmConfig.class);
  }

}
