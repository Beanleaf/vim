package be.beanleaf.vim.app.services;

import be.beanleaf.vim.domain.entities.InventoryItem;
import be.beanleaf.vim.services.QrService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VimPdfService {

  private final QrService qrService;

  @Autowired
  public VimPdfService(QrService qrService) {
    this.qrService = qrService;
  }

  private void allignCenter(PdfPCell cell) {
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
  }

  public byte[] getQrList(List<InventoryItem> items, int numberOfColumns) {
    try {
      Document document = new Document();
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      PdfWriter.getInstance(document, baos);
      document.open();
      PdfPTable parentTable = new PdfPTable(numberOfColumns);
      for (InventoryItem item : items) {
        PdfPTable subTable = new PdfPTable(1);
        PdfPCell descriptionCell = new PdfPCell(new Phrase(item.getDescription()));
        allignCenter(descriptionCell);
        descriptionCell.setBorder(0);
        subTable.addCell(descriptionCell);

        byte[] bytes = qrService.generateQr(item.getUuid(), "png", 25, 25);
        Image image = Image.getInstance(bytes);
        PdfPCell qrCell = new PdfPCell(image);
        qrCell.setBorder(0);
        allignCenter(qrCell);
        subTable.addCell(qrCell);
        parentTable.addCell(subTable);
      }
      addEmptyCells(parentTable, items.size(), numberOfColumns);
      document.add(parentTable);
      document.close();
      return baos.toByteArray();
    } catch (DocumentException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void addEmptyCells(PdfPTable parentTable, int size, int numberOfColumns) {
    int rest = size % numberOfColumns;
    if (rest > 0) {
      for (int i = 1; i <= rest; i++) {
        parentTable.addCell("#");
      }
    }
  }

}
