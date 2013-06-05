import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
        for (int i = 0; i < 1000000; i++) {
            String command = "";
            switch (r.nextInt(3)){
                case 0: command = "ADD"; break;
                case 1: command = "SUBTRACT"; break;
                case 2: command = "SQT"; break;
            }
            IOUtils.write(String.format("%s %s%n", command, r.nextInt(10)), fos);
        }
        fos.close();
    }

}
