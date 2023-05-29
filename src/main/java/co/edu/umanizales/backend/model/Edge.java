package co.edu.umanizales.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "edges")
@Data
@AllArgsConstructor
public class Edge implements Serializable {
    @Id
    private String edgeId;
    private int origin;
    private int destiny;
    private short weight;

    @Override
    public String toString() {
        return "Arista{" + "origen=" + origin + ", destino=" + destiny + ", peso=" + weight + '}';
    }
    
}
