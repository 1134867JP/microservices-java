package br.edu.atitus.paradigma.cambio_service.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cambio")
public class CambioEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 3, nullable = false)
    private String origem;

    @Column(length = 3, nullable = false)
    private String destino;

    @Column(nullable = false, precision = 15, scale = 5)
    private double fator;

    @Transient
    private String ambiente;

    @Transient
    private double valorConvertido;
}
