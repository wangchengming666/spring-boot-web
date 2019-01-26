package com.web.framework.common.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.web.framework.common.component.EnforcerComponent;
import com.web.framework.common.event.Event;

@Service
public class CreateUserListener implements Listener {

  @Autowired
  private EnforcerComponent enforcerComponent;

  @Override
  public void afterEventInvoked(Event event) {

    enforcerComponent.refresh();

  }


}
