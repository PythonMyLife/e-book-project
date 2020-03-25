package backend.ClassLoader;

import java.lang.reflect.Method;

public class ClassLoaderApplication {
    public static void main(String[] args) throws Exception{
        if(args.length != 2) {
            System.out.println("需要的输入为2个参数：加密文件，key");
            return;
        }
        try{
            String name = args[0];
            int key = Integer.parseInt(args[1]);
            ClassLoader loader = new CryptoClassLoader(key);
            Class c = loader.loadClass(name);
            String[] arguments = new String[] {};
            Method m = c.getMethod("main", args.getClass());
            m.invoke(null, (Object) arguments);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
