package co.edu.umanizales.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lists {
    private List<City> cities;
    private List<Edge> edges;
}
