/*
Kevin Lou
11/19/2020
This class contains an algorithm for implementing dijkstras algorithm
and visualizing the algorithm
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class Dijkstra extends JPanel {

    JFrame jfrm;
    ArrayList<Connection> connections = new ArrayList<>();
    ArrayList<Node> nodes = new ArrayList<>();
    int nodeCount = 4;
    int[] minDist;

    public Dijkstra() throws InterruptedException {

        int incr = (int)(360.0/nodeCount);
        int currDeg = -incr;

        for(int i = 0; i < nodeCount; i++){
            currDeg += incr;
            double rad = currDeg*Math.PI/180;
            int x = (int) (250+200*Math.cos(rad));
            int y = (int) (250-200*Math.sin(rad));
            if(i == 0){
                nodes.add(new Node(0, Integer.MAX_VALUE, x, y, Color.MAGENTA));
            }else if(i == nodeCount - 1){
                nodes.add(new Node(i, Integer.MAX_VALUE, x, y, Color.MAGENTA));
            }else{
                nodes.add(new Node(i, Integer.MAX_VALUE, x, y, Color.BLUE));
            }

        }

        connections.add(new Connection(nodes.get(0),nodes.get(1), 3, Color.BLACK));
        connections.add(new Connection(nodes.get(0), nodes.get(2), 5, Color.BLACK));
        connections.add(new Connection(nodes.get(1),nodes.get(2), 1, Color.BLACK));
        connections.add(new Connection(nodes.get(1),nodes.get(3), 7, Color.BLACK));
        connections.add(new Connection(nodes.get(2),nodes.get(3), 3, Color.BLACK));

        jfrm = new JFrame("Djikstra's Algorithm");
        jfrm.setSize(500, 527);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jfrm.add(this);
        jfrm.setVisible(true);

        ArrayList<Integer> shortestPath = shortestPath(4, connections);
        System.out.println(shortestPath.toString());
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(Connection connection : connections){
            connection.draw(g);
        }
        for(Node node : nodes){
            node.draw(g);
        }

    }

    public static void main(String args[]) throws InterruptedException {
        Dijkstra d = new Dijkstra();
    }

    public ArrayList<Integer> shortestPath(int nodes, ArrayList<Connection> connections) throws InterruptedException {
        minDist = new int[nodes];
        Arrays.fill(minDist, Integer.MAX_VALUE);
        minDist[0] = 0;
        ArrayList<Integer>[] optimalPaths = new ArrayList[nodes];
        int[][] connectionWeights = new int[nodes][nodes];
        HashMap<Integer, ArrayList> connected = new HashMap<>();

        //store connection data in maps and arrays
        for(Connection connection : connections){
            int first = connection.node1.id;
            int second = connection.node2.id;
            if(!connected.containsKey(first)){
                connected.put(first, new ArrayList<>());
            }
            if(!connected.containsKey(second)){
                connected.put(second, new ArrayList<>());
            }
            connected.get(first).add(second);
            connected.get(second).add(first);
            connectionWeights[first][second] = connection.weight;
            connectionWeights[second][first] = connection.weight;
        }

        //add node 0 as first in path
        LinkedList<Integer> bfsQueue = new LinkedList<>();
        bfsQueue.add(0);
        minDist[0] = 0;
        ArrayList<Integer> firstPath = new ArrayList<>();
        firstPath.add(0);
        optimalPaths[0] = firstPath;

        //basic bfs search
        while(!bfsQueue.isEmpty()){
            int currNode = bfsQueue.removeFirst();
            Color temp = this.nodes.get(currNode).c;
            this.nodes.get(currNode).c = Color.GREEN;
            repaint();
            Thread.sleep(500);
            if(currNode == nodes - 1) continue;
            ArrayList<Integer> connectedTo = connected.get(currNode);
            for(int connection : connectedTo){
                Color temp2 = this.nodes.get(connection).c;
                this.nodes.get(connection).c = Color.GREEN;
                for(Connection connect : this.connections){
                    if(connect.node1.val == currNode && connect.node2.val == connection
                    || connect.node1.val == connection && connect.node2.val == currNode){
                        connect.c = Color.GREEN;
                    }
                }
                repaint();
                Thread.sleep(500);
                if(minDist[connection] == -1 ||
                        minDist[currNode] + connectionWeights[currNode][connection] < minDist[connection]){
                    ArrayList<Integer> nextPath = new ArrayList<>(optimalPaths[currNode]);
                    nextPath.add(connection);
                    optimalPaths[connection] = nextPath;
                    bfsQueue.add(connection);
                    minDist[connection] = minDist[currNode] + connectionWeights[currNode][connection];
                }
                this.nodes.get(connection).val = minDist[connection];
                repaint();
                Thread.sleep(500);
                this.nodes.get(connection).c = temp2;
                for(Connection connect : this.connections){
                    connect.c = Color.BLACK;
                }
                repaint();
            }
            this.nodes.get(currNode).c = temp;
            repaint();
        }

        return optimalPaths[optimalPaths.length - 1];

    }

}
