package com.company;

import java.util.Queue;

//Citation: Codebase reused from 310 Project
class BinaryTreeNode{

	//members: all package visible
	Integer data;
	BinaryTreeNode left;
	BinaryTreeNode right;
	BinaryTreeNode parent;
	int depth;

	//constructors
	public BinaryTreeNode(int e){
		data = e;
		left = null;
		right = null;
		parent = null;
		depth = 0;
	}
	// compare two nodes
	// return true if: 1) they have the same element; and
	//                 2) their have matching left (subtree) and right (subtree)

	public boolean equals(BinaryTreeNode another){
		if(another == null){
			return false;
		}

		return data == another.data;

	}

	// toString
   	@Override
   	public String toString(){
   		return data.toString();
   	}




}