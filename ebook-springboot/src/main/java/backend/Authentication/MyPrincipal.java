package backend.Authentication;

import java.security.Principal;

public class MyPrincipal implements Principal {
    private String name;
    private String value;

    public MyPrincipal(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name + "=" + value;
    }

    public boolean equals(Object other) {
        if(this == other) return true;
        if(other == null) return false;
        if (getClass() != other.getClass()) return false;
        MyPrincipal o = (MyPrincipal) other;
        return getName().equals(o.getName());
    }
    public int hashCode() {
        return name.hashCode();
    }

}
