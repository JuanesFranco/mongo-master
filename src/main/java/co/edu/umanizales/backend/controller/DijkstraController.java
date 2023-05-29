package co.edu.umanizales.backend.controller;

import co.edu.umanizales.backend.model.*;
import co.edu.umanizales.backend.model.Lists;
import co.edu.umanizales.backend.model.dto.ResponseDTO;
import co.edu.umanizales.backend.model.exception.GraphException;
import co.edu.umanizales.backend.model.dijkstra.Dijkstra;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class DijkstraController {

    @PostMapping("/dijkstra/{origin}/{destiny}")
    public ResponseEntity<ResponseDTO> dijkstra(@PathVariable short origin,
                                                @PathVariable short destiny,
                                                @RequestBody Lists lists) throws GraphException {
        Graph graph = new UndirectedGraph();

        for (City city: lists.getCities()) {
            graph.addVertex(city);
        }
        for (Edge edge: lists.getEdges()) {
            graph.addEdge(edge);
        }
        Dijkstra dijkstra = new Dijkstra(origin, destiny, graph);
        return new ResponseEntity<>(new ResponseDTO(200, dijkstra.calcularDjikstra(), null), HttpStatus.OK);
    }
}
