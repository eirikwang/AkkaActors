import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

public class BatchRunner {

    private ExecutorService executorService = Executors.newFixedThreadPool(2);
    private final Map<Integer,Counter> counter = new HashMap<>();

    public void process(InputStream inputStream) {
        for (int i = 0; i < 100; i++) {
            counter.put(i, new Counter());
        }
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
            int key = parseInt(split[1]);
            switch (split[0]) {
                case "ADD":
                    counter.get(key).increment(parseLong(split[2]));
                    break;
                case "SUBTRACT":
                    counter.get(key).increment(parseLong(split[2]));
                    break;
                case "SQT":
                    counter.get(key).sqrt();
                    break;
            }
        }
    }
}
