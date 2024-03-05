package org.example.web_lab4_back.user;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserData, Long> {
    UserData findUserDataByLogin(@NonNull String login);
}