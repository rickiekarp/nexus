package net.rickiekarp.core;

import net.rickiekarp.api.HelloService;
import net.rickiekarp.impl.HelloServiceImpl;

public class HelloFactory {

  private static final HelloService SERVICE_INSTANCE = new HelloServiceImpl();

  public static HelloService createService() {
    return SERVICE_INSTANCE;
  }
}
