package com.test.springbootrest.model.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedDailyLifeRepository extends JpaRepository<FeedDailyLife, Long> {
}
