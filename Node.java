package at;

public class Node {

    //Dados de cada nó
    private String code;
    private String name;

    //Mneor caminho desse nó para o nó de origem
    private CustomList<Node> shortestPath = new CustomList<>();

    //Distância inicia com a distância máxima possível
    private Float distance = Float.MAX_VALUE;

    //HashMap de nós adjacentes
    CustomMap<Node, Float> adjacentNodes = new CustomMap<>();

    //Adiciona uma nova aresta
    public void addDestination(Node destination, float distance) {
        adjacentNodes.put(destination, distance);
    }

    public Node(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CustomList<Node> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(CustomList<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public CustomMap<Node, Float> getAdjacentNodes() {
        return adjacentNodes;
    }
}