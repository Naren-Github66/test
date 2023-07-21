package dataStructure.tree;

public class AVLTree<T> extends Tree<T> {

    private AVLTree<T> left;
    private AVLTree<T> right;

    private T value;
    private int height;

    public int getHeight() {
        return height;
    }
    public AVLTree(T value) {
        this.value = value;
    }

    public AVLTree(int height, T value) {
        genAVLTree(height, value);
    }

    public AVLTree<T> genAVLTree(int height, T value) {
        this.height = height;
        return genAVLTree(this, height, value);
    }

    private AVLTree<T> genAVLTree(AVLTree<T> avlTree, int height, T value) {
        if (height == 0) {
            return avlTree;
        }
        avlTree.height = height;
        avlTree.value = value;
        avlTree.left = new AVLTree<T>(value);
        avlTree.right = new AVLTree<T>(value);
        height--;
        genAVLTree(avlTree.left, height, value);
        genAVLTree(avlTree.right, height, value);
        return avlTree;
    }

    public void toTreeString() {
        System.out.println("value" + this.value);
        toTreeString(this, height);
    }

    private void toTreeString(AVLTree<T> avlTree, int height) {
        if (height == 0) {
            return;
        }
        System.out.print(avlTree.left);
        System.out.print(avlTree.right);
        System.out.println();
        height--;
        toTreeString(avlTree.left, height);
        toTreeString(avlTree.right, height);
    }
}
