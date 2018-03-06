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
public class Action {
    private Node From;
    private Node To;

    public Action(Node origen, Node destino) {
        this.From = origen;
        this.To = destino;
    }

    public Node getFrom() {
        return From;
    }

    public void setFrom(Node From) {
        this.From = From;
    }

    public Node getTo() {
        return To;
    }

    public void setTo(Node To) {
        this.To = To;
    }

    @Override
    public String toString() {
        return "Accion{" + "From = " + From + ", To = " + To + '}';
    }
}
