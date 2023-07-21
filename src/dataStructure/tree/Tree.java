package dataStructure.tree;


public class Tree<T> {
    protected Tree<T> left;
    protected Tree<T> right;

    public Tree() {}

    public Tree(int size) {}

    public Tree<T> getLeft() {
        return left;
    }

    public void setLeft(Tree<T> left) {
        this.left = left;
    }

    public Tree<T> getRight() {
        return right;
    }

    public void setRight(Tree<T> right) {
        this.right = right;
    }
}
