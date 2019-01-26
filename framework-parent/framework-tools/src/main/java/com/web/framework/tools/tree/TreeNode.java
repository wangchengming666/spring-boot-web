package com.web.framework.tools.tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TreeNode<T> implements Iterable<TreeNode<T>> {

  public T data;
  public TreeNode<T> parent;
  public List<TreeNode<T>> children;

  private boolean isRoot;
  private boolean isLeaf;
  private int level;

  public TreeNode(T data) {
    this.data = data;
    this.children = new LinkedList<TreeNode<T>>();
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public TreeNode<T> getParent() {
    return parent;
  }

  public void setParent(TreeNode<T> parent) {
    this.parent = parent;
  }

  public List<TreeNode<T>> getChildren() {
    return children;
  }

  public void setChildren(List<TreeNode<T>> children) {
    this.children = children;
  }

  public void setRoot(boolean isRoot) {
    this.isRoot = isRoot;
  }

  public void setLeaf(boolean isLeaf) {
    this.isLeaf = isLeaf;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public boolean isRoot() {
    return parent == null;
  }

  public boolean isLeaf() {
    return children.size() == 0;
  }

  /**
   * 
   * @param child
   * @return 返回加入的子节点
   */
  public TreeNode<T> addChild(T child) {
    TreeNode<T> childNode = new TreeNode<T>(child);
    childNode.parent = this;
    this.children.add(childNode);
    return childNode;
  }

  public int getLevel() {
    if (this.isRoot())
      return 0;
    else
      return parent.getLevel() + 1;
  }

  @Override
  public String toString() {
    return data != null ? data.toString() : "[data null]";
  }

  @Override
  public Iterator<TreeNode<T>> iterator() {
    return new TreeIterator<T>(this);
  }
}
