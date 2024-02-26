package com.contacts.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "TB_CONTATO")
@AllArgsConstructor
@Builder
public class Contato implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONTATO")
    private Long idContato;

    @Column(name = "NOME", length = 150)
    private String nome;

    @Column(name = "EMAIL", length = 100)
    private String email;

    @Column(name = "TELEFONE", length = 15)
    private String telefone;

    @Column(name = "DATA_NASCIMENTO")
    private Date dataNascimento;

    @OneToMany(mappedBy = "contato", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Endereco> enderecos = new ArrayList<>();;

    public Contato() {

    }

}
