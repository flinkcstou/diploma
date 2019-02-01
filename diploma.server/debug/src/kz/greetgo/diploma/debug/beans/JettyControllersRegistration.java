package kz.greetgo.diploma.debug.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.JettyWarServlet;
import kz.greetgo.mvc.builder.ExecDefinition;
import kz.greetgo.mvc.interfaces.Views;
import kz.greetgo.diploma.controller.util.Controller;
import kz.greetgo.diploma.debug.util.WebAppContextRegistration;

import java.util.ArrayList;
import java.util.List;

@Bean
public class JettyControllersRegistration extends JettyWarServlet implements WebAppContextRegistration {

  public BeanGetter<List<Controller>> controllerList;

  @Override
  protected List<Object> getControllerList() {
    return new ArrayList<>(controllerList.get());
  }

  public BeanGetter<Views> views;

  @Override
  protected Views getViews() {
    return views.get();
  }

  @Override
  protected void afterRegistered() {
    System.err.println("[WebAppContext] --------------------------------------");
    System.err.println("[WebAppContext] -- USING CONTROLLERS:");
    for (ExecDefinition execDefinition : execDefinitionList()) {

      System.err.println("[WebAppContext] --   " + execDefinition.infoStr());
    }
    System.err.println("[WebAppContext] --------------------------------------");
    printRegistration();
  }

  @Override
  protected String mappingBase() {
    return "/api/*";
  }

  @Override
  protected String getTargetSubContext() {
    return "/api";
  }

  @Override
  public double priority() {
    return 0;
  }
}
