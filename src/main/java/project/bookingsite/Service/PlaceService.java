package project.bookingsite.Service;

import project.bookingsite.Model.Hotel;
import project.bookingsite.Model.Place;
import project.bookingsite.Model.Villa;

public interface PlaceService {
    //TODO implement this

    public Place createHotel(Hotel hotel);

    public Place createVilla(Villa villa);

    public Place removePlace(Place place);
}
