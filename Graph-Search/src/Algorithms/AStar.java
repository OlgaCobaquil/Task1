/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithms;

import graph.search.Action;
import graph.search.Graph;
import graph.search.IProblemDefinition;
import graph.search.Node;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 *
 * @author olgac
 */
public class AStar implements IProblemDefinition{
    private Graph Graph;
    private ArrayList<Node> FinalPath;
    private ArrayList<Node> Checked;
    
    public AStar(int sizeX, int sizeY, BufferedImage image){
        this.Graph = new Graph(sizeX, sizeY);
        this.Graph.Create();
        this.Graph.fillGraph(image);
        this.Checked = new ArrayList<>();
    }

    public Graph getGraph() {
        return Graph;
    }

    public void setGraph(Graph Graph) {
        this.Graph = Graph;
    }

    public ArrayList<Node> getFinalPath() {
        return FinalPath;
    }

    public void setFinalPath(ArrayList<Node> FinalPath) {
        this.FinalPath = FinalPath;
    }
    
    //resolcion por el alogitmo de A*
    public void Solve(int type) {
        PriorityQueue<Node> waitingNodes = new PriorityQueue<>();

        this.Graph.getInitial().setCost(0);
        waitingNodes.add(this.Graph.getInitial());
        
        while (!waitingNodes.isEmpty()) {
            
            Node current = waitingNodes.poll();

            if (goalTest(current)) {
                this.FinalPath = new ArrayList<>();
                while (!(current.getRoot() == null)) {
                    this.FinalPath.add(current);
                    current = current.getRoot();
                }
                
                this.FinalPath.add(current);
                Collections.reverse(this.FinalPath);
                
                break;
            }
            
            this.Checked.add(current);
                    
            for (Action accion : actions(current)) {
                Node next = result(current, accion);
                
                if (!this.Checked.contains(next))
                    if (!next.isBlocked()) {
                        double cost = stepCost(current, next, accion);

                        if (!waitingNodes.contains(next)|| cost < next.getCost()){
                            next.setRoot(current);
                            next.setCost(cost); 
                            next.setHeuristic(Heuristic(next, this.Graph.getGoals(), type));

                           waitingNodes.add(next);
                        }
                    }
            }
        }
    }

    //Heuristicas
    private double Heuristic(Node current, ArrayList<Node> goals, Integer type) {
       
        double D = 1.0; 
        double minHypotenuse = Math.sqrt(this.Graph.getSizeX() * this.Graph.getSizeX()  + this.Graph.getSizeY() * this.Graph.getSizeY());
        
        Node goal = null;
        for (int i = 0; i < goals.size(); i++) {
            double dx = Math.abs(current.getX() - goals.get(i).getX());
            double dy = Math.abs(current.getY() - goals.get(i).getY());
            double hypotenuse = Math.sqrt(dx * dx + dy * dy);
            if (hypotenuse < minHypotenuse) {
                minHypotenuse = hypotenuse;
                goal = goals.get(i);
            } 
        }
        
        //Manhattan
        double dx = Math.abs(current.getX() - goal.getX());
        double dy = Math.abs(current.getY() - goal.getY());
        
        //BT
        double dx1 = current.getX() - goal.getX();
        double dy1 = current.getY() - goal.getY();
        double dx2 = this.Graph.getInitial().getX() - goal.getX();
        double dy2 = this.Graph.getInitial().getY() - goal.getY();
        
        
        if(type == 1)
            return (D * (dx + dy)); //Manhattan 
        
        return Math.abs(dx1 * dy2 -  dx2 * dy1);// BT
       
    }
    
    private double getDistance(Node from, Node to) {
        if ((from.getX() == to.getX()) || (from.getY() == to.getY()))
            return 1;
        
        int dx = Math.abs(from.getX() - to.getX());
        int dy = Math.abs(from.getY() - to.getY());
        
        return Math.sqrt(dx * dx + dy * dy);
    }
    //implementacion final del framework
    @Override
    public ArrayList<Action> actions(Node node) {
        return this.Graph.getNeighbors(node);
    }

    @Override
    public double stepCost(Node fromNode, Node toNode, Action action) {
        if (fromNode.equals(action.getFrom()) && toNode.equals(action.getTo())) {
           return fromNode.getCost() + this.getDistance(fromNode, toNode);
       }
        
        return 0;
    }

    @Override
    public double pathCost(ArrayList<Node> path) {
        Node nodo = path.get(path.size() - 1);
        return nodo.getCost() + nodo.getHeuristic();
    }

    @Override
    public boolean goalTest(Node goal) {
        return this.Graph.getGoals().contains(goal);
    }

    @Override
    public Node result(Node node, Action action) {
        if(node.equals(action.getFrom())){
            return action.getTo();
        }
        
        return null;
    }
}
