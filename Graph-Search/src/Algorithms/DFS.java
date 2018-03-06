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
import java.util.Stack;

/**
 *
 * @author olgac
 */
public class DFS implements IProblemDefinition{
    private Graph Graph;
    private ArrayList<Node> FinalPath;
    private ArrayList<Node> Checked;
    
    public DFS(int sizeX, int sizeY, BufferedImage image){
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

    public ArrayList<Node> getChecked() {
        return Checked;
    }

    public void Solve(){
        this.FinalPath = Solve(this.Graph.getInitial(), new ArrayList(), new ArrayList());
    }
    
    private ArrayList<Node> Solve(Node actualNode, ArrayList<Node> path,  ArrayList<Node> shortestPath) {
        path.add(actualNode);
       //si encuentra la solucion 
        if (goalTest(actualNode))
           return path;
        //buscar todos los nodos posibles de visitar hasta que no tenga
        //retorna al no cisitado y dibuja path final
        for (Action accion: actions(actualNode)) {
            Node nextNode = result(actualNode, accion);
            if (!nextNode.isBlocked()) {
                if (!this.Checked.contains(nextNode)) {
                    this.Checked.add(nextNode);
                    
                    if (shortestPath.isEmpty() || path.size() < shortestPath.size()) {
                        ArrayList<Node> newPath = Solve(nextNode, path, shortestPath);
                        if (!newPath.isEmpty())
                            shortestPath = newPath;
                    }
                }
            }
        }
       
        return shortestPath;
    }
    //implementacion del framework
    @Override
    public ArrayList<Action> actions(Node node) {
        //System.out.println(this.Graph.getNeighbors(node));
        return this.Graph.getNeighbors(node);
    }

    @Override
    public double stepCost(Node fromNode, Node toNode, Action action) {
        if(fromNode.equals(action.getFrom()) && toNode.equals(action.getTo())){
            return 1;
        }
        
        return 0;
    }

    @Override
    public double pathCost(ArrayList<Node> path) {
        float cost = 0;
        
        for(int i = 0; i < path.size(); i++){
            Action action = new Action(path.get(i), path.get(i + 1));
            cost += this.stepCost(path.get(i), path.get(i + 1), action);
        }
        
        return cost;
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
