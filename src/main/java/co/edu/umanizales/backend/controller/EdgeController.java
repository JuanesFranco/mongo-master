package co.edu.umanizales.backend.controller;

import co.edu.umanizales.backend.model.Edge;
import co.edu.umanizales.backend.model.EdgeRepository;
import co.edu.umanizales.backend.model.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class EdgeController {

    @Autowired
    private EdgeRepository edgeRepo;

    @PostMapping("/addedge")
    public ResponseEntity<ResponseDTO> addEdge(@RequestBody Edge edge) {
        return new ResponseEntity<>(new ResponseDTO(200, edgeRepo.save(edge), null), HttpStatus.OK);
    }
    @GetMapping("/edge/{id}")
    public ResponseEntity<ResponseDTO> getEdgeById(@PathVariable String id){
        return new ResponseEntity<>(new ResponseDTO(200, edgeRepo.findById(id), null), HttpStatus.OK);
    }
    @GetMapping("/edge")
    public ResponseEntity<ResponseDTO> getAllEdge(){
        return new ResponseEntity<>(new ResponseDTO(200, edgeRepo.findAll(), null), HttpStatus.OK);
    }
    @DeleteMapping("/edge/{id}")
    public ResponseEntity<ResponseDTO> deleteCity(@PathVariable String id) {
        edgeRepo.deleteById(id);
        return new ResponseEntity<>(new ResponseDTO(200, "Arista eliminada", null), HttpStatus.OK);
    }

    @PutMapping("/update/edge/{id}")
    public ResponseEntity<ResponseDTO> updateUser(@PathVariable String id, @RequestBody Edge edge) {
        Optional<Edge> edgeOptional = edgeRepo.findById(id);
        if (edgeOptional.isPresent()) {
            Edge existingEdge = edgeOptional.get();
            existingEdge.setOrigin(edge.getOrigin());
            existingEdge.setWeight(edge.getWeight());
            existingEdge.setDestiny(edge.getDestiny());
            edgeRepo.save(existingEdge);
            return new ResponseEntity<>(new ResponseDTO(200, existingEdge, null), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDTO(404, null, null), HttpStatus.OK);
        }
    }
}
