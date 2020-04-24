package backend.Controller;

import backend.Service.WordCountService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ebook")
@CrossOrigin
public class WordCountController {
    @Autowired
    WordCountService wordCountService;

    @GetMapping("/WordCountController/wordCount")
    @ResponseBody
    public String wordCount(@RequestParam("jobName") String jobName) throws Exception {
        String str="";
        System.setProperty("HADOOP_USER_NAME", "deploy_man");
        if (StringUtils.isEmpty(jobName)) {
            return str="请输入作业名";
        }
        wordCountService.wordCount(jobName);
        return "统计完成，请前去hdfs相关路径下查看";
    }
}
