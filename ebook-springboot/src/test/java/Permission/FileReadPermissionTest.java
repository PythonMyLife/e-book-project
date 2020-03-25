package Permission;

import backend.Permission.FileReadPermission;

public class FileReadPermissionTest {
    public static void main(String[] args) throws Exception {
        equalsTest();
        impliesTest();
        globalTest();
    }

    private static void equalsTest() {
        FileReadPermission fp = new FileReadPermission("C:/1.txt", "read");
        FileReadPermission fp1 = new FileReadPermission("C:/2.txt", "read");
        FileReadPermission fp2 = new FileReadPermission("C:/1.txt", "write");
        FileReadPermission fp3 = new FileReadPermission("C:/1.txt", "read");

        boolean bool = fp.equals(fp1);
        System.out.println(bool);
        bool = fp.equals(fp2);
        System.out.println(bool);
        bool = fp.equals(fp3);
        System.out.println(bool);
    }

    private static void impliesTest() {
        FileReadPermission fp = new FileReadPermission("C:/1/2/*", "read");
        FileReadPermission fp1 = new FileReadPermission("C:/1/2/3", "read");
        FileReadPermission fp2 = new FileReadPermission("C:/1/2/3/4", "read");
        FileReadPermission fp3 = new FileReadPermission("C:/1/2", "read");

        boolean bool = fp.implies(fp1);
        System.out.println(bool);
        bool = fp.implies(fp2);
        System.out.println(bool);
        bool = fp.implies(fp3);
        System.out.println(bool);
    }

    private static void globalTest() {
        FileReadPermission fp = new FileReadPermission("C:/1.txt", "read");
        SecurityManager manager = System.getSecurityManager();
        if(manager != null)
            manager.checkPermission(fp);
        System.out.println("test passed");
        fp = new FileReadPermission("D:/*", "read");
        if(manager != null)
            manager.checkPermission(fp);
    }
}
