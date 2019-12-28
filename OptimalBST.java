package com.company;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class OptimalBST extends BinaryTree{
    public static int NKEYS = 1000;
    public static double cost = 0;
    public static void main(String[] args) {

	// write your code here
        int [] key_arr = new int [NKEYS+1];
        //fill with numbers 1-1000
        for(int i = 1; i <= NKEYS; i++){
            key_arr[i] = i;
        }

        //the order of the key must be a uniform random permutation
        //algorithm pulled from textbook, pg. 126
        for(int i = 1; i <= NKEYS; i++){
            //"chooses the element A[i] randomly from among elements A[i]through A[n]"
            int random_key = returnRandom(i, NKEYS);
            //perform swap
            int temp = key_arr[i];
            key_arr[i] = key_arr[random_key];
            key_arr[random_key] = temp;
        }


        //Generate non-uniformly biased access sequences of keys
        int[] a_array = new int[NKEYS+1];
        int[] c_array = new int[NKEYS+1];
        //Generate n integers a_i where each is a random integer from 1 to 100
        for(int i = 1; i <= NKEYS; i++){
            int random_int = returnRandom(1, 100);
            a_array[i] = random_int;
        }
        //c_i being the sum of the first i generated integers, c_0 = 0 and A = c_n.
        c_array[0] = 0;
        for(int i = 1; i <= NKEYS; i++){
            c_array[i] = c_array[i-1] + a_array[i];
        }

        double [] key_accesses = new double[NKEYS+1];
        double [] key_dummies = new double[NKEYS+1];
        //double [] key_accesses = {0, .15, .10, .05, .10, .20};
        //double [] key_dummies = {.05, .1, .05, .05, .05, .1};
        int access_seq_size = 10000;
        Queue<Integer> access_seq = new ArrayDeque<Integer>();
        for(int i = 0; i < access_seq_size; i++){
            //Obtain a random integer j from 1 to A
            int j = returnRandom(1, c_array[NKEYS]);
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
        //calculate probabilities
        double sum = 0;
        for(int k = 1; k < key_accesses.length; k++){
            if(key_accesses[k] == 0){
                key_accesses[k]++;
            }
        }

        OptimalBST.generateOBST(key_accesses, key_dummies, NKEYS);


        /*toString(e);
        System.out.println();
        toString(w);
        System.out.println();
        rootString(root);*/

        System.out.println();

        System.out.println(cost);
        //TODO:print the optimal tree
    }
    public static BinaryTreeNode generateOBST(double[] key_accesses, double[] key_dummies, int nkeys){
        //OPTIMAL BST pg 402
        //create the static optimal BST table with those frequencies
        double [] [] e = new double [nkeys+2][nkeys+1];
        double [] [] w = new double [nkeys+2][nkeys+1];
        int [] [] root = new int [nkeys+1][nkeys+1];
        //modification of algorithm: did not initialize e and w values to dummy nodes
        for(int i = 1; i <= nkeys+1; i++){
            e[i][i-1] = key_dummies[i-1];
            w[i][i-1] = key_dummies[i-1];
        }
        for(int l = 1; l <= nkeys; l++){
            for(int i = 1; i <= nkeys-l+1; i++){
                int j = i+l-1;
                e[i][j] = Double.POSITIVE_INFINITY;;
                w[i][j] = w[i][j-1] + key_accesses[j] + key_dummies[j];//dafd
                for(int r = i; r <= j; r++){
                    //minimum optimal cost
                    double t = e[i][r-1]+e[r+1][j]+w[i][j];
                    if(t < e[i][j]){
                        e[i][j] = t;
                        root[i][j] = r;
                    }
                }
            }
        }
        BinaryTreeNode head = optimalBST(root, 1, NKEYS);
        cost = bstCost(head, key_accesses, 0);
        return head;
    }
    public static void toString(double[][] arr){
        for(int col = NKEYS; col >= 0; col--){
            for(int row = 1; row <= NKEYS+1; row++){
                String num = Double.toString(Math.round(arr[row][col]*100.0) / 100.0);
                if(arr[row][col] == 0){
                    num = "0.00";
                }
                System.out.print(" " + num + " ");
            }
            System.out.println();
        }
    }
    public static void rootString(int[][] arr){
        for(int col = NKEYS; col >= 1; col--){
            for(int row = 1; row <= NKEYS; row++){
                System.out.print(" " + arr[row][col] + " ");
            }
            System.out.println();
        }
    }
    public static BinaryTreeNode optimalBST(int [][] root, int i, int j){
        BinaryTreeNode b;
        if (i > j){
            return null;
        }
        if(i == 0){
          b =  new BinaryTreeNode(root[i][j]+1);
        }
        if(i == j){
            return new BinaryTreeNode(root[i][j]);
        }
        else {
            b = new BinaryTreeNode(root[i][j]);
        }
        b.left = optimalBST(root, i, root[i][j]-1);
        b.right = optimalBST(root, root[i][j]+1,j);
        return b;
    }
    public static double bstCost(BinaryTreeNode b, double[] weights, int depth){
        if(b == null){
            return 0;
        }
        double cost = depth * weights[b.data];
        cost += bstCost(b.left, weights, depth+1) + bstCost(b.right, weights, depth+1);
        return cost;
    }
    public static int returnRandom(int minimum, int maximum){
        return (int)(Math.random() * (maximum-minimum+1)) + minimum;
    }
    /*public static String toStringLevelOrder(BinaryTreeNode root, int tab_space){
        if(root == null){
            return null;
        }
        for(int i = COUNT; i < tab_space; i++){

        }

    }*/
}
