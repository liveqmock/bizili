package com.vteba.util.json;

import java.util.List;

/**
 * 树形节点，用于Json显示。
 * 
 * @author yinlei
 * @date 2014-03-26 21:30
 */
public class Node {
	private String id;
	private String name;
	private List<Node> children;

	public Node(Long id, String name) {
		super();
		this.id = id.toString();
		this.name = name;
	}
	
	public Node(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Node(String id, String name, List<Node> children) {
		super();
		this.id = id;
		this.name = name;
		this.children = children;
	}

	public Node(Long id, String name, List<Node> children) {
		super();
		this.id = id.toString();
		this.name = name;
		this.children = children;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Node> getChildren() {
		return children;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}
}
