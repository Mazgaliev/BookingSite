package project.bookingsite.Service;

import project.bookingsite.Model.Hotel;
import project.bookingsite.Model.Place;
import project.bookingsite.Model.Villa;

public interface PlaceService {
    //TODO implement this

    public Place createHotel(String name, String location, String contactNumber, Long ownerId,
                             Integer standardRooms, Integer vipRooms, Double standardPrice, Double vipPrice);

    public Place createVilla(String name, String location, String contactNumber, Long ownerId, String description, Long pricePerNight);

    public Place removePlace(Long placeId);
}
