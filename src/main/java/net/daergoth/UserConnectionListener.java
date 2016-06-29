package net.daergoth;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

public class UserConnectionListener implements HttpSessionAttributeListener {

    public UserConnectionListener() {
    
    }

    public void attributeAdded(HttpSessionBindingEvent event)  { 
    	if (event.getName().equals("profile")) {
    		TokenStorage.INSTANCE.createToken(event.getSession().getId());
    	}
    }

    public void attributeRemoved(HttpSessionBindingEvent event)  { 
    	if (event.getName().equals("profile")) {
    		TokenStorage.INSTANCE.removeToken(event.getSession().getId());
    	}
    }

    public void attributeReplaced(HttpSessionBindingEvent event)  { 
    	
    }
	
}
