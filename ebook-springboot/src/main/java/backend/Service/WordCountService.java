package backend.Service;

import java.io.IOException;

public interface WordCountService {
    public void wordCount(String jobName) throws InterruptedException, IOException, ClassNotFoundException;
}
