package com.company;

import java.util.ArrayDeque;

public class BinaryTree {
    private BinaryTreeNode head;
    public BinaryTree(BinaryTreeNode root){
        head = root;
    }
    public BinaryTree(){
        head = null;
    }
    public BinaryTreeNode buildFromArray(int[] arr){
        if(arr == null){
            return null;
        }
        for(int i = 1; i < arr.length; i++){
            head = insertNode(head, arr[i]);
        }
        updateDepth(head, 0);
        return head;
    }

    /*public BinaryTreeNode insertNode(BinaryTreeNode node, int data){
        if(node == null){
            node = new BinaryTreeNode(data);
            return node;
        }
        else {
            if (data > node.data) {
                node.right = insertNode(node.right, data);
            }
            else if (data < node.data) {
                node.left = insertNode(node.left, data);
            }
        }
        return node;
    }*/
    public BinaryTreeNode insertNode(BinaryTreeNode node, int data) {
        BinaryTreeNode parent = null;
        BinaryTreeNode root = node;
        BinaryTreeNode new_n = new BinaryTreeNode(data);

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
            setHead(new_n);
        } else if (new_n.data < parent.data) {
            parent.left = new_n;
        } else {
            parent.right = new_n;
        }
        return getHead();
    }
    public BinaryTreeNode contains(BinaryTreeNode node, int data){
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

    public BinaryTreeNode getHead(){
        return head;
    }
    public void setHead(BinaryTreeNode node){
        head = node;
    }
    public String preOrder(BinaryTreeNode node){
        if(node == null){
            return "";
        }
        return node.data + " " +  preOrder(node.left) +  " " + preOrder(node.right);
    }

    //at the end of insert, update depth of all nodes
    public void updateDepth(BinaryTreeNode node, int depth)
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
