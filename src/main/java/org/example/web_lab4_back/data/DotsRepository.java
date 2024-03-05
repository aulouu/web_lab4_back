package org.example.web_lab4_back.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DotsRepository extends JpaRepository<Dot, Long> {
    List<Dot> getDotByOwnerLogin(String ownerLogin);
    void deleteDotsByOwnerLogin(String ownerLogin);
}
