package com.example.bookingsite.Service.Implementation;

import com.example.bookingsite.Model.*;
import com.example.bookingsite.Model.Enum.PlaceType;
import com.example.bookingsite.Model.Exception.PersonAlreadyOwnsThePlaceException;
import com.example.bookingsite.Model.Exception.PersonDoesNotExistException;
import com.example.bookingsite.Model.Exception.PlaceAlreadyExistsException;
import com.example.bookingsite.Model.Exception.PlaceDoesNotExistException;
import com.example.bookingsite.Repository.HotelRepository;
import com.example.bookingsite.Repository.PersonRepository;
import com.example.bookingsite.Repository.PlaceRepository;
import com.example.bookingsite.Repository.VillaRepository;
import com.example.bookingsite.Service.PlaceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
                                       Integer priceVipRoom, Integer priceStandardRoom, List<String> images) {

        Person person = this.personRepository.findById(ownerId).orElseThrow(PersonDoesNotExistException::new);
        Place p = this.placeRepository.findByName(name).orElse(null);
        if (p != null && p.getName().equals(name)) {
            throw new PlaceAlreadyExistsException();
        } else if (p != null && p.getOwner().getId().equals(ownerId)) {
            throw new PersonAlreadyOwnsThePlaceException();
        }

        Hotel hotel = new Hotel(name, location, description, contactNumber, person, vipRooms, standardRooms, priceVipRoom, priceStandardRoom);
        hotel.setImages(images);
        this.personRepository.save(person);
        this.hotelRepository.save(hotel);
        return Optional.of(hotel);
    }

    @Override
    public Optional<Villa> createVilla(String name, String location, String contactNumber, Long ownerId,
                                       String description, Integer pricePerNight, List<String> images) {

        Person person = this.personRepository.findById(ownerId).orElseThrow(PersonDoesNotExistException::new);
        Place p = this.placeRepository.findByName(name).orElse(null);

        if (p != null && p.getName().equals(name)) {
            throw new PlaceAlreadyExistsException();
        } else if (p != null && p.getOwner().getId().equals(ownerId)) {
            throw new PersonAlreadyOwnsThePlaceException();
        }

        Villa villa = new Villa(name, location, description, contactNumber, person, pricePerNight);
        villa.setImages(images);

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
        if (this.hotelRepository.findById(id).isPresent())
            return Optional.of((Place) this.hotelRepository.findById(id).get());
        if (this.villaRepository.findById(id).isPresent())
            return Optional.of((Place) this.villaRepository.findById(id).get());
        throw new PlaceDoesNotExistException();
    }

    @Override
    public List<Place> findAll() {
        return this.placeRepository.findAll();
    }

    @Override
    public List<Villa> findAllVillas() {
        return this.villaRepository.findAll();
    }

    @Override
    public List<Hotel> findAllHotels() {
        return this.hotelRepository.findAll();
    }

    @Override
    public Page<Place> findPage(Pageable pageable) {
        return this.placeRepository.findAll(pageable);
    }

    @Override
    public long countPlaces() {
        return this.placeRepository.count();
    }

    @Override
    public Page<Villa> findPageVillas(Pageable pageable) {
        return this.villaRepository.findAll(pageable);
    }

    @Override
    public long countVillas() {
        return this.villaRepository.count();
    }

    @Override
    public Page<Hotel> findPageHotels(Pageable pageable) {
        return this.hotelRepository.findAll(pageable);
    }

    @Override
    public long countHotels() {
        return this.hotelRepository.count();
    }

    @Override
    public PlaceType placeType(Place place) {
        if (place instanceof Hotel) {
            return PlaceType.HOTEL;
        }
        return PlaceType.VILLA;
    }

    @Override
    public Place deleteById(Long id) {
        Place place = this.placeRepository.findById(id).orElseThrow(PlaceDoesNotExistException::new);
        this.placeRepository.delete(place);
        return place;
    }
}
