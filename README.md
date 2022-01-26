# COP4520 HOMEWORK 1

## ABOUT

Program in this repository can find all prime numbers from 2 to 10^8 (or other
giving upper limit). It is designed to use 8 threads that work concurrently. At
the end of the of the execution it writes results to a file.

The idea for the solution was taken from this source[^1] presented in ch. 1.1.
Threads share common counter that is implemented as atomic variable. This allows
synchronization between threads, since atomic variable's value could be read and
incremented as one atomic operation. In addition to that, a boolean array is
used to store the results of the checks for prime numbers, where value true
indicates that number represented by index of the array is prime. With this
solution approach, the writes to boolean array are thread safe since each thread
gets different not repeating number each time which is then used as index to
array. Therefore, it eliminates multiple writes in the same place of the array.

## NOTES ON EFFICIENCY

Even tho this solution provides even distribution of work between threads, it is
not an efficient way because it is using a lot of repeated computations. This is
due to the fact that it checks each individual number for primality (though
algorithm that is used for these checks is optimized[^2]). Space complexity
could also be improved with data structures that are designed to work in a
multithreaded environment to store just the prime numbers.

Running this program on my computer, I was getting execution times around
9000ms-10000ms. In comparison, single threaded program using classic algorithm
Sieve of Eratosthenes was able to accomplish the same task in less then 1000ms!

## HOW TO RUN

In order to run this program, navigate to the directory with the file inside and
type the following commands in terminal window:

```
javac PrimeNumbersFinder.java

java PrimeNumbersFinder
```

After that a new text file `primes.txt` should appear in the same directory that
contains all output specified by the assignment.

[^1]: Herlihy, M., & Shavit, N. (2012). *The Art of Multiprocessor Programming,
  Revised Reprint* (1st ed.). Morgan Kaufmann.
[^2]: Wikipedia contributors. (2022, January 11). *Primality test.* Wikipedia.
  https://en.wikipedia.org/wiki/Primality_test
