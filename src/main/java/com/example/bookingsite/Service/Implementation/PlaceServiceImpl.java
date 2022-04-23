package com.example.bookingsite.Service.Implementation;

import com.example.bookingsite.Model.Exception.PersonAlreadyOwnsThePlaceException;
import com.example.bookingsite.Model.Exception.PersonDoesNotExistException;
import com.example.bookingsite.Model.Exception.PlaceAlreadyExistsException;
import com.example.bookingsite.Model.Exception.PlaceDoesNotExistException;
import com.example.bookingsite.Model.Hotel;
import com.example.bookingsite.Model.Person;
import com.example.bookingsite.Model.Place;
import com.example.bookingsite.Model.Villa;
import com.example.bookingsite.Repository.HotelRepository;
import com.example.bookingsite.Repository.PersonRepository;
import com.example.bookingsite.Repository.PlaceRepository;
import com.example.bookingsite.Repository.VillaRepository;
import com.example.bookingsite.Service.PlaceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaceServiceImpl implements PlaceService {
    private final PlaceRepository placeRepository;
    private final PersonRepository personRepository;
    private final HotelRepository hotelRepository;
    private final VillaRepository villaRepository;

    public PlaceServiceImpl(PlaceRepository placeRepository, PersonRepository personRepository, HotelRepository hotelRepository, VillaRepository villaRepository) {
        this.placeRepository = placeRepository;
        this.personRepository = personRepository;
        this.hotelRepository = hotelRepository;
        this.villaRepository = villaRepository;
    }


    @Override
    public Optional<Hotel> createHotel(String name, String location, String contactNumber, Long ownerId,
                                       String description, Integer vipRooms, Integer standardRooms,
                                       Integer priceVipRoom, Integer priceStandardRoom) {

        Person person = this.personRepository.findById(ownerId).orElseThrow(PersonDoesNotExistException::new);
        Place p = this.placeRepository.findByName(name).orElse(null);

        if (p != null && p.getName().equals(name)) {
            throw new PlaceAlreadyExistsException();
        } else if (p != null && p.getOwner().getId().equals(ownerId)) {
            throw new PersonAlreadyOwnsThePlaceException();
        }

        Hotel hotel = new Hotel(name, location, description, contactNumber, person, vipRooms, standardRooms, priceVipRoom, priceStandardRoom);
        this.hotelRepository.save(hotel);
        return Optional.of(hotel);
    }

    @Override
    public Optional<Villa> createVilla(String name, String location, String contactNumber, Long ownerId,
                                       String description, Integer pricePerNight) {

        Person person = this.personRepository.findById(ownerId).orElseThrow(PersonDoesNotExistException::new);
        Place p = this.placeRepository.findByName(name).orElse(null);

        if (p != null && p.getName().equals(name)) {
            throw new PlaceAlreadyExistsException();
        } else if (p != null && p.getOwner().getId().equals(ownerId)) {
            throw new PersonAlreadyOwnsThePlaceException();
        }

        Villa villa = new Villa(name, location, description, contactNumber, person, pricePerNight);
        this.villaRepository.save(villa);
        return Optional.of(villa);
    }

    @Override
    public Optional<Hotel> updateHotel(Long id, String name, String location, String contactNumber,
                                       Long ownerId, String description, Integer vipRooms, Integer standardRooms,
                                       Integer priceVipRoom, Integer priceStandardRoom) {

        Hotel hotel = this.hotelRepository.findById(id).orElseThrow(PlaceDoesNotExistException::new);
        Person owner = this.personRepository.findById(ownerId).orElseThrow(PersonDoesNotExistException::new);
        if (this.placeRepository.findByName(name).isPresent()) {
            throw new PlaceAlreadyExistsException();
        }
        hotel.setName(name);
        hotel.setLocation(location);
        hotel.setOwner(owner);
        hotel.setContactNumber(contactNumber);
        hotel.setDescription(description);
        hotel.setVipRooms(vipRooms);
        hotel.setStandardRooms(standardRooms);
        hotel.setPriceStandardRoom(priceStandardRoom);
        hotel.setPriceVipRoom(priceVipRoom);

        this.hotelRepository.save(hotel);
        return Optional.of(hotel);
    }

    @Override
    public Optional<Villa> updateVilla(Long id, String name, String location, String contactNumber, Long ownerId,
                                       String description, Integer pricePerNight) {

        Villa villa = this.villaRepository.findById(id).orElseThrow(PlaceDoesNotExistException::new);
        Person owner = this.personRepository.findById(ownerId).orElseThrow(PersonDoesNotExistException::new);
        if (this.placeRepository.findByName(name).isPresent()) {
            throw new PlaceAlreadyExistsException();
        }

        villa.setName(name);
        villa.setLocation(location);
        villa.setOwner(owner);
        villa.setContactNumber(contactNumber);
        villa.setDescription(description);
        villa.setPricePerNight(pricePerNight);
        this.villaRepository.save(villa);

        return Optional.of(villa);
    }

    @Override
    public Optional<Place> addImages(Long placeId, List<String> images) {
        Place place = this.placeRepository.findById(placeId).orElseThrow(PlaceDoesNotExistException::new);
        List<String> imgs = place.getImages();
        imgs.addAll(images);
        place.setImages(imgs);
        this.placeRepository.save(place);
        return Optional.of(place);
    }

    @Override
    public void removePlace(Long id) {
        Place place = this.placeRepository.findById(id).orElseThrow(PlaceDoesNotExistException::new);
        this.placeRepository.delete(place);
    }

    @Override
    public Optional<Place> findById(Long id) {
        return this.placeRepository.findById(id);
    }

    @Override
    public List<Place> findAll() {
        return this.placeRepository.findAll();
    }
}
