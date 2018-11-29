package net.rickiekarp.api;

import java.io.InputStream;
import java.net.URL;

public interface HelloService {
  String hi(final String name);
  InputStream getStream(final String url);
  URL getUrl(final String url);
}
