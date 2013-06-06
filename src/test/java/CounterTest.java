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
        for (int i = 0; i < 100; i++) {
            String command = "";
            if(i%15 == 0){
                IOUtils.write(String.format("%s %s %s%n", "TIMES", r.nextInt(100), "feil"), fos);
            }
            if(i%10 == 0){
                IOUtils.write(String.format("%s %s %s%n", "TIMES", r.nextInt(100), r.nextInt(10)), fos);
            }
            switch (r.nextInt(3)){
                case 0: command = "ADD"; break;
                case 1: command = "SUBTRACT"; break;
                case 2: command = "SQT"; break;
            }
            IOUtils.write(String.format("%s %s %s%n", command, r.nextInt(100), r.nextInt(10)), fos);
        }
        fos.close();
    }

}
