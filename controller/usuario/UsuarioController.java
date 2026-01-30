package financeira.gestao.demo.controller.usuario;

import financeira.gestao.demo.domain.usuario.Usuario;
import financeira.gestao.demo.application.dto.request.UsuarioRequest;
import financeira.gestao.demo.application.dto.response.UsuarioResponse;
import financeira.gestao.demo.service.usuarioService.UsuarioImportService;
import financeira.gestao.demo.service.usuarioService.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioImportService usuarioImportService;

    public UsuarioController(UsuarioService usuarioService, UsuarioImportService usuarioImportService) {
        this.usuarioService = usuarioService;
        this.usuarioImportService = usuarioImportService;
    }

    @PostMapping
    public ResponseEntity<Void> criar(@RequestBody @Valid UsuarioRequest request) {
        Usuario usuario = new Usuario();
        usuario.setEmail(request.getEmail());
        usuario.setSenha(request.getSenha());
        usuario.setRole(request.getRole());

        usuarioService.salvar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<UsuarioResponse> listar() {
        return usuarioService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid UsuarioRequest request
    ) {
        return ResponseEntity.ok(usuarioService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        usuarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }
        @PostMapping("/importar")
        public String importar(@RequestParam("file") MultipartFile file) throws Exception {
            usuarioImportService.importarUsuarios(file);
            return "Usuários importados com sucesso!";
        }

}
