package com.project.karrot.service;

import com.project.karrot.src.location.Location;
import com.project.karrot.src.location.LocationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class LocationServiceTest {

    @Autowired
    LocationService locationService;

    @Test
    public void 지역_검색() {

        String location1 = "상동";

        List<Location> results = locationService.findByName(location1).orElseGet(() -> new ArrayList<Location>());

        for(Location l : results) {
            System.out.println(l.getAddress());
        }
    }
}
