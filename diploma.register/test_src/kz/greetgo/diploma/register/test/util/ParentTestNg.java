package kz.greetgo.diploma.register.test.util;

import kz.greetgo.depinject.testng.AbstractDepinjectTestNg;
import kz.greetgo.depinject.testng.ContainerConfig;

@ContainerConfig(BeanConfigTests.class)
public abstract class ParentTestNg extends AbstractDepinjectTestNg {}
