package com.contacts.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoUpdateDTO {

    @NotNull(message = "Identificador do endereço é um campo obrigatório na atualização!")
    private Long idEndereco;
    @Size(max = 100, message = "O limite máximo do campo rua é 100 caracters!")
    private String rua;
    private Integer numero;
    @Digits(integer = 8, fraction = 0, message = "CEP deve possuir 8 digitos!")
    private Integer cep;
}
