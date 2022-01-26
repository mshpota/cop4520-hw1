# COP4520 HOMEWORK 1

### ABOUT

Program in this repository can find all prime numbers from 2 to 10^8 (or other given upper limit). It is designed to use 8 threads that work concurrently. At the end of the execution, it writes results to a file.

The idea for the solution was taken from this source[^1] presented in ch. 1.1. Threads share a common counter that is implemented as an atomic variable. This allows synchronization between threads since an atomic variable's value could be read and incremented as one atomic operation. In addition to that, a boolean array is used to store the results of the checks for prime numbers, where a `true` value indicates that the number represented by the index of the array is prime. With this solution approach, the array is thread-safe since each thread gets a different unique number each time, which is then used to index the array. Therefore, it eliminates multiple writes in the same place of the array.

### NOTES ON EFFICIENCY

Even though this solution provides an even distribution of work between threads, it is not an efficient way because it is using a lot of repeated computations. This is because it checks each number for primality (though the algorithm that is used for these checks is optimized[^2]). Space complexity could also be improved with data structures that are designed to work in a multi-threaded environment to store just the prime numbers.

When running this program on my computer, I was getting execution times around 9000 -10000 ms. In comparison, a single-threaded program using the classic algorithm Sieve of Eratosthenes was able to accomplish the same task in less than 1000 ms!

### HOW TO RUN

To run this program, navigate to the directory with the file and type the following commands in the terminal window:

```
javac PrimeNumbersFinder.java

java PrimeNumbersFinder
```

After that, a new text file `primes.txt` should appear in the same directory that contains all output specified by the assignment.

[^1]: Herlihy, M., & Shavit, N. (2012). *The Art of Multiprocessor Programming,
  Revised Reprint* (1st ed.). Morgan Kaufmann.
[^2]: Wikipedia contributors. (2022, January 11). *Primality test.* Wikipedia.
  https://en.wikipedia.org/wiki/Primality_test
