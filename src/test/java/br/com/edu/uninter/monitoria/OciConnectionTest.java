package br.com.edu.uninter.monitoria;

import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.requests.GetBucketRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class OciConnectionTest {

    @Autowired
    private ObjectStorage objectStorage;

    @Value("${oci.namespace}")
    private String namespace;

    @Value("${oci.bucket}")
    private String bucket;

    @Test
    void deveAcessarBucketNaOci() {
        var response = objectStorage.getBucket(
                GetBucketRequest.builder()
                        .namespaceName(namespace)
                        .bucketName(bucket)
                        .build()
        );

        assertNotNull(response.getBucket());
        System.out.println("Conexão com o bucket OCI realizada!");
    }
}
