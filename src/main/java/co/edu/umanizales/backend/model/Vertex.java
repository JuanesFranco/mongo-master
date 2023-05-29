package co.edu.umanizales.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Vertex {
    private City data;
    private int code;

    @Override
    public String toString() {
        return "Vertice{" + "dato=" + data + ", codigo=" + code + '}';
    }
}
