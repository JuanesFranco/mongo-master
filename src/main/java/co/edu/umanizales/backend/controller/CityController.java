package co.edu.umanizales.backend.controller;

import co.edu.umanizales.backend.model.City;
import co.edu.umanizales.backend.model.CityRepository;
import co.edu.umanizales.backend.model.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/city")

@CrossOrigin(origins="http://localhost:4200")
public class CityController {

    @Autowired
    private CityRepository cityRepo;

    @PostMapping()
    public ResponseEntity<ResponseDTO> addCity(@RequestBody City city) {
        return new ResponseEntity<>(new ResponseDTO(200, cityRepo.save(city), null), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getCityById(@PathVariable String id){
        return new ResponseEntity<>(new ResponseDTO(200, cityRepo.findById(id), null), HttpStatus.OK);
    }
    @GetMapping()
    public ResponseEntity<ResponseDTO> getAllCity(){
        return new ResponseEntity<>(new ResponseDTO(200, cityRepo.findAll(), null), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteCity(@PathVariable String id) {
        cityRepo.deleteById(id);
        return new ResponseEntity<>(new ResponseDTO(200, "Ciudad eliminada", null), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateCity(@PathVariable String id, @RequestBody String name) {
        Optional<City> cityOptional = cityRepo.findById(id);
        if (cityOptional.isPresent()) {
            City existingCity = cityOptional.get();
            existingCity.setName(name);
            cityRepo.save(existingCity);
            return new ResponseEntity<>(new ResponseDTO(200, existingCity, null), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDTO(404, null, null), HttpStatus.OK);
        }
    }
}
