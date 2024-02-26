package com.contacts.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoCreateDTO {

    @Size(max = 100, message = "O limite máximo do campo rua é 100 caracters!")
    @NotBlank(message = "Rua é um campo obrigatorio!")
    private String rua;
    private Integer numero;
    @Digits(integer = 8, fraction = 0, message = "CEP deve possuir no maximo 8 digitos!")
    private Integer cep;
}
