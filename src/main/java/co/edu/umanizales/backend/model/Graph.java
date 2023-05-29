package co.edu.umanizales.backend.model;


import co.edu.umanizales.backend.model.exception.GraphException;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public abstract class Graph implements Serializable {
    private List<Vertex> vertex;
    private List<Edge> edges;
    private short consecutive;

    public Graph() {
        this.vertex= new ArrayList<>();
        this.edges= new ArrayList<>();
    }

    public void addVertex(City data)
    {
        Vertex newVertex= new Vertex(data, ++consecutive);
        vertex.add(newVertex);
    }
    public void addEdge(Edge edge) throws GraphException
    {
        if(validateExistingEdge(edge))
        {
            throw new GraphException("Ya existe la arista");
        }
        edges.add(edge);
    }

    public abstract boolean validateExistingEdge(Edge arista);

    public abstract List<Edge> getAdjacencies(int origen);


}