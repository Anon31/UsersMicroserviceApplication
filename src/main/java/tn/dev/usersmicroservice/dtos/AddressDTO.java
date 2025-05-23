package tn.dev.usersmicroservice.dtos;

import lombok.Data;

@Data
public class AddressDTO {
    private String number;
    private String street;
    private String city;
    private String zipCode;
    private String state;
}

