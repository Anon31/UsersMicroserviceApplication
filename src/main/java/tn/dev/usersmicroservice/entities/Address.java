package tn.dev.usersmicroservice.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private Integer number;
    private String street;
    private String city;
    private Integer zipCode;
    private String state;
}
