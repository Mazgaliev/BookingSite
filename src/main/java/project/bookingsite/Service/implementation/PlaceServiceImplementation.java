package project.bookingsite.Service.implementation;


import org.springframework.stereotype.Service;
import project.bookingsite.Model.Hotel;
import project.bookingsite.Model.Place;
import project.bookingsite.Model.User;
import project.bookingsite.Model.Villa;
import project.bookingsite.Model.exceptions.ContactNumberAlreadyUsedException;
import project.bookingsite.Model.exceptions.PlaceDoesNotExistException;
import project.bookingsite.Model.exceptions.PlaceNameTakenException;
import project.bookingsite.Model.exceptions.UserDoesNotExistException;
import project.bookingsite.Repository.HotelRepository;
import project.bookingsite.Repository.PlaceRepository;
import project.bookingsite.Repository.UserRepository;
import project.bookingsite.Repository.VillaRepository;
import project.bookingsite.Service.PlaceService;

@Service

public class PlaceServiceImplementation implements PlaceService {
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;
    private final VillaRepository villaRepository;
    private final PlaceRepository placeRepository;

    public PlaceServiceImplementation(HotelRepository hotelRepository, UserRepository userRepository, VillaRepository villaRepository, PlaceRepository placeRepository) {
        this.villaRepository = villaRepository;
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
        this.placeRepository = placeRepository;
    }

    @Override
    public Place createHotel(String name, String location, String contactNumber,
                             Long ownerId, Integer standardRooms, Integer vipRooms, Double standardPrice, Double vipPrice) {
        //this is dumb implementation lmao but ok
        Place p = hotelRepository.findByName(name).orElse(null);
        User user = userRepository.findById(ownerId).orElseThrow(UserDoesNotExistException::new);
        if (p != null)
            throw new PlaceNameTakenException();
        else if (hotelRepository.findByContactNumber(contactNumber).orElse(null) != null)
            throw new ContactNumberAlreadyUsedException();


        return new Hotel(name, location, contactNumber, user, standardRooms, vipRooms, standardPrice, vipPrice);
    }

    @Override
    public Place createVilla(String name, String location, String contactNumber, Long ownerId, String description, Long pricePerNight) {
        Place p = villaRepository.findByName(name).orElse(null);
        User user = userRepository.findById(ownerId).orElseThrow(UserDoesNotExistException::new);
        if (p != null)
            throw new PlaceNameTakenException();
        else if (villaRepository.findByContactNumber(contactNumber).orElse(null) != null)
            throw new ContactNumberAlreadyUsedException();

        return new Villa(name, location, contactNumber, user, description, pricePerNight);
    }

    @Override
    public Place removePlace(Long placeId) {
        Place p = placeRepository.findById(placeId).orElseThrow(PlaceDoesNotExistException::new);
        placeRepository.delete(p);
        return p;
    }
}
