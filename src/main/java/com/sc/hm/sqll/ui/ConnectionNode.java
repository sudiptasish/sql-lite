package com.sc.hm.sqll.ui;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.TreeNode;

import com.sc.hm.sqll.config.ConnectionConfig;

public class ConnectionNode implements TreeNode {
	
	private String context = "";
	private ConnectionConfig config = null;
	private ConnectionNode parent;
		
	private Vector<ConnectionNode> children = null;
	
	public ConnectionNode(String context) {
	    this.context = context;
		children = new Vector<ConnectionNode>(2);
	}
	
	public ConnectionNode(String context, ConnectionConfig config, ConnectionNode parent) {
	    this.context = context;
	    this.config = config;
	    this.parent = parent;
		children = new Vector<ConnectionNode>(2);
	}
	
	public boolean hasChildren() {
		return children.size() > 0;
	}
	
	public void addChild(ConnectionNode child) {
		children.add(child);
	}

	public Enumeration<ConnectionNode> children() {
		return children.elements();
	}
	
	public boolean getAllowsChildren() {
		return true;
	}
	
	public TreeNode getChildAt(int childIndex) {
		if (childIndex >= children.size()) {
			return null;
		}
		return children.elementAt(childIndex);
	}

	public int getChildCount() {
		return children.size();
	}

	public int getIndex(TreeNode node) {
		for (int i = children.size() - 1; i >= 0; i --) {
			if (children.elementAt(i) == node) {
				return i;
			}
		}
		return -1;
	}

	public void setParent(ConnectionNode parent) {
		this.parent = parent;
	}

	public TreeNode getParent() {
		return parent;
	}

	public boolean isLeaf() {
		return children.size() == 0;
	}

	public ConnectionConfig getConfig() {
		return config;
	}

	public String toString() {
		return context;
	}
}
