package com.contacts.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

@Entity
@Table(name = "TB_ENDERECO")
@Data
public class Endereco implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ENDERECO")
    private Long idEndereco;

    @Column(name = "RUA", length = 150)
    private String rua;

    @Column(name = "NUMERO")
    private Integer numero;

    @Column(name = "CEP")
    private Integer cep;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_CONTATO", foreignKey = @ForeignKey(name = "FK_ENDERECO_CONTATO"))
    private Contato contato;

}
