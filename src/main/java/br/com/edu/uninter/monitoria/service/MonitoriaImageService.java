package br.com.edu.uninter.monitoria.service;

import br.com.edu.uninter.monitoria.exception.ImageExistException;
import br.com.edu.uninter.monitoria.exception.ImageNotFoundException;
import br.com.edu.uninter.monitoria.model.Encontro;
import br.com.edu.uninter.monitoria.model.MonitoriaImage;
import br.com.edu.uninter.monitoria.repository.EncontroRepository;
import br.com.edu.uninter.monitoria.repository.MonitoriaImageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@Service
public class MonitoriaImageService {

    private static final String IMAGE_TYPE = "image/jpeg";

    private static final Logger log =
            LoggerFactory.getLogger(MonitoriaImageService.class);

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
            throw new ImageExistException();
        }

        Encontro encontro = encontroRepository.findById(encontroId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Não existe encontro com id: " + encontroId
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
                .orElseThrow(ImageNotFoundException::new);

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
                .orElseThrow(ImageNotFoundException::new);

        storage.excluirImagem(image.getObjectKey());

        imageRepository.delete(image);
    }

    @Transactional
    public Optional<String> removerRegistroSeExistir(Long encontroId) {
        return imageRepository.findByEncontroId(encontroId)
                .map(image -> {
                    String chave = image.getObjectKey();
                    imageRepository.delete(image);
                    imageRepository.flush();
                    return chave;
                });
    }

    public void limparObjetoSilenciosamente(String chaveObjeto) {
        try {
            storage.excluirImagem(chaveObjeto);
        } catch (RuntimeException e) {
            log.warn("Objeto órfão no bucket: {}", chaveObjeto, e);
        }
    }

    public record ImageContent(
            byte[] content,
            String contentType
    ) {
    }
}