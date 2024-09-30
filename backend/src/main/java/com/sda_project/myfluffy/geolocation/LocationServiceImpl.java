package com.sda_project.myfluffy.geolocation;

import com.sda_project.myfluffy.dto.LocationCreateDto;
import com.sda_project.myfluffy.dto.LocationDto;
import com.sda_project.myfluffy.exception.ResourceNotFoundException;
import com.sda_project.myfluffy.mapper.LocationCreateMapper;
import com.sda_project.myfluffy.mapper.LocationMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class LocationServiceImpl implements ILocationService {

    private final LocationRepository locationRepository;
    private final String googleMapUrl;

    public LocationServiceImpl(LocationRepository locationRepository,
                               @Value("${api.google-map.url}") String googleMapUrl) {
        this.locationRepository = locationRepository;
        this.googleMapUrl = googleMapUrl;
    }

    @Override
    public Location createLocation(LocationCreateDto locationCreateDto) {
        Location location = new Location();
        LocationCreateMapper.mapToLocation(locationCreateDto, location);
        return locationRepository.save(location);
    }


    @Override
    public LocationDto fetchLocationById(int id) {
        Location location = locationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Location", "id", Integer.toString(id))
        );
        LocationDto locationDto = LocationMapper.mapToLocationDto(location, new LocationDto());

        locationDto.setAddressUrl(buildGoogleMapUrl(location.getAddress()));

        return locationDto;
    }

    private String buildGoogleMapUrl(String address) {
        if (address == null || address.isEmpty()) {
            return googleMapUrl + "No+Address+Available";
        }
        String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
        return googleMapUrl + encodedAddress;
    }
}
