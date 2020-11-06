package at;

public class Graph {

    //Lista de todos os n�s do grafo
    private CustomMap<String, Node> nodes = new CustomMap<>();

    //M�todo para adicionar um novo n� na lista
    public void addNode(Node nodeA) {
        nodes.put(nodeA.getCode(), nodeA);
    }

    public CustomMap<String, Node> getNodes() {
        return nodes;
    }
}