package financeira.gestao.demo.controller.cambio;

import financeira.gestao.demo.service.cambio.CambioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cambio")
public class CambioController {

    private final CambioService cambioService;

    public CambioController(CambioService cambioService) {
        this.cambioService = cambioService;
    }

    @GetMapping("/converter")
    public ResponseEntity<BigDecimal> converter(
            @RequestParam BigDecimal valor,
            @RequestParam String moeda
    ) {

        BigDecimal valorConvertido = cambioService.converterParaBRL(valor, moeda);

        return ResponseEntity.ok(valorConvertido);
    }
}
