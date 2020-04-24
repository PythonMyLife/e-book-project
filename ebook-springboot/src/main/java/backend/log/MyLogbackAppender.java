package backend.log;


import backend.util.HBaseUtils;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.status.ErrorStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class MyLogbackAppender extends AppenderBase<LoggingEvent> {
    private HBaseUtils hBaseUtils = new HBaseUtils();

    private static Integer logOrder = 0;
    private static Integer logNum = 0;

    @Override
    public void start() {
        String[] columnFamily = new String[]{"content"};
        try {
            hBaseUtils.createTable("ebook-log0", columnFamily);
            hBaseUtils.createTable("ebook-log1", columnFamily);
            hBaseUtils.createTable("ebook-log2", columnFamily);
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.start();
    }

    @Override
    public void stop() {
        //释放相关资源，如数据库连接，redis线程池等等
        if(!isStarted()) {
            return;
        }
        super.stop();
    }

    @Override
    protected void append(LoggingEvent event) {
        if (event == null || !isStarted()) {
            return;
        }
        // 此处自定义实现输出
        // 获取输出值：event.getFormattedMessage()
        // System.out.print(event.getFormattedMessage());
        // 格式化输出
        System.out.print(event.getFormattedMessage());
        String message = event.getFormattedMessage();
        if(logNum == 999) {
            logNum = 1;
            logOrder = (logOrder + 1) % 3;
        }
        String logTable = "ebook-log" + logOrder;
        try {
            hBaseUtils.insertOneRecord(logTable, logNum.toString(), "content", "log", message);
            logNum++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
