package capillary.p3brute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;



public class TestClass {
    //Trying stupid bruteforce
    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	
	int N = sc.nextInt();
	int TWO = sc.nextInt();
	int[] costs = new int[N];
	
	Graph G = new Graph(N+1);
	
	Map<Edge, Edge> edges = new HashMap<>(N * 2);
	for(int i=0; i< N; i++){
	    int u = sc.nextInt();
	    int v = sc.nextInt();
	    
	    G.addLink(u, v);
	    Edge edge = new Edge(u, v);
	    edges.put(edge, edge);
	}
	
	for(int i=0; i< N; i++){
	    costs[i] = sc.nextInt();
	}
	
	for(int i=1; i<= N+1; i++){
	    for(int j=i+1; j <= N+1; j++){
		//find path between i and j and increament each edge in path
		List<GraphNode> path = dfs(G, i, j);
		
		if(path.size() >= 2){
		    GraphNode node = path.get(0);
		    
		    for(int k=1; k < path.size(); k++){
			GraphNode nextNode = path.get(k);
			
			Edge edge = null;
			Edge key1 = new Edge(node.value, nextNode.value);
			edge = edges.get(key1);
			if(edge == null){
			    Edge key2 = new Edge(nextNode.value, node.value);
			    edge = edges.get(key2);
			}
			
			edge.traversals++;
			node = nextNode;
		    }
		    
		}
		
	    }
	}
	
	Set<Edge> sortedEdgesSet = new TreeSet<>(edges.keySet());
	
	Arrays.sort(costs);
	
	for(Edge edge : edges.keySet()){
	    System.out.print(edge + " ");
	}
	System.out.println("");
	for(Edge edge : sortedEdgesSet){
	    System.out.print(edge + " ");
	}
	System.out.println("");
	for(int i=0; i< N; i++){
	    System.out.print(costs[i] + " ");
	}
	
	long maxToll = 0;
	int index = 0;
	for(Edge edge : sortedEdgesSet){
	    maxToll += (edge.traversals * costs[index]);
	    index++;
	}
	System.out.println("");
	
	
	System.out.println(maxToll);
	sc.close();
    }
    
    private static List<GraphNode> dfs(Graph G, int src, int dest){
	
	List<GraphNode> path = new ArrayList<>();
	Stack<GraphNode> stack = new Stack<>();
	Map<GraphNode, GraphNode> parentMap = new HashMap<>();
	for(int i=0; i<G.nodes.length; i++){
	    G.nodes[i].visited = false;
	}
	stack.push(G.nodes[src-1]);
	G.nodes[src-1].visited = true;
	
	while(!stack.isEmpty()){
	    GraphNode node = stack.pop();
	    if(node.value == dest){
		//values in the stack will give path at this point
		path.add(node);
		GraphNode parent = parentMap.get(node);
		while(parent != null){
		    path.add(parent);
		    parent = parentMap.get(parent);
		}
		break;
	    }
	    for(GraphNode neibhour : node.neibhours){
		if(!neibhour.visited){
		    parentMap.put(neibhour, node);
		    stack.push(neibhour);
		    neibhour.visited = true;
		}
	    }
	}
	
	
	return path;
    }
    

    static class Graph{
	
	GraphNode[] nodes;
	Graph(int N){
	    nodes = new GraphNode[N];
	    for(int i=0; i< N; i++){
		nodes[i] = new GraphNode(i+1);
	    }
	}
	
	public void addLink(int u, int v){
	    nodes[u-1].addNeibhour(nodes[v-1]);
	    nodes[v-1].addNeibhour(nodes[u-1]);
	}
	
    }
    static class GraphNode{
	    int value;
	    LinkedList<GraphNode> neibhours = new LinkedList<>();
	    boolean visited = false;
	    
	    GraphNode(int value){
		this.value = value;
	    }
	    public void addNeibhour(GraphNode gn){
		neibhours.add(gn);
	    }
	    @Override
	    public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
		return result;
	    }
	    @Override
	    public boolean equals(Object obj) {
		if (this == obj)
		    return true;
		if (obj == null)
		    return false;
		if (getClass() != obj.getClass())
		    return false;
		GraphNode other = (GraphNode) obj;
		if (value != other.value)
		    return false;
		return true;
	    }
	    
	}
    
    static class Edge implements Comparable<Edge>{
	int u;
	int v;
	int traversals;
	public Edge(int u, int v) {
	    super();
	    this.u = u;
	    this.v = v;
	}
	
	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + u;
	    result = prime * result + v;
	    return result;
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj)
		return true;
	    if (obj == null)
		return false;
	    if (getClass() != obj.getClass())
		return false;
	    Edge other = (Edge) obj;
	    if (u != other.u)
		return false;
	    if (v != other.v)
		return false;
	    return true;
	}

	@Override
	public String toString() {
	    return "Edge [u=" + u + ", v=" + v + ", traversals=" + traversals + "]";
	}
	@Override
	public int compareTo(Edge o) {
	    int result = o.traversals-this.traversals;
	    if(result == 0){
		result = o.u - this.u;
	    }
	    if(result == 0){
		result = o.v - this.v;
	    }
	    return result * -1;
	}
	
    }
    
}
