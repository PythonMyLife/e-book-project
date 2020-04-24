package backend.ServiceImpl;

import backend.Service.VisitService;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class VisitServiceImpl implements VisitService {
    public synchronized Long getCount() {
        Long count = 0L;
        try {
            String visitFile = "D:\\school\\3x\\SE343\\project\\ebook-springboot\\src\\main\\java\\backend\\visit.txt";
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(visitFile), "UTF-8"));
            count = Long.valueOf(in.readLine());
            in.close();
            count++;
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(visitFile), "UTF-8"));
            out.write(String.valueOf(count));
            out.flush();
            out.close();
        } catch (Exception e) {
            count = -1L;
        }
        return count;
    }
}
