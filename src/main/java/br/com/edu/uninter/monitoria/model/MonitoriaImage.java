package br.com.edu.uninter.monitoria.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "monitoria_image",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_monitoria_image_encontro",
                        columnNames = "encontro_id"
                )
        }
)
public class MonitoriaImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "encontro_id", nullable = false)
    private Encontro encontro;

    @Column(name = "object_key", nullable = false, unique = true)
    private String objectKey;

    @Column(name = "content_type", nullable = false, length = 100)
    private String contentType;

    @Column(name = "size_bytes", nullable = false)
    private Long sizeBytes;

    @Column(name = "upload_date", nullable = false)
    private LocalDateTime uploadDate;

    protected MonitoriaImage() {
    }

    public MonitoriaImage(
            Encontro encontro,
            String objectKey,
            String contentType,
            Long sizeBytes
    ) {
        this.encontro = encontro;
        this.objectKey = objectKey;
        this.contentType = contentType;
        this.sizeBytes = sizeBytes;
        this.uploadDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Encontro getEncontro() {
        return encontro;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public String getContentType() {
        return contentType;
    }

    public Long getSizeBytes() {
        return sizeBytes;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }
}