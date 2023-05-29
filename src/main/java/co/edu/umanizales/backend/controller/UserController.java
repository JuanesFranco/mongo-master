package co.edu.umanizales.backend.controller;

import java.util.Optional;

import co.edu.umanizales.backend.model.User;
import co.edu.umanizales.backend.model.UserRepository;
import co.edu.umanizales.backend.model.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
@CrossOrigin(origins="http://localhost:4200")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @PostMapping()
    public ResponseEntity<ResponseDTO> addUser(@RequestBody User user) {
        return new ResponseEntity<>(new ResponseDTO(200, userRepo.save(user), null), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getUserById(@PathVariable String id){
        return new ResponseEntity<>(new ResponseDTO(200, userRepo.findById(id), null), HttpStatus.OK);
    }
    @GetMapping()
    public ResponseEntity<ResponseDTO> getAllUser(){
        return new ResponseEntity<>(new ResponseDTO(200, userRepo.findAll(), null), HttpStatus.OK);
    }
    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable String id) {
        userRepo.deleteById(id);
        return new ResponseEntity<>(new ResponseDTO(200, "Usuario eliminado.", null), HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> updateUser(@PathVariable String id, @RequestBody User user) {
        Optional<User> userOptional = userRepo.findById(id);
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            existingUser.setName(user.getName());
            existingUser.setRollNumber(user.getRollNumber());
            existingUser.setPassword(user.getPassword());
            existingUser.setEmail(user.getEmail());
            userRepo.save(existingUser);
            return new ResponseEntity<>(new ResponseDTO(200, existingUser, null), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDTO(404, null, null), HttpStatus.OK);
        }
    }
}

