package com.project.karrot.repository;

import com.project.karrot.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query( "select l from Location l where l.address like %:name%")
    Optional<List<Location>> findByName(@Param("name") String name); // 그래야 메인화면에서 회원 지역의 상품들 지역 아이디로 갖고오기 가능

}
