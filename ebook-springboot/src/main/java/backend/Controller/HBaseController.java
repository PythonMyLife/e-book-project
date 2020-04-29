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

    @RequestMapping(value = "/scan")
    @ResponseBody
    public Map<String,Object> scan(String table) throws IOException {
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

    @RequestMapping(value = "/select")
    @ResponseBody
    public String select(String tablename, String rowKey) throws IOException {
        return hBaseUtils.selectRow(tablename, rowKey);
    }
}
