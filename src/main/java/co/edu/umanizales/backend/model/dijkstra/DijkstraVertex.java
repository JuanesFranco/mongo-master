package co.edu.umanizales.backend.model.dijkstra;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class DijkstraVertex implements Serializable{

    private int code;
    private DijkstraVertex before;
    private short weight;
    private boolean check;

    public DijkstraVertex(int code, DijkstraVertex before, short peso) {
        this.code = code;
        this.before = before;
        this.weight = peso;
    }
}