/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.search;

/**
 *
 * @author olgac
 */
public class Node implements Comparable<Node>{
    private Node root;
    private int x;
    private int y;
    private Boolean Blocked;
    private Graph Graph;
    private double Cost;
    private double Heuristic;
    
    public Node(int x, int y, Graph graph) {
        this.x = x;
        this.y = y;
        this.Blocked = false;
        this.Graph = graph;
    }
    //método para comparar igualdad entre nodos
    public boolean equals(Node nodo) {
        return (this.x == nodo.x) && (this.y == nodo.y);
    }
    
    public boolean isBlocked() {
        return Blocked;
    }

    public void setBlocked() {
        this.Blocked = true;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public Graph getGrafo() {
        return Graph;
    }

    public void setGraph(Graph graph) {
        this.Graph = graph;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getCost() {
        return Cost;
    }

    public void setCost(double cost) {
        this.Cost = cost;
    }

    public double getHeuristic() {
        return Heuristic;
    }

    public void setHeuristic(double heuristic) {
        this.Heuristic = heuristic;
    }

    @Override
    public String toString()
    {
        return "("+this.x +" ," + this.y +")";
    }

    @Override
    public int compareTo(Node other) {
        double distanceToGoal = this.getCost() + this.getHeuristic();
        double otherDistanceFromGoal = other.getCost() + other.getHeuristic();
        
        if (distanceToGoal < otherDistanceFromGoal)
                return -1;
        
        if (distanceToGoal > otherDistanceFromGoal)
                return 1;
        
        return 0;
    }
}
