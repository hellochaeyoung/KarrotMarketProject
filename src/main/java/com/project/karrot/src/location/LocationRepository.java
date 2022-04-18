package com.project.karrot.src.location;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<List<Location>> findByAddressContains(String address); // 그래야 메인화면에서 회원 지역의 상품들 지역 아이디로 갖고오기 가능

    Optional<Location> findByAddress(String address);

}
