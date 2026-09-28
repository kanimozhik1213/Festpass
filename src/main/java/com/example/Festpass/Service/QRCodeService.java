package com.example.Festpass.Service;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Service
public class QRCodeService {

    public String generateQRCode(String ticketCode) {

        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();

            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.MARGIN, 1);

            BitMatrix bitMatrix = qrCodeWriter.encode(
                    ticketCode,
                    BarcodeFormat.QR_CODE,
                    250,
                    250,
                    hints
            );

            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

            MatrixToImageWriter.writeToStream(
                    bitMatrix,
                    "PNG",
                    outputStream
            );

            String base64Image = Base64.getEncoder()
                    .encodeToString(outputStream.toByteArray());

            return "data:image/png;base64," + base64Image;

        } catch (WriterException | java.io.IOException exception) {
            throw new RuntimeException("Could not generate QR code");
        }
    }
}