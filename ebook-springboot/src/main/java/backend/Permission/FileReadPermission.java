package backend.Permission;

import java.security.Permission;
import java.util.Arrays;
import java.util.Objects;

public class FileReadPermission extends Permission{
    private String action;

    public FileReadPermission(String target, String anAction) {
        super(target);
        action = anAction;
    }

    public String getActions() {
        return action;
    }

    @Override
    public boolean equals(Object other) {
        if(other == null) return false;
        if(!getClass().equals(other.getClass())) return false;
        FileReadPermission b = (FileReadPermission) other;
        if(!action.equals(b.action)) return false;
        return getName().equals(b.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode() + action.hashCode();
    }

    @Override
    public boolean implies(Permission other) {
        String[] locationList = getName().split("/");
        String[] otherList = other.getName().split("/");
        if(locationList.length != otherList.length) {
            return false;
        }
        if(Arrays.equals(locationList, otherList)) {
            return true;
        }
        if (locationList[locationList.length - 1].equals("-") || locationList[locationList.length - 1].equals("*")) {
            for(int i = 0; i < locationList.length - 1; i++) {
                if(!locationList[i].equals(otherList[i])) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
