package backend.ServiceImpl;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.*;

public class WordCountMap extends Mapper<Object, Text, Text, IntWritable> {

    private final static IntWritable intWritable = new IntWritable(1);
    private Text text = new Text();

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String line = new String(value.getBytes(), 0, value.getLength(), "UTF-8").trim();
        if (StringUtils.isNotEmpty(line)) {
            byte[] btValue = line.getBytes();
            InputStream inputStream = new ByteArrayInputStream(btValue);
            Reader reader = new InputStreamReader(inputStream);
            IKSegmenter ikSegmenter = new IKSegmenter(reader, true);
            Lexeme lexeme;
            while ((lexeme = ikSegmenter.next()) != null) {
                text.set(lexeme.getLexemeText());
                context.write(text, intWritable);
            }
        }
    }
}
