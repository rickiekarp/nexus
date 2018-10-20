module config {
  opens net.rickiekarp.core;
  exports net.rickiekarp.core;
  exports net.rickiekarp.core.components.textfield;
  exports net.rickiekarp.core.debug;
  exports net.rickiekarp.core.controller;
  exports net.rickiekarp.core.view;
  exports net.rickiekarp.core.view.layout;
  exports net.rickiekarp.core.util.crypt;

  requires kotlin.stdlib;
  requires java.logging;


  requires javafx.controls;

  requires api;
  requires impl;
  requires okhttp;
  requires java.xml;
  requires json;
  requires java.desktop;
}
