package com.ms.user.service.service.serviceImpl;

import com.ms.user.service.entity.Hotel;
import com.ms.user.service.entity.Rating;
import com.ms.user.service.entity.User;
import com.ms.user.service.exception.ResourceNotFoundException;
import com.ms.user.service.external.services.HotelService;
import com.ms.user.service.repository.UserRepository;
import com.ms.user.service.service.UserService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepos;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService; // coming from our *.external.services.HotelService

    private Logger logger =  LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        //generate unique userId
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepos.save(user);
    }

    @Override
    public List<User> getAllUser() {
        List<User> allUsers =  userRepos.findAll();

        for (User user : allUsers) {
            ArrayList<Rating> ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(),
                    ArrayList.class);
            logger.info("{}", ratingsOfUser);
            user.setRatings(ratingsOfUser);
        }

        return allUsers;
    }

    //get single user
    @Override
    public User getUser(String userId) {
        //get user from database with the help of user repository
        User user = userRepos.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User with given id is not found on server!! : "+userId));

        //fetch rating of the above user from RATING SERVICE and removing hard-coded hostname and port number and making it dynamic
        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);

        logger.info("{}", ratingsOfUser);

        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();

        List<Rating> ratingList = ratings.stream().map(rating -> {
            //API call to hotel service to get the hotel
             Hotel hotel = hotelService.getHotel(rating.getHotelId());

            //Set the hotel to rating
            rating.setHotel(hotel);
            //return the rating
            return rating;
        }).toList();

        user.setRatings(ratingList); 
        return user;
    }

}
