package at;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    private static Graph graph;

    public static void main(String[] args) {
        //Instancia e popula o grafo com base no CSV/TXT
        graph = new Graph();
        Main.populate(graph);

        //Determina origem e fim
        String from = "BR";
        String to = "CA";

        //Calcula os caminhos mais curtos
        Main.ShortestPath(graph.getNodes().get(from));

        //Busca o nó de destino
        Node destination = graph.getNodes().get(to);

        //Pega o nó inicial do caminho
        InnerNode<Node> iterator = destination.getShortestPath().getStart();

        //Se for null, imprime que não tem caminho
        if (destination.getShortestPath().getStart() == null) {
            System.out.println("Nenhum caminho encontrado");
        } else {
            //Itera sobre o caminho mais curto para o nó de destino e imprime cada nó
            while (iterator != null) {
                System.out.print(iterator.element.getName() + " >> ");
                iterator = iterator.next;
            }
            //Imprime o nó final
            System.out.println(destination.getName());
        }
    }

    public static void ShortestPath(Node source) {
        //Define a distância como 0 para o próprio nó
        source.setDistance((float) 0);

        //Prepara a lista para os nós calculados e os nós para calcular
        CustomList<Node> resolvedNodes = new CustomList<>();
        CustomList<Node> nodesToResolve = new CustomList<>();

        //ADiciona nó de origem a lista de nós para calcular
        nodesToResolve.add(source);

        //enquanto houver nós para calcular
        while (nodesToResolve.getSize() != 0) {
            //Pega o nó com menor distância entre os nós para calcular
            Node currentNode = LowestDistance(nodesToResolve);
            //Remove o nó da lista de nós para calcular
            nodesToResolve.remove(currentNode);

            //Busca todas as arestas desse nó
            for (Entry<Node, Float> adjacencyPair : currentNode.getAdjacentNodes().getBuckets()) {
                while (adjacencyPair != null) {
                    Node adjacentNode = adjacencyPair.key;
                    Float edgeWeight = adjacencyPair.value;
                    //Calcula a distância para essa aresta
                    //E adiciona o nó da aresta para a lista de nós para resolver
                    if (!resolvedNodes.find(adjacentNode)) {
                        MinimumDistance(adjacentNode, edgeWeight, currentNode);
                        nodesToResolve.add(adjacentNode);
                    }

                    adjacencyPair = adjacencyPair.next;
                }
            }
            //Adicioan nos nós resolvidos
            resolvedNodes.add(currentNode);
        }
    }

    private static Node LowestDistance(CustomList<Node> nodesToResolve) {
        //Itera sobre todos os nós para calcular e encontra aquela cuja distância é menor
        Node lowestDistanceNode = null;
        float lowestDistance = Float.MAX_VALUE;
        InnerNode<Node> iterator = nodesToResolve.getStart();
        while (iterator != null) {
            float nodeDistance = iterator.element.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = iterator.element;
            }
            iterator = iterator.next;
        }
        return lowestDistanceNode;
    }

    private static void MinimumDistance(Node evaluationNode, Float edgeWeigh, Node sourceNode) {
        //Calcula a menor distância entre dois nós
        Float sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);

            //Na hora de reconstruir o caminho, pega o anterior e clona
            CustomList<Node> s = sourceNode.getShortestPath().Clone();

            //Para depois adicionar um novo nó no caminho
            s.add(sourceNode);
            evaluationNode.setShortestPath(s);
        }
    }

    private static void populate(Graph graph) {
        BufferedReader csvReader = null;
        try {
            //Abre o CSV/TXT
            csvReader = new BufferedReader(new FileReader("paises.txt"));
            String row;
            //Pega cada linha
            while ((row = csvReader.readLine()) != null) {
                //Separa na ,
                String[] data = row.split(",");

                //Cria os nós ou busca no grafo caso ele já tenha sido criado
                Node n1, n2 = null;
                if (graph.getNodes().containsKey(data[0])) {
                    n1 = graph.getNodes().get(data[0]);
                } else {
                    n1 = new Node(data[0], data[1]);
                    graph.getNodes().put(data[0], n1);
                }

                if (!data[3].equals("")) {
                    if (graph.getNodes().containsKey(data[2])) {
                        n2 = graph.getNodes().get(data[2]);
                    } else {
                        n2 = new Node(data[2], data[3]);
                        graph.getNodes().put(data[2], n2);
                    }
                }

                //Caso o segundo nó não seja null, ou seja, existe distância
                if (n2 != null) {
                    //Adiciona a relação entre os dois nós
                    float distance = Float.parseFloat(data[4]);

                    n1.addDestination(n2, distance);
                    n2.addDestination(n1, distance);
                }
            }
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
