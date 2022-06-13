package me.ramos.WaitForm.domain.recommend.repository;

import me.ramos.WaitForm.domain.recommend.domain.Recommend;
import me.ramos.WaitForm.domain.recommend.repository.querydsl.RecommendCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendRepository extends JpaRepository<Recommend, Long>, RecommendCustomRepository {
}
