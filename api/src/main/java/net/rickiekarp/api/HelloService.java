package net.rickiekarp.api;

import java.io.InputStream;

public interface HelloService {
  String hi(final String name);
  InputStream getStream(final String url);
}
