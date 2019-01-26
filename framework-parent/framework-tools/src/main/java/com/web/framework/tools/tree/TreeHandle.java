package com.web.framework.tools.tree;

import java.util.List;

public interface TreeHandle<T> {
  /**
   * @param node
   * @return 返回 null表示跟节点
   */
  default T findParent(T node, List<T> source) {
    return null;
  }

  /**
   * 找到下级子节点，不是所有子节点
   * 
   * @param node null 表示 root节点
   * @param list 源数据
   * @return 可以null,表示没有子节点，是叶子节点
   */
  List<T> findChildren(T node, List<T> source);
}
