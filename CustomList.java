package at;

public class CustomList<V> {
    private InnerNode<V> start = null;
    private InnerNode<V> end = null;

    private int size = 0;

    //Adiciona um novo elemento na lista
    public void add(V n)
    {
        if (this.find(n)) {
            return;
        }
        size++;
        InnerNode<V> newElement = new InnerNode<>(n);
        if (this.start == null) {
            this.start = newElement;
        } else {
            this.end.next = newElement;
            newElement.last = this.end;
        }
        this.end = newElement;
    }

    public InnerNode<V> getStart() {
        return start;
    }

    //Clona a lista original mantendo apenas os endereços de memórias dos Node, mas ignorando o InnerNode
    public CustomList<V> Clone()
    {
        CustomList<V> clone = new CustomList<>();
        InnerNode<V> iterator = this.getStart();

        while (iterator != null) {
            clone.add(iterator.element);
            iterator = iterator.next;
        }

        return clone;
    }

    public int getSize() {
        return size;
    }

    //Procura um elemento na lista
    public boolean find(V el)
    {
        InnerNode<V> iterator = this.getStart();

        while (iterator != null) {
            if (el == iterator.element) {
                return true;
            }
            iterator = iterator.next;
        }

        return false;
    }

    //Remove um elemento caso ele exista na lista
    public boolean remove(V el)
    {
        if (!find(el) || this.size == 0) {
            return false;
        }

        this.size--;

        InnerNode<V> iterator = this.getStart();

        while (iterator != null) {
            if (el == iterator.element) {
                //refaz os apontamentos
                if (iterator.last != null) {
                    iterator.last.next = iterator.next;
                }

                if (iterator.next != null) {
                    iterator.next.last = iterator.last;
                }

                if (iterator == start) {
                    start = iterator.next;
                }

                if (iterator == end) {
                    end = iterator.last;
                }

                return true;
            }
            iterator = iterator.next;
        }

        return false;
    }
}