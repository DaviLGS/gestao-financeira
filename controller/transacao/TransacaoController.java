package financeira.gestao.demo.controller.transacao;

import financeira.gestao.demo.application.dto.request.TransacaoRequest;
import financeira.gestao.demo.application.dto.response.TransacaoResponse;
import financeira.gestao.demo.infra.seguranca.UsuarioDetalhes;
import financeira.gestao.demo.service.cambio.CambioService;
import financeira.gestao.demo.service.exportarXls.ExportacaoTransacaoService;
import financeira.gestao.demo.service.transacao.transacaoService.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;
    private final ExportacaoTransacaoService exportacaoService;
    private final CambioService cambioService;

    public TransacaoController(
            TransacaoService transacaoService,
            ExportacaoTransacaoService exportacaoService,
            CambioService cambioService
    ) {
        this.transacaoService = transacaoService;
        this.exportacaoService = exportacaoService;
        this.cambioService = cambioService;
    }

    @PostMapping
    public ResponseEntity<TransacaoResponse> criar(
            @RequestBody @Valid TransacaoRequest request,
            Authentication authentication
    ) {
        UsuarioDetalhes usuario =
                (UsuarioDetalhes) authentication.getPrincipal();

        if (request.getMoeda() == null) {
            request.setMoeda("BRL");
        }

        if (request.getAtualizarSaldo() == null) {
            request.setAtualizarSaldo(true);
        }

        TransacaoResponse response =
                transacaoService.executar(request, usuario.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<TransacaoResponse>> listarPorPeriodo(
            @RequestParam LocalDateTime inicio,
            @RequestParam LocalDateTime fim,
            Authentication authentication
    ) {
        UsuarioDetalhes usuario =
                (UsuarioDetalhes) authentication.getPrincipal();

        List<TransacaoResponse> transacoes =
                transacaoService.listarPorPeriodo(
                        usuario.getId(), inicio, fim
                );

        return ResponseEntity.ok(transacoes);
    }

    @GetMapping("/exportar")
    public ResponseEntity<byte[]> exportar(Authentication authentication) {

        UsuarioDetalhes usuario =
                (UsuarioDetalhes) authentication.getPrincipal();

        byte[] arquivo =
                exportacaoService.exportarTransacoes(usuario.getId());

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"transacoes.xlsx\""
                )
                .contentType(
                        MediaType.parseMediaType(
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                        )
                )
                .body(arquivo);
    }
/*
    @PostMapping("/converter")
    public ResponseEntity<BigDecimal> converter(@RequestBody ConverterRequest request) {
        BigDecimal valorConvertido = cambioService.converterParaBRL(
                request.getValor(),
                request.getMoeda()
        );

        return ResponseEntity.ok(valorConvertido);
    }*/
}
