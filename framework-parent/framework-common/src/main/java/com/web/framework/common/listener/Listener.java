package com.web.framework.common.listener;

import com.web.framework.common.event.Event;

public interface Listener extends java.util.EventListener {

  public void afterEventInvoked(Event event);

}
