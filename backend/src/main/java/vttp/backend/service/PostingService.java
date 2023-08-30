package vttp.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.f4b6a3.ulid.UlidCreator;

import vttp.backend.model.Posting;
import vttp.backend.repository.RedisRepo;
import vttp.backend.repository.S3Repo;
import vttp.backend.repository.SqlRepo;

@Service
public class PostingService {

    @Autowired
    private S3Repo s3Repo;

    @Autowired
    private RedisRepo redisRepo;

    @Autowired
    private SqlRepo sqlRepo;

    public String createId() {
        return UlidCreator.getUlid().toString().substring(0, 8);
    }

    public String saveImage(MultipartFile image, String id) {
        return s3Repo.saveImage(image, id);
    }

    public void savePosting(String postingId, String posting) throws IllegalArgumentException {
        redisRepo.savePosting(postingId, posting);
    }

    public Optional<String> getPosting(String postingId) {
        return redisRepo.getPosting(postingId);
        // Optional<String> opt = redisRepo.getPosting(postingId);
        
        // if (opt.isEmpty())
        //     return Optional.empty();
        
        // JsonReader r = Json.createReader(new StringReader(opt.get()));
        // JsonObject o = r.readObject();

        // Posting p = new Posting(postingId);
        // p.setPostingDate(o.getString("date"));
        // p.setName(o.getString("name"));
        // p.setEmail(o.getString("email"));
        // p.setPhone(o.getString("phone"));
        // p.setTitle(o.getString("title"));
        // p.setDescription(o.getString("description"));
        // p.setImage(o.getString("image"));

        // return Optional.of(p);
    }

    public void deletePosting(String postingId) {
        redisRepo.deletePosting(postingId);
    }

    public boolean confirmPost(Posting posting) {
        return sqlRepo.confirmPost(posting);
    }
}
