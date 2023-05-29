package co.edu.umanizales.backend.controller;

import co.edu.umanizales.backend.model.User;
import co.edu.umanizales.backend.model.dto.ResponseDTO;
import co.edu.umanizales.backend.model.dto.UserDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class AuthController {
    private final MongoTemplate mongoTemplate;

    public AuthController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    @PostMapping("login")
    public ResponseEntity<ResponseDTO> login(@RequestBody UserDTO userDTO) {
        User user = mongoTemplate.findOne(
                Query.query(Criteria.where("name").is(userDTO.getUsername()).and("password").is(userDTO.getPassword())),
                User.class);
        if (user == null) {
            List<String> errors = new ArrayList<>();
            errors.add("Datos inválidos.");
            return new ResponseEntity<>(new ResponseDTO(409, null, errors),HttpStatus.OK);
        }
        String token = getJWTToken(userDTO.getUsername());
        return  new ResponseEntity(new ResponseDTO(200, token, null), HttpStatus.OK);
    }

    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1200000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }
}
