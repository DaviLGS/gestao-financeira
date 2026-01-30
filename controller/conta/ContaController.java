package financeira.gestao.demo.controller.conta;

import financeira.gestao.demo.application.dto.response.SaldoResponse;
import financeira.gestao.demo.infra.service.saldo.SaldoBancarioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/conta")
public class ContaController {

    private final SaldoBancarioService saldoService;

    public ContaController(SaldoBancarioService saldoService) {
        this.saldoService = saldoService;
    }

    @GetMapping("/saldo")
    public SaldoResponse consultarSaldo(
            @RequestParam Long userId
    ) {
        return saldoService.buscarSaldo(userId);
    }
}
