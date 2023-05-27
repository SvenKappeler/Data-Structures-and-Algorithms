package com.skappele;

import java.io.Serializable;

public class Edge implements Serializable {
    private BusinessNode source;
    private BusinessNode destination;
    private double weight;

    public Edge(BusinessNode source, BusinessNode destination, double weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public BusinessNode getSource() {
        return source;
    }

    public double getWeight() {
        return weight;
    }

    public BusinessNode getDestination() {
        return destination;
    }


}