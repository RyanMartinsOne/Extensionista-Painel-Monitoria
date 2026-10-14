package br.com.edu.uninter.monitoria.service;

import br.com.edu.uninter.monitoria.model.Encontro;
import br.com.edu.uninter.monitoria.model.MonitoriaImage;
import br.com.edu.uninter.monitoria.repository.EncontroRepository;
import br.com.edu.uninter.monitoria.repository.MonitoriaImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class MonitoriaImageService {

    private static final String IMAGE_TYPE = "image/jpeg";

    private final MonitoriaImageRepository imageRepository;
    private final EncontroRepository encontroRepository;
    private final ImageCompressorService compressor;
    private final OciObjectStorageService storage;

    public MonitoriaImageService(
            MonitoriaImageRepository imageRepository,
            EncontroRepository encontroRepository,
            ImageCompressorService compressor,
            OciObjectStorageService storage
    ) {
        this.imageRepository = imageRepository;
        this.encontroRepository = encontroRepository;
        this.compressor = compressor;
        this.storage = storage;
    }

    @Transactional
    public MonitoriaImage enviarImagem(
            Long encontroId,
            MultipartFile file
    ) throws IOException {

        if (imageRepository.findByEncontroId(encontroId).isPresent()) {
            throw new IllegalArgumentException(
                    "Este encontro já possui uma imagem."
            );
        }

        Encontro encontro = encontroRepository.findById(encontroId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Encontro não encontrado."
                ));

        byte[] conteudo = compressor.compress(file);

        String chaveObjeto = "encontros/"
                + encontroId + "/"
                + UUID.randomUUID() + ".jpg";

        storage.enviarImagem(
                chaveObjeto,
                conteudo,
                IMAGE_TYPE
        );

        try {
            MonitoriaImage imagem = new MonitoriaImage(
                    encontro,
                    chaveObjeto,
                    IMAGE_TYPE,
                    (long) conteudo.length
            );

            return imageRepository.save(imagem);

        } catch (RuntimeException e) {
            try {
                storage.excluirImagem(chaveObjeto);
            } catch (RuntimeException erroExclusao) {
                e.addSuppressed(erroExclusao);
            }

            throw e;
        }
    }

    public ImageContent obterImagem(Long encontroId)
            throws IOException {

        MonitoriaImage image = imageRepository
                .findByEncontroId(encontroId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Este encontro não possui imagem."
                ));

        try (InputStream entrada =
                     storage.obterImagem(image.getObjectKey())) {

            return new ImageContent(
                    entrada.readAllBytes(),
                    image.getContentType()
            );
        }
    }

    @Transactional
    public void excluirImagem(Long encontroId) {

        MonitoriaImage image = imageRepository
                .findByEncontroId(encontroId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Este encontro não possui imagem."
                ));

        storage.excluirImagem(image.getObjectKey());

        imageRepository.delete(image);
    }

    public record ImageContent(
            byte[] content,
            String contentType
    ) {
    }
}