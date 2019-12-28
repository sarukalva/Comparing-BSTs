package com.company;

import java.util.Arrays;
import java.util.Queue;


public class DynamicMonotone{
    public class DMNode{
        public int count;
        Integer data;
        DMNode left;
        DMNode right;
        DMNode parent;
        int depth;

        public DMNode(int e) {
            count = 0;
            data = e;
            left = null;
            right = null;
            parent = null;
            depth = 0;
        }
        @Override
        public String toString(){
            return data.toString();
        }

    }
    private DMNode head;
    int cost;
    public DynamicMonotone(){
        head = null;
        cost = 0;
    }
    public DMNode getHead(){
        return head;
    }
    public static void main(String[] args) {
        int nkeys = 1000;
        int access_sequence_size = 10000;

        AccessSequence a = new AccessSequence(nkeys, access_sequence_size);
        //int[] key_arr = {0, 4, 2, 1, 3, 6, 5, 7};
        //a.setKey_arr(key_arr);
        int[] key_arr = a.generateKeyArr();
        //System.out.println("Array of accesses to each key");
        System.out.println(Arrays.toString(key_arr));

        DynamicMonotone dmTree = new DynamicMonotone();
        dmTree.buildFromArray(key_arr);
        String str = dmTree.preOrder(dmTree.head);
        System.out.println(str);

        Queue<Integer> seq = a.keyAccessSeq();
        for(int i : seq){
            dmTree.access(i);
        }
        str = dmTree.preOrder(dmTree.head);
        System.out.println(str);
    }
    //TODO:redo buildfromarray?
    //its counter is incremented by one and the element is rotated upwards
    //while its counter is greater than the counter in its parent
    //Self Organizing Data Structures paper, pg 24

    public DMNode access(int data){
        DMNode node = (DMNode)contains(head, data);
        if(node == null){
            return null;
        }
        cost += 1 + node.depth;
        node.count++;
        while(node.parent != null && node.parent.count < node.count) {
            if (node.equals(node.parent.left)) {
                zigRightWithLeftChild(node.parent);
                cost++;
            } else if (node.equals(node.parent.right)) {
                zagLeftWithRightChild(node.parent);
                cost++;
            }
        }
        updateDepth(head, data);
        return node;
    }

    public DMNode buildFromArray(int[] arr){
        if(arr == null){
            return null;
        }
        for(int i = 1; i < arr.length; i++){
            head = insertNode(head, arr[i]);
        }
        updateDepth(head, 0);
        return head;
    }
    public DMNode insertNode(DMNode node, int data) {
        DMNode parent = null;
        DMNode root = node;
        DMNode new_n = new DMNode(data);

        while (root != null) {
            parent = root;
            if (new_n.data < root.data) {
                root = root.left;
            } else {
                root = root.right;
            }
        }
        new_n.parent = parent;
        if (parent == null) {
            head = new_n;
        } else if (new_n.data < parent.data) {
            parent.left = new_n;
        } else {
            parent.right = new_n;
        }
        return head;
    }


    //right rotation
    public DMNode zigRightWithLeftChild(DMNode p){
        DMNode c = (DMNode)p.left;
        p.left = c.right;
        if(c.right != null){
            c.right.parent = p;
        }
        c.right = p;

        if(p.parent ==null){
            head = c;
        }
        else{
            if(p == p.parent.left){
                p.parent.left = c;
            }
            else{
                p.parent.right = c;
            }
        }
        if(c != null){
            c.parent = p.parent;
        }
        p.parent = c;
        return c;
    }
    //left rotation
    public DMNode zagLeftWithRightChild(DMNode p){
        DMNode c = (DMNode)p.right;
        p.right = c.left;
        if(c.left != null){
            c.left.parent = p;
        }
        c.left = p;

        if(p.parent ==null){
            head = c;
        }
        else if(p == p.parent.left){
            p.parent.left = c;
        }
        else{
            p.parent.right = c;
        }
        if(c != null){
            c.parent = p.parent;
        }
        p.parent = c;
        return c;
    }
    public String preOrder(DMNode node){
        if(node == null){
            return "";
        }
        return node.data + " " +  preOrder(node.left) +  " " + preOrder(node.right);
    }
    public DMNode contains(DMNode node, int data){
        if(node == null){
            return null;
        }
        if(node.data == data){
            return node;
        }
        if(data < node.data){
            return contains(node.left, data);
        }
        else if(data > node.data){
            return contains(node.right, data);
        }
        return null;
    }
    //at the end of insert, update depth of all nodes
    public void updateDepth(DMNode node, int depth)
    {
        if (node != null)
        {
            if(node.parent == null){
                depth = 1;
            }
            node.depth = depth;
            updateDepth(node.left, depth + 1);
            updateDepth(node.right, depth + 1);
        }
    }
}
