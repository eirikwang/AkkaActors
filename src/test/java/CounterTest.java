import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class CounterTest {



    @Test
    public void runLoad() throws FileNotFoundException {
        long start = System.currentTimeMillis();
        BatchRunner runner = new BatchRunner();
        runner.process(new FileInputStream("testinput.txt"));
        System.out.println("Finished in: " + (System.currentTimeMillis() - start));
    }

    @Test
    public void createTestFile() throws IOException {

        FileOutputStream fos = new FileOutputStream("testinput.txt");
        Random r = new Random();
        for (int i = 0; i < 10000; i++) {
            IOUtils.write(String.format("ADD %s%n", r.nextInt(10)), fos);
        }
        fos.close();
    }

}
