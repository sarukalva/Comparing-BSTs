package com.company;

import java.util.ArrayDeque;
import java.util.Queue;

public class AccessSequence
{
    private int nkeys = 1000;
    private int access_seq_size = 10000;
    private int [] key_arr;
    private double [] key_accesses;
    private double [] key_dummies;

    public AccessSequence(int n, int access_seq){
        nkeys = n;
        access_seq_size = access_seq;
        key_dummies = new double[nkeys+1];
    }

    public int getNKeys() {
        return nkeys;
    }
    public int[] getKeyArr() {
        return key_arr;
    }

    public double[] getKey_accesses() {
        return key_accesses;
    }

    public double[] getKey_dummies() {
        return key_dummies;
    }

    public void setNkeys(int nkeys) {
        this.nkeys = nkeys;
    }
    public void setKey_arr(int[] key_arr) {
        this.key_arr = key_arr;
    }

    public int[] generateKeyArr(){
        // write your code here
        int[] arr = new int[nkeys + 1];
        //fill with numbers 1-1000
        for (int i = 1; i <= nkeys; i++) {
            arr[i] = i;
        }

        //the order of the key must be a uniform random permutation
        //algorithm pulled from textbook, pg. 126
        for (int i = 1; i <= nkeys; i++) {
            //"chooses the element A[i] randomly from among elements A[i]through A[n]"
            int random_key = returnRandom(i, nkeys);
            //perform swap
            int temp = arr[i];
            arr[i] = arr[random_key];
            arr[random_key] = temp;
        }
        key_arr = arr;
        return arr;
    }
    public Queue<Integer> keyAccessSeq(){
        if(key_arr == null) {
            generateKeyArr();
        }
        //Generate non-uniformly biased access sequences of keys
        int[] a_array = new int[nkeys+1];
        int[] c_array = new int[nkeys+1];
        //Generate n integers a_i where each is a random integer from 1 to 100
        for(int i = 1; i <= nkeys; i++){
            int random_int = returnRandom(1, 100);
            a_array[i] = random_int;
        }
        //c_i being the sum of the first i generated integers, c_0 = 0 and A = c_n.
        c_array[0] = 0;
        for(int i = 1; i <= nkeys; i++){
            c_array[i] = c_array[i-1] + a_array[i];
        }

        key_accesses = new double[nkeys+1];

        Queue<Integer> access_seq = new ArrayDeque<Integer>();
        for(int i = 0; i < access_seq_size; i++){
            //Obtain a random integer j from 1 to A
            int j = returnRandom(1, c_array[nkeys]);
            //“access” key k if c_(k-1) < j <= c_k.
            for (int k = 1; k <= c_array.length; k++){
                //find a key
                if(j > c_array[k-1] && j <= c_array[k]){
                    //keep track of the number of accesses to each key
                    key_accesses[k]++;
                    //add to access sequence
                    access_seq.add(k);
                    break;
                }
            }
        }
        return access_seq;
    }

    public static int returnRandom(int minimum, int maximum){
        return (int)(Math.random() * (maximum-minimum+1)) + minimum;
    }
}
