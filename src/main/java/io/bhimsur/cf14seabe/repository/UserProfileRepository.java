package io.bhimsur.cf14seabe.repository;

import io.bhimsur.cf14seabe.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> getUserProfileByUserId(Integer userId);
}
