package com.example.bookingsite.Service;

import com.example.bookingsite.Model.Enum.PlaceType;
import com.example.bookingsite.Model.Hotel;
import com.example.bookingsite.Model.Place;
import com.example.bookingsite.Model.Reservation;
import com.example.bookingsite.Model.Villa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PlaceService {

    Optional<Hotel> createHotel(String name, String location, String contactNumber,
                                Long ownerId, String description, Integer vipRooms, Integer standardRooms,
                                Integer priceVipRoom, Integer priceStandardRoom, List<String> images);

    Optional<Villa> createVilla(String name, String location, String contactNumber,
                                Long ownerId, String description, Integer pricePerNight, List<String> images);

    Optional<Hotel> updateHotel(Long id, String name, String location, String contactNumber,
                                Long ownerId, String description, Integer vipRooms, Integer standardRooms,
                                Integer priceVipRoom, Integer priceStandardRoom, List<String> images);

    Optional<Villa> updateVilla(Long id, String name, String location, String contactNumber,
                                Long ownerId, String description, Integer pricePerNight, List<String> images);

    Optional<Place> addImages(Long placeId, List<String> images);

    void removePlace(Long id);

    Optional<Place> findById(Long id);

    List<Place> findAll();

    List<Villa> findAllVillas();

    List<Hotel> findAllHotels();

    Page<Place> findPage(Pageable pageable);

    long countPlaces();

    Page<Villa> findPageVillas(Pageable pageable);

    long countVillas();

    Page<Hotel> findPageHotels(Pageable pageable);

    long countHotels();

    PlaceType placeType(Place place);

    Place deleteById(Long id);
}
