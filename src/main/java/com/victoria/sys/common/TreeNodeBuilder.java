package com.victoria.sys.common;


import java.util.ArrayList;
import java.util.List;

public class TreeNodeBuilder {

    public static List<TreeNode> builder(List<TreeNode> treeNodes,Integer topId) {
        List<TreeNode> nodes = new ArrayList<>();
        for (TreeNode n1 : treeNodes) {
            if (n1.getPid() == topId) {
                nodes.add(n1);
            }
            for (TreeNode n2 : treeNodes) {
                if (n1.getId() == n2.getPid()) {
                    n1.getChildren().add(n2);
                }
            }
        }
        return nodes;
    }
}
