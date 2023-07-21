package test;

public class VarHolder <T> {

    private T val;

    public VarHolder() {
        this.val = null;
    }

    public VarHolder(T val) {
        this.val = val;
    }

    public T get() {
        return val;
    }

    public void set(T val) {
        this.val = val;
    }
}