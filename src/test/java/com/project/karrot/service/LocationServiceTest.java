package com.project.karrot.service;

import com.project.karrot.src.location.LocationService;
import com.project.karrot.src.location.dto.LocationResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class LocationServiceTest {

    @Autowired
    LocationService locationService;

    private Long locationId = 2L;

    @Test
    void 지역_조회() {
        LocationResponseDto locationResponseDto = locationService.find(locationId);

        assertEquals(locationResponseDto.getAddress(), "경기도 부천시");
    }

    @Test
    void 지역_검색() {

        String location1 = "서울특별시";

        LocationResponseDto results = locationService.findByAddress(location1);

        assertEquals(results.getAddress(), location1);
    }

    @Test
    void 지역_검색_리스트() {

        String location1 = "광역시";

        List<LocationResponseDto> results = locationService.findByAddressAll(location1);

        assertThat(results.size()).isGreaterThan(0);

    }

}
