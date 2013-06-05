import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Counter {
    private long count;
    private final Lock lock = new ReentrantLock();

    public void increment(long amount) {
        lock.lock();
        try {
            count += amount;
        } finally {
            lock.unlock();
        }

    }
    public void decrement(long amount) {
        lock.lock();
        try {
            count += amount;
        } finally {
            lock.unlock();
        }
    }
    public void sqrt(){
        lock.lock();
        try {
            count = (long) (Math.sqrt(count) * 2);
        } finally {
            lock.unlock();
        }
    }
    public long get(){
        return count;
    }
}
