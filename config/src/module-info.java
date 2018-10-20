module config {
  opens net.rickiekarp.core;
  exports net.rickiekarp.core;

  requires javafx.controls;

  requires api;
  requires impl;
}
