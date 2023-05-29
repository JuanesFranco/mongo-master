package co.edu.umanizales.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cities")
@Data
@AllArgsConstructor
public class City {
    @Id
    private String cityId;
    private String name;
}
