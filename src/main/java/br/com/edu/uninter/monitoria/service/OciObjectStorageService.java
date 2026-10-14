package br.com.edu.uninter.monitoria.service;

import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.requests.DeleteObjectRequest;
import com.oracle.bmc.objectstorage.requests.GetObjectRequest;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import com.oracle.bmc.objectstorage.responses.GetObjectResponse;
import com.oracle.bmc.objectstorage.responses.PutObjectResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
public class OciObjectStorageService {

    private final ObjectStorage objectStorage;
    private final String namespace;
    private final String bucketName;

    public OciObjectStorageService(
            ObjectStorage objectStorage,
            @Value("${oci.namespace}") String namespace,
            @Value("${oci.bucket}") String bucketName
    ) {
        this.objectStorage = objectStorage;
        this.namespace = namespace;
        this.bucketName = bucketName;
    }

    public String enviarImagem(
            String objectKey,
            byte[] content,
            String contentType
    ) {
        PutObjectRequest request = PutObjectRequest.builder()
                .namespaceName(namespace)
                .bucketName(bucketName)
                .objectName(objectKey)
                .contentType(contentType)
                .contentLength((long) content.length)
                .putObjectBody(new ByteArrayInputStream(content))
                .build();

        PutObjectResponse response = objectStorage.putObject(request);

        return response.getETag();
    }

    public InputStream obterImagem(String chaveObjeto) {
        GetObjectRequest request = GetObjectRequest.builder()
                .namespaceName(namespace)
                .bucketName(bucketName)
                .objectName(chaveObjeto)
                .build();

        GetObjectResponse response = objectStorage.getObject(request);

        return response.getInputStream();
    }

    public void excluirImagem(String chaveObjeto) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .namespaceName(namespace)
                .bucketName(bucketName)
                .objectName(chaveObjeto)
                .build();

        objectStorage.deleteObject(request);
    }
}