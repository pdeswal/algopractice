package capillary.p3;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;


public class TestClass {
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
	
	Tree T = G.createTree();
	
	countChildren(T.nodes[0]);
	countRightChildOnly(T);
	markLevelOnNodes(T);
	levelByLevelRightSideTreeSpecialCount(T);
	
	levelByLevelCountEdgeTraversals(T, edges);
	
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
    
    private static void levelByLevelCountEdgeTraversals(Tree T, Map<Edge, Edge> edges){
	//level by level traversal to mark level on nodes
	Queue<Node> queue = new LinkedList<>();
	queue.add(T.nodes[0]);
	while(!queue.isEmpty()){
	    Node node = queue.poll();
	    for(Node child : node.children){
		queue.add(child);
	    }
	    
	    if(node.parent != null){
		//not the root node
		Edge edge = null;
		Edge key1 = new Edge(node.value, node.parent.value);
		edge = edges.get(key1);
		if(edge == null){
		    Edge key2 = new Edge(node.parent.value, node.value);
		    edge = edges.get(key2);
		}
		
		edge.traversals = (node.leftSideTreeSpecialCount * (1 + node.childCount)) + 
			(node.rightSideTreeSpecialCount * (1 + node.childCount)) + 
			((1 + node.childCount)*node.level);
	    }
	}
    }
    
    private static void levelByLevelRightSideTreeSpecialCount(Tree T){
	//level by level traversal to mark level on nodes
	Queue<Node> queue = new LinkedList<>();
	queue.add(T.nodes[0]);
	while(!queue.isEmpty()){
	    Node node = queue.poll();
	    for(Node child : node.children){
		queue.add(child);
	    }
	    if(node.parent != null){
		//not the root node
		Node parent = node.parent;
		node.rightSideTreeSpecialCount = node.rightSideTreeSpecialCount + parent.rightSideTreeSpecialCount;
		node.rightSideTreeSpecialCount = node.rightSideTreeSpecialCount + node.parentRightToThisNodeCount;
		
		
		node.leftSideTreeSpecialCount = node.leftSideTreeSpecialCount + parent.leftSideTreeSpecialCount;
		node.leftSideTreeSpecialCount = node.leftSideTreeSpecialCount + node.parentLeftToThisNodeCount;
		
	    }
	}
    }
    
    static class Graph{
	
	GraphNode[] nodes;
	Graph(int N){
	    nodes = new GraphNode[N];
	    for(int i=0; i< N; i++){
		nodes[i] = new GraphNode(i+1);
	    }
	}
	
	public Tree createTree(){
	    Tree T = new Tree(nodes.length);
	    Queue<GraphNode> q = new LinkedList<>();
	    q.add(nodes[0]);
	    nodes[0].visited = true;
	    while(!q.isEmpty()){
		GraphNode n = q.poll();
		for(GraphNode child : n.neibhours){
		    if(!child.visited){
			child.visited=true;
			q.add(child);
			
			T.addLink(n.value, child.value);
		    }
		}
	    }
	    return T;
	}
	
	public void addLink(int u, int v){
	    nodes[u-1].addNeibhour(nodes[v-1]);
	    nodes[v-1].addNeibhour(nodes[u-1]);
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
	}
    }
    private static void markLevelOnNodes(Tree T){
	//level by level traversal to mark level on nodes
	Queue<Node> queue = new LinkedList<>();
	queue.add(T.nodes[0]);
	T.nodes[0].level = 0;
	while(!queue.isEmpty()){
	    Node node = queue.poll();
	    int level = node.level;
	    int index = 0;
	    for(Node child : node.children){
		child.level = level + 1;
		child.levelIndex = index;
		index++;
		queue.add(child);
	    }
	}
    }
    private static int countChildren(Node n){
	int tempCount = 0;
	for(Node temp : n.children){
	    tempCount += countChildren(temp)+1;
	}
	n.childCount = tempCount;
	return tempCount;
	
    }
    
    private static void countRightChildOnly(Tree T){
	for(int i = 0; i < T.nodes.length; i++){
	    int size = T.nodes[i].children.size();
	    T.nodes[i].childrenArr = new Node[size];
	    //T.nodes[i].rightChilrenCountArr = new int[size];
	    int j=0;
	    for(Node child : T.nodes[i].children){
		T.nodes[i].childrenArr[j] = child;
		j++;
	    }
	    
	    j = T.nodes[i].childrenArr.length -1;
	    int sum = 0;
	    for(;j>=0; j--){
		T.nodes[i].childrenArr[j].parentRightToThisNodeCount = sum;
		sum += T.nodes[i].childrenArr[j].childCount + 1;
	    }
	    
	    sum=0;
	    for(j=0;j< T.nodes[i].childrenArr.length; j++){
		T.nodes[i].childrenArr[j].parentLeftToThisNodeCount = sum;
		sum += T.nodes[i].childrenArr[j].childCount + 1;
	    }
	}
    }
    static class Tree{
	Node[] nodes;
	Tree(int N){
	    nodes = new Node[N];
	    for(int i=0; i< N; i++){
		nodes[i] = new Node(i+1);
	    }
	}
	
	public Node getNode(int name){
	    return nodes[name-1];
	}
	
	public void addLink(int src, int dest){
	    nodes[src-1].addChild(nodes[dest-1]);
	    nodes[dest-1].parent = nodes[src-1];
	}
    }
    
    static class Node{
	int value;
	int childCount;
	int parentRightToThisNodeCount;
	int parentLeftToThisNodeCount;
	int rightSideTreeSpecialCount;
	int leftSideTreeSpecialCount;
	Node[] childrenArr;
	LinkedList<Node> children = new LinkedList<>();
	int level;
	int levelIndex;
	Node parent = null;
	Node(int value){
	    this.value = value;
	}
	
	public void addChild(Node n){
	    this.children.add(n);
	}

	@Override
	public String toString() {
	    return "Node [value=" + value + ", childCount=" + childCount + ", rightChilrenCount=" + parentRightToThisNodeCount + "]";
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
