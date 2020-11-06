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

        //Busca o n� de destino
        Node destination = graph.getNodes().get(to);

        //Pega o n� inicial do caminho
        InnerNode<Node> iterator = destination.getShortestPath().getStart();

        //Se for null, imprime que n�o tem caminho
        if (destination.getShortestPath().getStart() == null) {
            System.out.println("Nenhum caminho encontrado");
        } else {
            //Itera sobre o caminho mais curto para o n� de destino e imprime cada n�
            while (iterator != null) {
                System.out.print(iterator.element.getName() + " >> ");
                iterator = iterator.next;
            }
            //Imprime o n� final
            System.out.println(destination.getName());
        }
    }

    public static void ShortestPath(Node source) {
        //Define a dist�ncia como 0 para o pr�prio n�
        source.setDistance((float) 0);

        //Prepara a lista para os n�s calculados e os n�s para calcular
        CustomList<Node> resolvedNodes = new CustomList<>();
        CustomList<Node> nodesToResolve = new CustomList<>();

        //ADiciona n� de origem a lista de n�s para calcular
        nodesToResolve.add(source);

        //enquanto houver n�s para calcular
        while (nodesToResolve.getSize() != 0) {
            //Pega o n� com menor dist�ncia entre os n�s para calcular
            Node currentNode = LowestDistance(nodesToResolve);
            //Remove o n� da lista de n�s para calcular
            nodesToResolve.remove(currentNode);

            //Busca todas as arestas desse n�
            for (Entry<Node, Float> adjacencyPair : currentNode.getAdjacentNodes().getBuckets()) {
                while (adjacencyPair != null) {
                    Node adjacentNode = adjacencyPair.key;
                    Float edgeWeight = adjacencyPair.value;
                    //Calcula a dist�ncia para essa aresta
                    //E adiciona o n� da aresta para a lista de n�s para resolver
                    if (!resolvedNodes.find(adjacentNode)) {
                        MinimumDistance(adjacentNode, edgeWeight, currentNode);
                        nodesToResolve.add(adjacentNode);
                    }

                    adjacencyPair = adjacencyPair.next;
                }
            }
            //Adicioan nos n�s resolvidos
            resolvedNodes.add(currentNode);
        }
    }

    private static Node LowestDistance(CustomList<Node> nodesToResolve) {
        //Itera sobre todos os n�s para calcular e encontra aquela cuja dist�ncia � menor
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
        //Calcula a menor dist�ncia entre dois n�s
        Float sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);

            //Na hora de reconstruir o caminho, pega o anterior e clona
            CustomList<Node> s = sourceNode.getShortestPath().Clone();

            //Para depois adicionar um novo n� no caminho
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

                //Cria os n�s ou busca no grafo caso ele j� tenha sido criado
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

                //Caso o segundo n� n�o seja null, ou seja, existe dist�ncia
                if (n2 != null) {
                    //Adiciona a rela��o entre os dois n�s
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
