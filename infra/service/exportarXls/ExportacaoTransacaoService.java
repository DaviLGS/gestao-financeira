package financeira.gestao.demo.infra.service.exportarXls;

import financeira.gestao.demo.domain.entities.transacao.TipoTransacao;
import financeira.gestao.demo.domain.entities.transacao.Transacao;
import financeira.gestao.demo.domain.repository.TransacaoRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class ExportacaoTransacaoService {

    private final TransacaoRepository repository;

    public ExportacaoTransacaoService(TransacaoRepository repository) {
        this.repository = repository;
    }

    public byte[] exportarTransacoes(Long userId) {

        List<Transacao> transacoes = repository.findByUsuarioId(userId);

        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Transações");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Data");
            header.createCell(1).setCellValue("Descrição");
            header.createCell(2).setCellValue("Tipo");
            header.createCell(3).setCellValue("Status");
            header.createCell(4).setCellValue("Valor");

            sheet.createFreezePane(0, 1);

            CreationHelper creationHelper = workbook.getCreationHelper();

            CellStyle dateStyle = workbook.createCellStyle();
            dateStyle.setDataFormat(
                    creationHelper
                            .createDataFormat()
                            .getFormat("dd/MM/yyyy HH:mm")
            );

            int rowIdx = 1;
            BigDecimal saldo = BigDecimal.ZERO;

            for (Transacao t : transacoes) {
                Row row = sheet.createRow(rowIdx++);
                Cell dataCell = row.createCell(0);
                dataCell.setCellValue(
                        Date.from(
                                t.getCriadoAs()
                                        .atZone(ZoneId.systemDefault())
                                        .toInstant()
                        )
                );
                dataCell.setCellStyle(dateStyle);

                row.createCell(1).setCellValue(t.getDescricao());
                row.createCell(2).setCellValue(t.getTipo().name());
                row.createCell(3).setCellValue(t.getStatus().name());
                row.createCell(4).setCellValue(t.getValor().doubleValue());

                if (t.getTipo() == TipoTransacao.ENTRADA) {
                    saldo = saldo.add(t.getValor());
                } else {
                    saldo = saldo.subtract(t.getValor());
                }
            }

            Row saldoRow = sheet.createRow(rowIdx + 1);
            saldoRow.createCell(3).setCellValue("SALDO FINAL");
            saldoRow.createCell(4).setCellValue(saldo.doubleValue());

            sheet.setAutoFilter(
                    new CellRangeAddress(
                            0,
                            rowIdx - 1,
                            0,
                            4
                    )
            );

            for (int i = 0; i <= 4; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);

            return out.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar Excel", e);
        }
    }
}
