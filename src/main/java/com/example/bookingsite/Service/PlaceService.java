package com.example.bookingsite.Service;

import com.example.bookingsite.Model.Hotel;
import com.example.bookingsite.Model.Person;
import com.example.bookingsite.Model.Place;
import com.example.bookingsite.Model.Villa;

import java.util.Optional;

public interface PlaceService {

    Optional<Hotel> createHotel(String name, String location, String contactNumber,
                                Person owner, String description, Integer vipRooms, Integer standardRooms,
                                Integer priceVipRoom, Integer priceStandardRoom);

    Optional<Villa> createVilla(String name, String location, String contactNumber,
                                Person owner, String description, Integer pricePerNight);

    Optional<Hotel> updateHotel(String name, String location, String contactNumber,
                                Person owner, String description, Integer vipRooms, Integer standardRooms,
                                Integer priceVipRoom, Integer priceStandardRoom);

    Optional<Hotel> updateVilla(String name, String location, String contactNumber,
                                Person owner, String description, Integer vipRooms, Integer standardRooms,
                                Integer priceVipRoom, Integer priceStandardRoom);

    void removePlace(Long id);

    Optional<Place> findById(Long id);



}
