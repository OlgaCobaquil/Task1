/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.search;

import java.util.ArrayList;

/**
 *
 * @author olgac
 */
public interface IProblemDefinition {
    public ArrayList<Action> actions(Node node);
    public double stepCost(Node fromNode, Node toNode, Action action); 
    public double pathCost(ArrayList<Node> path);
    public boolean goalTest(Node goal);
    public Node result(Node node, Action action);       
}
