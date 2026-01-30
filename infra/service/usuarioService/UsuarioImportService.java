package financeira.gestao.demo.infra.service.usuarioService;

import financeira.gestao.demo.domain.entities.usuario.Role;
import financeira.gestao.demo.domain.entities.usuario.Usuario;
import financeira.gestao.demo.domain.repository.UsuarioRepository;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioImportService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioImportService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void importarUsuarios(MultipartFile file) throws Exception {

        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        List<Usuario> usuarios = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            String email = formatter.formatCellValue(row.getCell(0));
            String senha = formatter.formatCellValue(row.getCell(1));

            Usuario u = new Usuario();
            u.setEmail(email);
            u.setSenha(senha);
            u.setRole(Role.USUARIO);
            u.setSaldo(BigDecimal.ZERO);

            usuarios.add(u);
        }

        usuarioRepository.saveAll(usuarios);
        workbook.close();
    }
}
