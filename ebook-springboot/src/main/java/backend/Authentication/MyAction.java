package backend.Authentication;

import java.security.PrivilegedAction;

public class MyAction implements PrivilegedAction {
    private String propertyName;

    public MyAction(String propertyName) {
        this.propertyName = propertyName;
    }

    public Object run() {
        return System.getProperty(propertyName);
    }
}
