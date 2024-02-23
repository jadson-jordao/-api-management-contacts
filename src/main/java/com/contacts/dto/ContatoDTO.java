package com.contacts.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ContatoDTO {

    private Long idContato;
    private String nome;
    private String email;
    private String telefone;
    private Date dataNascimento;
    private List<EnderecoDTO> enderecos;
}
