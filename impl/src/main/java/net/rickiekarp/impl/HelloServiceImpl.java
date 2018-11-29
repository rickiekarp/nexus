package net.rickiekarp.impl;

import net.rickiekarp.api.HelloService;

import java.io.InputStream;
import java.net.URL;

import static java.lang.String.format;

public class HelloServiceImpl implements HelloService {

  @Override
  public String hi(final String name) {
    return format("hello, %s", name);
  }

  public InputStream getStream(String url) {
    return HelloServiceImpl.class.getResourceAsStream(url);
  }

  @Override
  public URL getUrl(String url) {
    return HelloServiceImpl.class.getResource(url);
  }
}
