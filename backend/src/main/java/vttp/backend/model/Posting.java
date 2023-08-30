package vttp.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Posting {
    
    String postingId;
    String postingDate;
    String name;
    String email;
    String phone;
    String title;
    String description;
    String image;
    
    public Posting(String postingId) {
        this.postingId = postingId;
    }

}
