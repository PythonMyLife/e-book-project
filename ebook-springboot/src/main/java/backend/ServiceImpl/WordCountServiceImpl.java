package backend.ServiceImpl;

import backend.Service.WordCountService;
import backend.util.JobUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class WordCountServiceImpl implements WordCountService {
    SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
    String BATCH = df.format(new Date());
    @Override
    public void wordCount(String jobName) throws InterruptedException, IOException, ClassNotFoundException {
        if (StringUtils.isEmpty(jobName)) {
            return;
        }
        /* 存储路径 */
        String outputPath =  "D:/school/3x/SE343/project/ebook-springboot/output/" + jobName + "_" + BATCH;
        JobUtils.getWordCountJobsConf(jobName, outputPath);
    }
}
