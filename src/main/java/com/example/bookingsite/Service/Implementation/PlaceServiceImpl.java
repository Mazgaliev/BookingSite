package com.example.bookingsite.Service.Implementation;

import com.example.bookingsite.Model.Hotel;
import com.example.bookingsite.Model.Person;
import com.example.bookingsite.Model.Place;
import com.example.bookingsite.Model.Villa;
import com.example.bookingsite.Service.PlaceService;

import java.util.Optional;

public class PlaceServiceImpl implements PlaceService {
    @Override
    public Optional<Hotel> createHotel(String name, String location, String contactNumber, Person owner, String description, Integer vipRooms, Integer standardRooms, Integer priceVipRoom, Integer priceStandardRoom) {
        return Optional.empty();
    }

    @Override
    public Optional<Villa> createVilla(String name, String location, String contactNumber, Person owner, String description, Integer pricePerNight) {
        return Optional.empty();
    }

    @Override
    public Optional<Hotel> updateHotel(String name, String location, String contactNumber, Person owner, String description, Integer vipRooms, Integer standardRooms, Integer priceVipRoom, Integer priceStandardRoom) {
        return Optional.empty();
    }

    @Override
    public Optional<Hotel> updateVilla(String name, String location, String contactNumber, Person owner, String description, Integer vipRooms, Integer standardRooms, Integer priceVipRoom, Integer priceStandardRoom) {
        return Optional.empty();
    }

    @Override
    public void removePlace(Long id) {

    }

    @Override
    public Optional<Place> findById(Long id) {
        return Optional.empty();
    }
}
