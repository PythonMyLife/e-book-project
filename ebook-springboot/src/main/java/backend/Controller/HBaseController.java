package backend.Controller;

import backend.util.HBaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ebook")
public class HBaseController {

    @Autowired
    private HBaseUtils hBaseUtils;

    @RequestMapping(value = "/test1")
    @ResponseBody
    public Map<String,Object> test1(String table) throws IOException {
        Map<String,Object> map = new HashMap<String,Object>();
        if(!hBaseUtils.existsTable(table)) {
            return map;
        }
        try {
            String str = hBaseUtils.scanAllRecord(table);//扫描表
            System.out.println("获取到hbase的内容："+str);
            map.put("hbaseContent",str);
            return map;
        } catch (IOException e) {
            return null;
        }
    }

    @RequestMapping(value = "/test2")
    @ResponseBody
    public Boolean test2() {
        try{
            String[] columnFamily = new String[]{"one"};
            hBaseUtils.createTable("oneTest", columnFamily);
            hBaseUtils.insertOneRecord("oneTest", "1", "one", "111","111");
            String str = hBaseUtils.scanAllRecord("oneTest");//扫描表
            System.out.println("获取到hbase的内容："+str);
            hBaseUtils.deleteTable("oneTest");
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}
