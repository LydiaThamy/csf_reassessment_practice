package vttp.backend.repository;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Repository
public class S3Repo {

    @Autowired
    private S3Client s3;

    public String saveImage(MultipartFile image, String id) {

        try {
            PutObjectRequest putReq = PutObjectRequest.builder()
                    .bucket("vttp")
                    .key("image/%s".formatted(id))
                    .contentLength(image.getSize())
                    .contentType(image.getContentType())
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .build();

            s3.putObject(putReq, RequestBody.fromInputStream(
                    image.getInputStream(), image.getSize()));

        } catch (IOException e) {
            e.printStackTrace();
        }

        String imageUrl = UriComponentsBuilder
            .fromUriString("https://vttp.sgp1.digitaloceanspaces.com/image")
                .pathSegment(id)
                .toUriString();
                
        return imageUrl;
    }

}
