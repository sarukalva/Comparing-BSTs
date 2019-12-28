package com.company;

import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.Queue;

public class SingleRotation extends BinaryTree{
    public int rotationCount = 0;
    public int cost = 0;
    public SingleRotation(){
        super();
    }
    public SingleRotation(BinaryTreeNode head){
        super(head);
    }
    public static void main(String[] args) {
        /*int[] arr = {0, 4, 2, 1, 3, 6, 5, 7};
        BinaryTree b = new BinaryTree();
        b.buildFromArray(arr);*/

        int nkeys = 1000;
        int access_sequence_size = 10000;

        AccessSequence a = new AccessSequence(nkeys, access_sequence_size);
        //int[] key_arr = {0, 3, 1, 4, 5, 2, 9, 6, 8};
        //a.setKey_arr(key_arr);
        int[] key_arr = a.generateKeyArr();
        System.out.println(Arrays.toString(key_arr));

        SingleRotation s = new SingleRotation();
        s.buildFromArray(key_arr);
        String str = s.preOrder(s.getHead());
        System.out.println(str);

        Queue<Integer> seq = a.keyAccessSeq();
        for(int i : seq){
            s.access(i);
        }
        str = s.preOrder(s.getHead());
        System.out.println(str);
    }

    public BinaryTreeNode access(int data){
        BinaryTreeNode node = contains(getHead(), data);
        if(node == null){
            return null;
        }
        cost += 1 + node.depth;
        BinaryTreeNode n = rotateEdge(node);
        updateDepth(getHead(), 0);
        return n;
    }
    public BinaryTreeNode rotateEdge(BinaryTreeNode node){
        if(node.parent != null){
            if (node.equals(node.parent.left)) {
                zigRightWithLeftChild(node.parent);
                cost++;
            } else if (node.equals(node.parent.right)) {
                zagLeftWithRightChild(node.parent);
                cost++;
            }
        }
        return node;
    }

    //right rotation
    public BinaryTreeNode zigRightWithLeftChild(BinaryTreeNode p){
        BinaryTreeNode c = p.left;
        p.left = c.right;
        if(c.right != null){
            c.right.parent = p;
        }
        c.right = p;

        if(p.parent ==null){
            setHead(c);
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
    public BinaryTreeNode zagLeftWithRightChild(BinaryTreeNode p){
        BinaryTreeNode c = p.right;
        p.right = c.left;
        if(c.left != null){
            c.left.parent = p;
        }
        c.left = p;

        if(p.parent ==null){
            setHead(c);
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

}
