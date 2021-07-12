package main.java.investor;

import java.util.*;
import java.util.stream.Collectors;

public class InvestorGraph {
    Map<String, Node> graphNodes;

    public void printGraph() {
        System.out.println(graphNodes);
    }

    public Double getMarketValue(String fundName) {
        if (!graphNodes.containsKey(fundName)) {
            throw new RuntimeException("No Such Fund Exists into the System. Please check and resubmit");
        }
        Node fundNode = graphNodes.get(fundName);
        if (!(fundNode instanceof Fund)) {
            throw new RuntimeException("No Such Fund Exists into the System. Please check and resubmit");
        }
        return fundNode.getChilds().stream().mapToDouble(node -> ((Holding) node).getFundNameToQtyMap().get(fundName) * ((Holding) node).getMarketValue()).sum();
    }

    public void addNode(Map<Integer, Map<Node, List<Node>>> parentChildLevelMap) {

        // Track Nodes
        if (graphNodes == null || graphNodes.isEmpty()) {
            graphNodes = new HashMap<>();
        }

        Set<Integer> parentLevelSet = parentChildLevelMap.keySet();
        parentLevelSet.forEach(parentLevel -> {
            if (parentLevel == 1 || parentLevel == 2) {
                addParentChild(parentChildLevelMap, parentLevel);
            } else {
                System.out.println("Please note that it's 3 level graph so you can add parent level at only 1 or 2. " +
                        "For now, It will skip " + parentLevel + " for now.");
                return;
            }
        });
    }

    private void addParentChild(Map<Integer, Map<Node, List<Node>>> parentChildLevelMap, int parentLevel) {
        Map<Node, List<Node>> parentChildMap = parentChildLevelMap.get(parentLevel);
        Set<Node> parentsSet = parentChildMap.keySet();

        // Parent nodes
        parentsSet.forEach(parentNode -> {
            Node node = graphNodes.get(parentNode.getName());
            List<Node> childs = parentChildMap.get(parentNode);

            if (node == null) {
                node = parentNode;
                node.setChilds(new ArrayList<>());

                //validate to ensure parent of given parent node exists
                if (parentLevel == 2) {
                    List<Node> nodeList = node.getParents().stream().filter(n -> !graphNodes.containsKey(n.getName())).collect(Collectors.toList());
                    if (!nodeList.isEmpty()) {
                        System.out.println("One or More Parent Node of provided parent doesn't exists. " +
                                "First create level 1. For now, it will skip and proceed to the next one");
                    }
                    return;
                }

            }
            // add childs
            addChilds(childs, node, parentLevel + 1);
            graphNodes.put(node.getName(), node);
        });
    }

    private void addChilds(List<Node> childs, Node parentNode, int level) {
        childs.forEach(childNode -> {
            if (level == 3) {
                Holding holding = (Holding) childNode;
                if (holding.getMarketValue() == null || holding.getFundNameToQtyMap() == null || holding.getFundNameToQtyMap().isEmpty()) {
                    throw new RuntimeException("Child Node " + childNode.getName() + "is leaf node and missing market value or qty information");
                }
            }

            Node child = graphNodes.get(childNode.getName());
            if (child == null) {
                child = childNode;

                List<Node> parents = new ArrayList<>();
                parents.add(parentNode);
                child.setParents(parents);
                child.setChilds(new ArrayList<>());

            } else {
                child.getParents().add(parentNode);
            }
            parentNode.getChilds().add(child);

            graphNodes.put(child.getName(), child);
        });
    }

}
