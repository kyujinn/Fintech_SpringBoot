package com.example.demo.repository;

import com.example.demo.entity.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends PagingAndSortingRepository<Member,Long> {
    Optional<Member> findBySeqAndName(Long key, String name);

    @Query("SELECT m.org, count(m.seq) FROM Member m where m.active = ?1 group by m.org")
    List<Object> countOrgGroup(Boolean isActive);
}
