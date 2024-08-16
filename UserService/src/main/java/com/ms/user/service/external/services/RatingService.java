package com.ms.user.service.external.services;

import com.ms.user.service.entity.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@FeignClient(name = "RATING-SERVICE")
public interface RatingService {

    //GET
    @GetMapping("/ratings")
    public ResponseEntity<List<Rating>> getRatings();

    //POST
    @PostMapping("/ratings")
    public ResponseEntity<Rating> createRating(Rating rating);

    //PUT
    @PutMapping("/ratings/{ratingId}")
    public ResponseEntity<Rating> updateRating(@PathVariable String ratingId, Rating rating);

    //DELETE
    @DeleteMapping("/ratings/{ratingId}")
    public ResponseEntity<Void> deleteRating(@PathVariable String ratingId);


    

}
