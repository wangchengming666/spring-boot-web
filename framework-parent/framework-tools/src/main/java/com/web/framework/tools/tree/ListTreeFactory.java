package com.web.framework.tools.tree;

import java.util.List;
import java.util.Objects;

public class ListTreeFactory<T> implements TreeFactory {

  private List<T> source;
  private TreeHandle<T> treeHandle;

  public ListTreeFactory(List<T> source, TreeHandle<T> treeHandle) {
    super();
    this.source = source;
    this.treeHandle = treeHandle;
    Objects.requireNonNull(source);
    Objects.requireNonNull(treeHandle);
  }

  @Override
  public TreeNode<T> build() {
    TreeNode<T> root = new TreeNode<T>(null);

    addChildrenTreeNode(null, root, source, treeHandle);

    return root;
  }
  /**
   * 
   * @param node 当前数据
   * @param treeNode 当前节点
   * @param list 源数据
   * @param convert 转换器
   */
  private void addChildrenTreeNode(T node, TreeNode<T> treeNode, List<T> list,
      TreeHandle<T> convert) {
    List<T> children = convert.findChildren(node, list);

    if (children != null && children.size() != 0) {
      for (T t : children) {
        // 添加子节点
        TreeNode<T> childTreeNode = treeNode.addChild(t);
        // 继续查找子节点的下级节点
        addChildrenTreeNode(t, childTreeNode, list, convert);
      }
    }
  }

}
