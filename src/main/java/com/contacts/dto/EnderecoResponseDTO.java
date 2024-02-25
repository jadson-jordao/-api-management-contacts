package com.contacts.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoResponseDTO {

    private Long idEndereco;
    private String rua;
    private Integer numero;
    private Integer cep;
}
