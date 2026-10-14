package br.com.edu.uninter.monitoria.service;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ImageCompressorService {

    private static final long MAX_SIZE =
            1_000_000; // 1 MB

    private static final int MAX_WIDTH = 1600;
    private static final int MAX_HEIGHT = 1600;

    public byte[] compress(MultipartFile arquivo)
            throws IOException {

        validateFormat(arquivo);

        BufferedImage image = ImageIO.read(
                arquivo.getInputStream()
        );

        if (image == null) {
            throw new IllegalArgumentException(
                    "O arquivo enviado não é uma imagem válida."
            );
        }

        double escala = Math.min(
                1.0,
                Math.min(
                        (double) MAX_WIDTH / image.getWidth(),
                        (double) MAX_HEIGHT / image.getHeight()
                )
        );

        int widht = Math.max(
                1, (int) (image.getWidth() * escala)
        );

        int height = Math.max(
                1, (int) (image.getHeight() * escala)
        );

        BufferedImage rgb = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );

        var graphics = rgb.createGraphics();
        graphics.setColor(java.awt.Color.WHITE);
        graphics.fillRect(
                0, 0, image.getWidth(), image.getHeight()
        );
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();

        for (float quality = 0.85f;
             quality >= 0.50f;
             quality -= 0.10f) {

            ByteArrayOutputStream saida =
                    new ByteArrayOutputStream();

            Thumbnails.of(rgb)
                    .size(widht, height)
                    .outputFormat("jpg")
                    .outputQuality(quality)
                    .toOutputStream(saida);

            byte[] result = saida.toByteArray();

            if (result.length <= MAX_SIZE) {
                return result;
            }
        }

        throw new IllegalArgumentException(
                "Não foi possível reduzir a imagem para "
                        + "aproximadamente 1 MB com qualidade aceitável."
        );
    }

    private void validateFormat(MultipartFile file) {
        String tipo = file.getContentType();

        if (tipo == null ||
                !(tipo.equals("image/jpeg")
                        || tipo.equals("image/png"))) {
            throw new IllegalArgumentException(
                    "Envie uma imagem JPEG ou PNG."
            );
        }

        if (file.isEmpty()) {
            throw new IllegalArgumentException(
                    "O arquivo está vazio."
            );
        }
    }
}
