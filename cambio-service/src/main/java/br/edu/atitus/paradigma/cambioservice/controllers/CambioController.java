package br.edu.atitus.paradigma.cambio_service.controller;

import br.edu.atitus.paradigma.cambio_service.entity.CambioEntity;
import br.edu.atitus.paradigma.cambio_service.repository.CambioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@RestController
@RequestMapping("cambio-service")
public class CambioController {

    private final CambioRepository cambioRepository;

    @Value("${spring.application.name}")
    private String ambiente;

    public CambioController(CambioRepository cambioRepository) {
        this.cambioRepository = cambioRepository;
    }

    @GetMapping("/{valor}/{origem}/{destino}")
    public CambioEntity getCambio(@PathVariable BigDecimal valor,
                                  @PathVariable String origem,
                                  @PathVariable String destino) {

        Optional<CambioEntity> cambioEntityOptional = cambioRepository.findByOrigemAndDestino(origem, destino);

        if (!cambioEntityOptional.isPresent()) {
            throw new RuntimeException("Câmbio não encontrado.");
        }

        CambioEntity cambioEntity = cambioEntityOptional.get();
        BigDecimal fator = BigDecimal.valueOf(cambioEntity.getFator());
        BigDecimal valorConvertido = valor.multiply(fator).setScale(2, RoundingMode.CEILING);

        cambioEntity.setValorConvertido(valorConvertido.doubleValue());
        cambioEntity.setAmbiente(ambiente);

        return cambioEntity;
    }
}
