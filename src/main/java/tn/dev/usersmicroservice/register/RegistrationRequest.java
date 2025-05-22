package tn.dev.usersmicroservice.register;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.dev.usersmicroservice.dtos.AddressDTO;

import java.time.LocalDate;

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
    // This @JsonFormat line is only used to deserialize (read) the JSON in the POST.
    // Without it, Jackson will not be able to parse "15/05/2002" into a LocalDate.
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birthDate;
    private AddressDTO billingAddress;
    private AddressDTO deliveryAddress;
}
