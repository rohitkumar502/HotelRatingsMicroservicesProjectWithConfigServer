package com.ms.user.service;

import com.ms.user.service.entity.Rating;
import com.ms.user.service.external.services.RatingService;
import com.ms.user.service.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class UserServiceApplicationTests {

    @Test
    void contextLoads() {
    }


    @Autowired
    private RatingService ratingService;

    @Test
    void createRating() {
        Rating rating = Rating.builder().rating(10).userId("")
                .hotelId("").feedback("This is created using feign client").build();

        ResponseEntity<Rating> ratingResponseEntity = ratingService.createRating(rating);
        Rating savedRating = ratingResponseEntity.getBody();
        System.out.println("new rating created: " + savedRating);

    }



}
