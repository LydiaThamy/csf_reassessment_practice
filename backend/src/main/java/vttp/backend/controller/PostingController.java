package vttp.backend.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import vttp.backend.model.Posting;
import vttp.backend.service.PostingService;

@RestController
@RequestMapping("/api/posting")
public class PostingController {

    @Autowired
    private PostingService service;

    @PostMapping(path = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postForm(@RequestPart MultipartFile imageFile, @ModelAttribute Posting posting)
            throws IllegalArgumentException {

        // set ID and date in posting
        posting.setPostingId(service.createId());
        posting.setPostingDate(new Date().toString());

        // save image in S3 repo
        String imageUrl = service.saveImage(imageFile, posting.getPostingId());

        String json = Json.createObjectBuilder()
                .add("postingId", posting.getPostingId())
                .add("postingDate", posting.getPostingDate().toString())
                .add("name", posting.getName())
                .add("email", posting.getEmail())
                .add("phone", posting.getPhone())
                .add("title", posting.getTitle())
                .add("description", posting.getDescription())
                .add("image", imageUrl)
                .build().toString();

        // save posting in Redis repo
        service.savePosting(posting.getPostingId(), json);

        return ResponseEntity.ok(json);
    }

    @GetMapping(path = "/{postingId}")
    public ResponseEntity<String> getPosting(@PathVariable("postingId") String postingId) {

        // get posting from Redis
        Optional<String> opt = service.getPosting(postingId);

        if (opt.isEmpty()) {
            JsonObject payload = Json.createObjectBuilder()
                    .add("message", "Posting ID " + postingId + " not found")
                    .build();
            return ResponseEntity.badRequest().body(payload.toString());
        }

        return ResponseEntity.ok(opt.get());
    }

    @PutMapping(path = "/{postingId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> confirmPost(@PathVariable("postingId") String postingId,
            @RequestBody Posting posting) {

        JsonObjectBuilder payload = Json.createObjectBuilder();

        // get posting from Redis
        Optional<String> opt = service.getPosting(postingId);

        if (opt.isEmpty()) {
            payload.add("message", "Posting ID " + postingId + " not found");
            return ResponseEntity.badRequest().body(payload.build().toString());
        }

        // delete posting from Redis
        service.deletePosting(postingId);

        // save posting to SQL
        

        // send payload
        payload.add("message", "Accepted " + postingId);

        return ResponseEntity.ok(payload.build().toString());
    }

}
