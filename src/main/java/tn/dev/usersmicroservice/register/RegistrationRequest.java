package tn.dev.usersmicroservice.register;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.dev.usersmicroservice.dtos.AddressDTO;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;
    private String phone;
    private OffsetDateTime birthDate;
    private AddressDTO billingAddress;
    private AddressDTO deliveryAddress;
}
