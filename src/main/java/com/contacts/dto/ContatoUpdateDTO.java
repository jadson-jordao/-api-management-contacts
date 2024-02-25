package com.contacts.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ContatoUpdateDTO {

    @Size(max = 150, message = "O limite máximo do campo nome é 150 caracters!")
    private String nome;
    @Size(max = 100, message = "O limite máximo do campo email é 100 caracters!")
    @Email(message = "Endereço de e-mail inválido!")
    private String email;
    @Size(max = 15, message = "Telefone não pode possuir mais de 15 caracters!")
    private String telefone;
    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataNascimento;
    @Valid
    private List<EnderecoUpdateDTO> enderecos = new ArrayList<>();

}
