import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.CountDownLatch;

public class PrimeNumbersFinder {

  public static void main(String [] args) {

    // Use CountDownLatch to wait for all 8 threads to finish execution.
    CountDownLatch cdLatch = new CountDownLatch(8);

    // Measure how long will it take to find all primes.
    long startTime = System.currentTimeMillis();

    // Create and start 8 threads.
    for (int i = 0; i < 8; i++)
      new Thread(new PrimeFinderThread(cdLatch)).start();

    try {
      // Wait for threads to be done.
      cdLatch.await();
    } catch (InterruptedException e) {
      System.out.println("Interruption in main.");
    }

    long endTime = System.currentTimeMillis();
    long execTime = endTime - startTime;

    writeToFile("./primes.txt", execTime);
  }

  // Auxiliary method to write results to a file.
  private static void writeToFile(String filename, long executionTime) {
    // Create a string representation of the last ten prime numbers.
    StringBuilder last10Primes = new StringBuilder();

    // Find last ten primes in the array of numbers.
    for (int i = SharedData.UPPERLIMIT - 1, count = 10; i >= 2 && count > 0; i--) {
      if (SharedData.numbers[i] == true) {
        // Insert each new string at 0 index to create increasing order.
        last10Primes.insert(0, String.valueOf(i) + "\n");
        count--;
      }
    }

    // Write to file.
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
      bw.write("Execution time = " + String.valueOf(executionTime) + " ms" +
               "\nTotal number of primes found = " +
               String.valueOf(SharedData.numOfPrimes) +
               "\nSum of all primes found = " + SharedData.sumOfPrimes +
               "\nTop ten maximum primes:\n" + last10Primes.toString());
    } catch (FileNotFoundException e) {
      System.out.println("File Not Found.");
    } catch(IOException e) {
      System.out.println("I/O Error: " + e);
    }
  }

}

// Class that contains data that is shared between the threads.
// For synchronization purposes, it is making use of the classes
// from Java's atomic package.
class SharedData {
  static final int UPPERLIMIT = 100000000;
  static boolean [] numbers = new boolean[UPPERLIMIT];
  static AtomicInteger counter = new AtomicInteger(2);
  static AtomicInteger numOfPrimes = new AtomicInteger(0);
  static AtomicLong sumOfPrimes = new AtomicLong(0);
}


class PrimeFinderThread implements Runnable {
  CountDownLatch latch;

  PrimeFinderThread(CountDownLatch cdl) {
    latch = cdl;
  }

  public void run() {
    // Get the number to check for primality.
    int primeCandidate = SharedData.counter.getAndIncrement();

    while (primeCandidate < SharedData.UPPERLIMIT) {

      if (isPrime(primeCandidate)) {
        SharedData.sumOfPrimes.addAndGet(primeCandidate);
        SharedData.numOfPrimes.addAndGet(1);
        SharedData.numbers[primeCandidate] = true;
      }

      primeCandidate = SharedData.counter.getAndIncrement();
    }

    // Decrement latch counter.
    latch.countDown();
  }

  // Method that checks if given number is prime. Returns true if it is.
  // Credit: https://en.wikipedia.org/wiki/Primality_test
  private boolean isPrime(int number) {
    if (number == 2 || number == 3)
      return true;

    if (number % 2 == 0 || number % 3 == 0)
      return false;

    for (int i = 5; i * i <= number; i += 6) {
      if (number % i == 0 || number % (i + 2) == 0)
        return false;
    }

    return true;
  }
}
