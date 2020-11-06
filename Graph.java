package at;

public class Graph {

    //Lista de todos os nós do grafo
    private CustomMap<String, Node> nodes = new CustomMap<>();

    //Método para adicionar um novo nó na lista
    public void addNode(Node nodeA) {
        nodes.put(nodeA.getCode(), nodeA);
    }

    public CustomMap<String, Node> getNodes() {
        return nodes;
    }
}