package com.contacts.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@Builder
@Table(name = "TB_ENDERECO")
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

    @ManyToOne(targetEntity = Contato.class)
    @JoinColumn(name = "ID_CONTATO", nullable = false, foreignKey = @ForeignKey(name = "FK_ENDERECO_CONTATO"))
    private Contato contato;

    public Endereco() {

    }
}
