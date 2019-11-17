package be.vizit.vim.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class QrService {

  byte[] generateQr(String input, String imageType, int width, int height) {
    if (StringUtils.isEmpty(input)) {
      throw new IllegalArgumentException("The input while generating the QR code was not set.");
    }

    try {
      QRCodeWriter qrCodeWriter = new QRCodeWriter();
      BitMatrix bitMatrix = qrCodeWriter.encode(input, BarcodeFormat.QR_CODE, width, height);
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      MatrixToImageWriter.writeToStream(bitMatrix, imageType.toUpperCase(), outputStream);
      return outputStream.toByteArray();
    } catch (IOException | WriterException exc) {
      throw new RuntimeException("An exception occured while rendering the QR code", exc);
    }
  }

  public String getQrPngSrc(String input, int width, int height) {
    String imageString = Base64.getEncoder()
        .encodeToString(generateQr(input, "PNG", width, height));
    return StringUtils.join("data:image/png;base64,", imageString);
  }
}
