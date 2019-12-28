package com.company;

import java.util.Arrays;
import java.util.Queue;

public class TestingTrees {


    public static void main(String[] args) {
        //loop 10 times
        for(int loop = 1; loop <= 10; loop++){
            //create a key access arr,seq
            int nkeys = 1000;
            int access_sequence_size = 10000;
            System.out.println("EXPERIMENT " + loop);
            System.out.println("---------------------------------------------");
            AccessSequence a = new AccessSequence(nkeys, access_sequence_size);

            int[] key_arr = a.generateKeyArr();
            Queue<Integer> seq = a.keyAccessSeq();

            System.out.println("Random key array");
            System.out.println(Arrays.toString(key_arr));
            System.out.println("Array of key access sequence");
            System.out.println(seq);
            System.out.println();

            BinaryTreeNode oBST = OptimalBST.generateOBST(a.getKey_accesses(), a.getKey_dummies(), nkeys);
            double oBST_cost = OptimalBST.cost;

            SingleRotation srtree = new SingleRotation();
            srtree.buildFromArray(key_arr);
            String str = srtree.preOrder(srtree.getHead());
            System.out.println("Single Rotation: Before Access");
            System.out.println(str);
            for (int i : seq) {
                srtree.access(i);
            }
            double sr_cost = srtree.cost;
            System.out.println("After Access");
            str = srtree.preOrder(srtree.getHead());
            System.out.println(str);
            System.out.println();

            SplayTree splayTree = new SplayTree();
            splayTree.buildFromArray(key_arr);
            str = splayTree.preOrder(splayTree.getHead());
            System.out.println("Splay Tree: Before Access");
            System.out.println(str);
            for (int i : seq) {
                splayTree.splayFind(i);
            }
            double splay_cost = splayTree.cost;
            System.out.println("After Access");
            str = splayTree.preOrder(splayTree.getHead());
            System.out.println(str);
            System.out.println();

            DynamicMonotone dmTree = new DynamicMonotone();
            dmTree.buildFromArray(key_arr);
            str = dmTree.preOrder(dmTree.getHead());
            System.out.println("Dynamic Monotone Tree: Before Access");
            System.out.println(str);
            for (int i : seq) {
                dmTree.access(i);
            }
            double dmt_cost = dmTree.cost;
            System.out.println("After Access");
            str = dmTree.preOrder(dmTree.getHead());
            System.out.println(str);
            System.out.println();

            double sr_scr = sr_cost / oBST_cost;
            double splay_scr = splay_cost / oBST_cost;
            double dmt_scr = dmt_cost / oBST_cost;

            System.out.println("Optimal BST Cost: "+oBST_cost);

            System.out.println("SR Cost: "+sr_cost);
            System.out.println("Splay Cost: "+splay_cost);
            System.out.println("DMT Cost: "+dmt_cost);
            System.out.println();

            System.out.println("Static Competitive Ratio:");

            System.out.println("SR: "+sr_scr);
            System.out.println("Splay: "+splay_scr);
            System.out.println("DMT: "+dmt_scr);
            System.out.println();
            System.out.println();
        }
    }
}
