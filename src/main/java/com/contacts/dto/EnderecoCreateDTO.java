package com.contacts.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoCreateDTO {

    @NotBlank(message = "Rua é um campo obrigatorio!")
    private String rua;
    private Integer numero;
    @Digits(integer = 8, fraction = 0, message = "CEP deve possuir 8 digitos!")
    private Integer cep;
}
