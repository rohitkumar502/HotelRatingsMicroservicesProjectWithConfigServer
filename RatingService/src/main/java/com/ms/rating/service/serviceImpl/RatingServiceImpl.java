package com.ms.rating.service.serviceImpl;

import com.ms.rating.entity.Rating;
import com.ms.rating.repository.RatingRepository;
import com.ms.rating.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    RatingRepository ratingRepos;

    @Override
    public Rating create(Rating rating) {
      return ratingRepos.save(rating);
    }

    @Override
    public List<Rating> getRatings() {
        return ratingRepos.findAll();
    }

    @Override
    public List<Rating> getRatingsByUserId(String userId) {
        return ratingRepos.findByUserId(userId);
    }

    @Override
    public List<Rating> getRatingsByHotelId(String hotelId) {
        return ratingRepos.findByHotelId(hotelId);
    }

    @Override
    public Rating updateRating(String ratingId, Rating rating) {

//        @Id
//        private String ratingId;
//        private String userId;
//        private String hotelId;
//        private int rating;
//        private String feedback;

        Rating rating1 = ratingRepos.findById(ratingId)
                                    .orElseThrow(() -> new RuntimeException("Rating not found."));

        rating1.setUserId(rating.getUserId());
        rating1.setHotelId(rating.getHotelId());
        rating1.setRating(rating.getRating());
        rating1.setFeedback(rating.getFeedback());

        return ratingRepos.save(rating1);

    }

    @Override
    public void deleteRating(String ratingId) {
        ratingRepos.deleteById(ratingId);
    }


}
