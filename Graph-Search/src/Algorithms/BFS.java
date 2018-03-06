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
import java.util.LinkedList;

/**
 *
 * @author olgac
 */
public class BFS implements IProblemDefinition{
    private Graph Graph;
    private ArrayList<Node> FinalPath;
    private ArrayList<Node> Checked;
    
    /**
     *
     * @param sizeX
     * @param size
     */
    public BFS(int sizeX, int sizeY, BufferedImage image){
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

    public void setChecked(ArrayList<Node> Checked) {
        this.Checked = Checked;
    }
    
    public void Solve() {
        //array para la frontera
        LinkedList<ArrayList<Node>> boundary = new LinkedList();
        ArrayList initial = new ArrayList();
        initial.add(this.Graph.getInitial());
        boundary.add(initial);
        
        while (!boundary.isEmpty()) {
            //sacar el primer elemento de boundary y elimina de la lista 
            ArrayList<Node> path = boundary.poll();
            //obtieje el tamaño del nodo que acaban de sacar 
            Node last = path.get(path.size()-1);
            //System.out.println("last " +last);
           
            if (this.Checked.contains(last)) {
                continue;
            }
            this.Checked.add(last);
            
            
            if (goalTest(last)) { 
                for(int i=0; i<path.size(); i++){
                    path.get(i).setCost(i);
                }
                
                this.FinalPath = path;
                break;
            }
            // para las acciones verificar si no es un nodo bloqueado y seguir con el algoritmo
            for (Action accion : actions(last)) {
                Node adyacente = accion.getTo();
                
                if (!adyacente.isBlocked()) {
                    ArrayList<Node> newPath = new ArrayList();
                    newPath.addAll(path);
                    Node newNode = result(last, accion);
                    newPath.add(newNode);
                    boundary.add(newPath);
                }
            }
        }
    }
    
    //implementacion del framework

    @Override
    public ArrayList<Action> actions(Node node) {
        System.out.println(this.Graph.getNeighbors(node));
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
        //System.out.println("Costo" + cost);
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
