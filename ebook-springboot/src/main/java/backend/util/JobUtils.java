package backend.util;

import java.io.IOException;
import javax.annotation.PostConstruct;

import backend.ServiceImpl.WordCountMap;
import backend.ServiceImpl.WordCountReduce;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JobUtils {
    @Value("${hdfs.path}")
    private String path;
    @Value("${hdfs.ip}")
    private String ip;

    private static String hdfsPath;
    private static String host;


    public static Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        configuration.set("dfs.defaultFS", hdfsPath);
        return configuration;
    }

    public static void getWordCountJobsConf(String jobName, String outputPath)
            throws IOException, ClassNotFoundException, InterruptedException {
        /* 获取hdfs配置 */
        Configuration conf = getConfiguration();
        Job job = Job.getInstance(conf, jobName);
        job.setMapperClass(WordCountMap.class);
        job.setCombinerClass(WordCountReduce.class);
        job.setReducerClass(WordCountReduce.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        //job.addFileToClassPath(new Path("/"));

        //job.setJar("D:\\project\\BigData\\target\\BigData-1.0.jar");
        /* 小文件合并 */
        job.setInputFormatClass(CombineTextInputFormat.class);
        CombineTextInputFormat.setMaxInputSplitSize(job, 4 * 1024 * 1024);
        CombineTextInputFormat.setMinInputSplitSize(job, 2 * 1024 * 1024);
        //FileInputFormat.addInputPath(job, new Path(inputPath));
        FileInputFormat.setInputPaths(job, new Path("D:/school/3x/SE343/project/ebook-springboot/logs/ebook.log"));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));
        job.waitForCompletion(true);
    }

    @PostConstruct
    public void setHhdfsPath() {
        hdfsPath = this.path;
    }

    public static String getHdfsPath() {
        return hdfsPath;
    }
    @PostConstruct
    public void setHost() {
        host = this.ip;
    }

    public static String getHost() {
        return host;
    }
}
