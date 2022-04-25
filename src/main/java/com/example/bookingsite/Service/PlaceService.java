package com.example.bookingsite.Service;

import com.example.bookingsite.Model.Enum.PlaceType;
import com.example.bookingsite.Model.Hotel;
import com.example.bookingsite.Model.Place;
import com.example.bookingsite.Model.Villa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PlaceService {

    Optional<Hotel> createHotel(String name, String location, String contactNumber,
                                Long ownerId, String description, Integer vipRooms, Integer standardRooms,
                                Integer priceVipRoom, Integer priceStandardRoom);

    Optional<Villa> createVilla(String name, String location, String contactNumber,
                                Long ownerId, String description, Integer pricePerNight);

    Optional<Hotel> updateHotel(Long id, String name, String location, String contactNumber,
                                Long ownerId, String description, Integer vipRooms, Integer standardRooms,
                                Integer priceVipRoom, Integer priceStandardRoom);

    Optional<Villa> updateVilla(Long id, String name, String location, String contactNumber,
                                Long ownerId, String description, Integer pricePerNight);

    Optional<Place> addImages(Long placeId, List<String> images);

    void removePlace(Long id);

    Optional<Place> findById(Long id);

    List<Place> findAll();

    List<Villa> findAllVillas();

    List<Hotel> findAllHotels();

    Page<Place> findPage(Pageable pageable);

    Page<Villa> findPageVillas(Pageable pageable);

    Page<Hotel> findPageHotels(Pageable pageable);

    PlaceType placeType(Place place);

}
