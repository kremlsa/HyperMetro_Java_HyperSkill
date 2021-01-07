import java.util.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MetroGraph
{

    private Queue<Node> queue;
    Map<Integer, Node> stations = new LinkedHashMap<>();
    static ArrayList<Node> nodes=new ArrayList<Node>();
    static class Node
    {
        int data;
        boolean visited;
        List<Node> neighbours;

        Node(int data)
        {
            this.data=data;
            this.neighbours=new ArrayList<>();

        }
        public void addneighbours(Node neighbourNode)
        {
            this.neighbours.add(neighbourNode);
        }
        public List<Node> getNeighbours() {
            return neighbours;
        }
        public void setNeighbours(List<Node> neighbours) {
            this.neighbours = neighbours;
        }
    }

    public MetroGraph()
    {
        queue = new LinkedList<Node>();
    }

   // public void bfs(Node node, Node finish)
   public void bfs(Station start, Station end)
    {
        Node node = stations.get(start.getGraph_id());
        Node finish = stations.get(end.getGraph_id());
        queue.add(node);
        node.visited=true;
        while (!queue.isEmpty())
        {

            Node element=queue.remove();
            System.out.print(element.data + "t");
            List<Node> neighbours=element.getNeighbours();
            for (int i = 0; i < neighbours.size(); i++) {
                Node n=neighbours.get(i);
               // if (n.data == finish.data) break;
                if(n!=null && !n.visited)
                {
                    queue.add(n);
                    n.visited=true;
                }
            }

        }
    }



    public void addStations(Station station) {
        System.out.println("Station Created " + station.getGraph_id());
        stations.put(station.getGraph_id(), new Node(station.getGraph_id()));
    }

    public void addNeighboursStations(Station src, Station dst) {
        System.out.println("Create neighbours " + src.getGraph_id() + "-" + dst.getGraph_id());
        Node tempSrc = stations.get(src.getGraph_id());
        Node tempDst = stations.get(dst.getGraph_id());
        tempSrc.addneighbours(tempDst);
        stations.replace(src.getGraph_id(), tempSrc);
    }

    public static void main(String arg[])
    {

        Node node40 =new Node(40);
        Node node10 =new Node(10);
        Node node20 =new Node(20);
        Node node30 =new Node(30);
        Node node60 =new Node(60);
        Node node50 =new Node(50);
        Node node70 =new Node(70);

        node40.addneighbours(node10);
        node40.addneighbours(node20);
        node10.addneighbours(node30);
        node20.addneighbours(node10);
        node20.addneighbours(node30);
        node20.addneighbours(node60);
        node20.addneighbours(node50);
        node30.addneighbours(node60);
        node60.addneighbours(node70);
        node50.addneighbours(node70);
        System.out.println("The BFS traversal of the graph is ");
        MetroGraph bfsExample = new MetroGraph();
       //bfsExample.bfs(node40, node60);

    }
}