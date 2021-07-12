package main.java.investor;

import java.util.List;

class Node {
    List<Node> parents;
    List<Node> childs;
    String name;

    public List<Node> getChilds() {
        return childs;
    }

    public void setChilds(List<Node> childs) {
        this.childs = childs;
    }

    public List<Node> getParents() {
        return parents;
    }

    public void setParents(List<Node> parents) {
        this.parents = parents;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Node{" +
                "parents=" + parents +
                ", childs=" + childs +
                ", name='" + name + '\'' +
                '}';
    }
}
