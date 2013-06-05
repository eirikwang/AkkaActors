import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BatchRunner {

    private ExecutorService executorService = Executors.newFixedThreadPool(2);
    private final Counter counter = new Counter();

    public void process(InputStream inputStream) {
        try (BufferedReader st = new BufferedReader(new InputStreamReader(inputStream))) {

            String line = st.readLine();
            while (line != null) {
                executorService.submit(new Worker(line));
                line = st.readLine();
            }
            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Feil skjedde", e);
        }
    }

    public final class Worker implements Runnable {
        private final String line;

        public Worker(String line) {
            this.line = line;
        }

        @Override
        public void run() {
            String[] split = line.split(" ");
            if(split[0].equals("ADD")){
                counter.increment(Long.parseLong(split[1]));
            } else if(split[0].equals("SUBTRACT")){
                counter.increment(Long.parseLong(split[1]));
            }else if(split[0].equals("SQT")){
                counter.sqrt();
            }
        }
    }
}
