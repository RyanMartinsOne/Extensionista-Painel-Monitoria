package br.com.edu.uninter.monitoria.config;

import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.Region;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.ObjectStorageClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class OciObjectStorageConfig {

    @Value("${oci.region}")
    private String region;

    @Bean
    public ObjectStorage objectStorage() throws IOException {
        var configFile = ConfigFileReader.parseDefault();

        var provider =
                new ConfigFileAuthenticationDetailsProvider(configFile);

        return ObjectStorageClient.builder()
                .region(Region.fromRegionId(region))
                .build(provider);
    }
}
