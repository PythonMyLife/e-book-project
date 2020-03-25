package backend.ClassLoader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class CypherFile {
    public static void main(String[] args) throws Exception {
        if(args.length != 3) {
            System.out.println("需要的输入为三个参数：写入文件，写出文件，key");
            return;
        }
        try {
            FileInputStream in = new FileInputStream(args[0]);
            FileOutputStream out = new FileOutputStream(args[1]);
            int key = Integer.parseInt(args[2]);
            int ch;
            while ((ch = in.read()) != -1) {
                ch = ch ^ key;
                out.write(ch);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
