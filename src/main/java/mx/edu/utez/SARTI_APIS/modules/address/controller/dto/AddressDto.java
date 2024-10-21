package mx.edu.utez.SARTI_APIS.modules.address.controller.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.SARTI_APIS.modules.address.model.Address;
import mx.edu.utez.SARTI_APIS.modules.address.model.AddressTypes;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddressDto {

    @NotNull(groups = Update.class)
    private Long id;

    @NotBlank(message = "El país es obligatorio", groups = {Create.class, Update.class})
    @Size(max = 100, message = "El país no debe exceder los 100 caracteres", groups = {Create.class, Update.class})
    private String country;

    @NotBlank(message = "El estado es obligatorio", groups = {Create.class, Update.class})
    @Size(max = 100, message = "El estado no debe exceder los 100 caracteres", groups = {Create.class, Update.class})
    private String state;

    @NotBlank(message = "La ciudad es obligatoria", groups = {Create.class, Update.class})
    @Size(max = 100, message = "La ciudad no debe exceder los 100 caracteres", groups = {Create.class, Update.class})
    private String city;

    @NotBlank(message = "La localidad es obligatoria", groups = {Create.class, Update.class})
    @Size(max = 100, message = "La localidad no debe exceder los 100 caracteres", groups = {Create.class, Update.class})
    private String locality;

    @NotBlank(message = "La colonia es obligatoria", groups = {Create.class, Update.class})
    @Size(max = 100, message = "La colonia no debe exceder los 100 caracteres", groups = {Create.class, Update.class})
    private String colony;

    @NotBlank(message = "La calle es obligatoria", groups = {Create.class, Update.class})
    @Size(max = 100, message = "La calle no debe exceder los 100 caracteres", groups = {Create.class, Update.class})
    private String street;

    @NotNull(message = "El código postal es obligatorio", groups = {Create.class, Update.class})
    @Digits(integer = 5, fraction = 0, message = "El código postal debe tener 5 dígitos", groups = {Create.class, Update.class})
    private Integer zipCode;

    @NotBlank(message = "El número exterior es obligatorio", groups = {Create.class, Update.class})
    @Size(max = 5, message = "El número exterior no debe exceder los 5 caracteres", groups = {Create.class, Update.class})
    private String externalNumber;

    @NotBlank(message = "El número interior es obligatorio", groups = {Create.class, Update.class})
    @Size(max = 5, message = "El número interior no debe exceder los 5 caracteres", groups = {Create.class, Update.class})
    private String internalNumber;

    @NotBlank(message = "La referencia es obligatoria", groups = {Create.class, Update.class})
    @Size(max = 100, message = "La referencia no debe exceder los 100 caracteres", groups = {Create.class, Update.class})
    private String referenceNear;

    @NotNull(message = "El tipo de dirección es obligatorio", groups = {Create.class, Update.class})
    private AddressTypes addressType;

    public interface Create {
    }

    public interface Update {
    }

    public Address getAddressEntity() {
        return new Address(
                getId(),
                getCountry(),
                getState(),
                getCity(),
                getLocality(),
                getColony(),
                getStreet(),
                getZipCode(),
                getExternalNumber(),
                getInternalNumber(),
                getReferenceNear(),
                getAddressType(),
                null,
                null,
                null,
                null,
                null
        );
    }
}
