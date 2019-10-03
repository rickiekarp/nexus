package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung11.Elf;

/**
 * Implementation des Sieb des Eratosthenes zur Berechnung von Primzahlen
 */
public class Eratosthenes {
    public static int[] calcPrimesBelow(int bound) {
        boolean[] isPrime = new boolean[bound];
        for (int i = 0; i < bound; i++) {
            isPrime[i] = true;
        }

        double root = Math.sqrt(bound);
        for (int i = 2; i < root; i++) {
            if (isPrime[i]) {
                for (int k = 2*i; k < bound; k+= i) {
                    isPrime[k] = false;
                }
            }
        }

        int count = 0;
        for (int i = 2; i < bound; i++) {
            if (isPrime[i]) {
                count++;
            }
        }

        int[] primes = new int[count];
        int j = 0;
        for (int i = 2; i < bound; i++) {
            if (isPrime[i]) {
                primes[j] = i;
                j++;
            }
        }

        return primes;
    }
}
