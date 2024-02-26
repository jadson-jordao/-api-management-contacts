package com.contacts.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoResponseDTO {

    private Long idEndereco;
    private String rua;
    private Integer numero;
    private Integer cep;
}
