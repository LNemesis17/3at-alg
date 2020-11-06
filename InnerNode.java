package at;

public class InnerNode<V> {
    //Cada elemento da lista de menor caminho
    InnerNode(V n) {
        this.element = n;
    }

    public V element;
    public InnerNode<V> next;
    public InnerNode<V> last;
}