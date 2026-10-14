package br.com.edu.uninter.monitoria.controller;

import br.com.edu.uninter.monitoria.service.MonitoriaImageService;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/encontros/{encontroId}/imagem")
public class MonitoriaImageController {

    private final MonitoriaImageService imageService;

    public MonitoriaImageController(
            MonitoriaImageService imageService
    ) {
        this.imageService = imageService;
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> enviarImagem(
            @PathVariable Long encontroId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        var image = imageService.enviarImagem(
                encontroId,
                file
        );

        return ResponseEntity.ok(Map.of(
                "mensagem", "Imagem enviada com sucesso.",
                "encontroId", encontroId,
                "contentType", image.getContentType(),
                "byteSize", image.getSizeBytes()
        ));
    }

    @GetMapping
    public ResponseEntity<byte[]> obterImagem(
            @PathVariable Long encontroId
    ) throws IOException {

        var image = imageService.obterImagem(encontroId);

        MediaType type = MediaType.parseMediaType(
                image.contentType()
        );

        return ResponseEntity.ok()
                .contentType(type)
                .cacheControl(CacheControl.noCache())
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"image.jpg\""
                )
                .body(image.content());
    }

    @DeleteMapping
    public ResponseEntity<Void> excluirImagem(
            @PathVariable Long encontroId
    ) {
        imageService.excluirImagem(encontroId);
        return ResponseEntity.noContent().build();
    }
}