package com.airbnb_2.repository;

import com.airbnb_2.entity.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
}