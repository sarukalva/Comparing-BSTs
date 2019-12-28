package com.company;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class SplayTree extends BinaryTree{
    public static final int NKEYS = 1000;
    public int cost = 0;
    public SplayTree(){
        super();
    }
    public SplayTree(BinaryTreeNode head){
        super(head);
    }
    public static void main(String[] args) {

        // write your code here

        int access_sequence_size = 10000;
        AccessSequence a = new AccessSequence(NKEYS, access_sequence_size);
        int[] key_arr = a.generateKeyArr();
        //int[] key_arr = {0, 3, 1, 4, 5, 2, 9, 6, 8};
        //a.setKey_arr(key_arr);
        Queue<Integer> seq = a.keyAccessSeq();


        //start each time with a randomly-built BST on the distinct keys (1, … , n}, n=1000.
        //standard insert of keys into an initially empty tree.
        System.out.println(Arrays.toString(key_arr));
        SplayTree s = new SplayTree();
        //BinaryTree b = new BinaryTree();

        /*b.buildFromArray(key_arr);
        String str1 = b.preOrder(b.getHead());
        System.out.println(str1);*/

        s.buildFromArray(key_arr);
        String str2 = s.preOrder(s.getHead());
        System.out.println(str2);

        for(int i : seq){
            s.splayFind(i);
        }

        str2 = s.preOrder(s.getHead());

        System.out.println(Arrays.toString(a.getKey_accesses()));
        System.out.println(seq);
        System.out.println(str2);


        //Splay Tree insert
    }
    //from Algorithms textbook, pg. 296
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
        cost++;
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
        cost++;
        p.parent = c;
        return c;
    }
    //double rotation
    public BinaryTreeNode zigZagWithRightChild(BinaryTreeNode g_parent){
        BinaryTreeNode parent = g_parent.left;
        g_parent.left = zagLeftWithRightChild(parent);
        return zigRightWithLeftChild(g_parent);
    }
    public BinaryTreeNode zagZigWithLeftChild(BinaryTreeNode g_parent){
        BinaryTreeNode parent = g_parent.right;
        g_parent.right = zigRightWithLeftChild(parent);
        return zagLeftWithRightChild(g_parent);
    }
    public BinaryTreeNode zigZigWithLeftChild(BinaryTreeNode g_parent){
        BinaryTreeNode parent = zigRightWithLeftChild(g_parent);
        return zigRightWithLeftChild(parent);
    }
    public BinaryTreeNode zagZagWithRightChild(BinaryTreeNode g_parent){
        BinaryTreeNode parent = zagLeftWithRightChild(g_parent);
        return zagLeftWithRightChild(parent);
    }
    //TODO:splay
    ////at the end of splay, the result must include the node at the
    // root of the tree
    public BinaryTreeNode splay(BinaryTreeNode node){
        if(node == null){
            return null;
        }
        //until you are at the top of the splay tree
        while (node.parent != null) {
            BinaryTreeNode g_parent = node.parent.parent;
            if(g_parent == null) {
                if (node.equals(node.parent.left)) {
                    zigRightWithLeftChild(node.parent);
                } else if (node.equals(node.parent.right)) {
                    zagLeftWithRightChild(node.parent);
                }
            }
            else{
                if(isLeftGrandchild(node)){
                    zigZigWithLeftChild(g_parent);
                }
                else if(isRightGrandchild(node)){
                    zagZagWithRightChild(g_parent);
                }
                else if(isRightLeftGrandchild(node)){
                    zigZagWithRightChild(g_parent);
                }
                else if(isLeftRightGrandchild(node)){
                    zagZigWithLeftChild(g_parent);
                }
            }
        }
        return node;
    }
    //zig zig
    public boolean isLeftGrandchild(BinaryTreeNode b){
        return (b.equals(b.parent.left) && b.parent.equals(b.parent.parent.left));
    }
    //zag zag
    public boolean isRightGrandchild(BinaryTreeNode b){
        return (b.equals(b.parent.right) && b.parent.equals(b.parent.parent.right));
    }
    //zigzag
    public boolean isRightLeftGrandchild(BinaryTreeNode b){
        return (b.equals(b.parent.right) && b.parent.equals(b.parent.parent.left));
    }
    //zigzag
    public boolean isLeftRightGrandchild(BinaryTreeNode b){
        return (b.equals(b.parent.left) && b.parent.equals(b.parent.parent.right));
    }
    //insert a unique key into tree
    //Algorithms textbook pg 294
    @Override
    public BinaryTreeNode insertNode(BinaryTreeNode node, int data){
        BinaryTreeNode parent = null;
        BinaryTreeNode root = node;
        BinaryTreeNode new_n = new BinaryTreeNode(data);
        //TODO:search operation?
        while(root != null){
            parent = root;
            if(new_n.data < root.data){
                root  = root.left;
            }
            else{
                root = root.right;
            }
        }
        new_n.parent = parent;
        if(parent == null){
            setHead(new_n);
        }
        else if(new_n.data < parent.data){
            parent.left = new_n;
        }
        else{
            parent.right = new_n;
        }
        return splay(new_n);
    }
    //if not found return last-visited
    public BinaryTreeNode splayFind(int data){
        BinaryTreeNode node = contains(getHead(), null, data);

        cost += 1 + node.depth;
        BinaryTreeNode returnNode = splay(node);
        updateDepth(getHead(), 0);
        return returnNode;

    }
    //public BinaryTreeNode contains(BinaryTreeNode node, int data){
    public BinaryTreeNode contains(BinaryTreeNode node, BinaryTreeNode lastVisited, int data){
        if(node == null){
            return lastVisited;
        }
        if(node.data == data){
            return node;
        }
        if(data < node.data){
            return contains(node.left, node, data);
        }
        else if(data > node.data){
            return contains(node.right, node, data);
        }
        return null;
    }







}