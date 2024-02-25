package com.contacts.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;



import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EnderecoDTO {

    private Long idEndereco;
    @NotBlank(message = "Rua é um campo obrigatorio!")
    private String rua;
    private Integer numero;

    @Digits(integer = 8, fraction = 0, message = "CEP deve possuir 8 digitos!")
    private Integer cep;
}
