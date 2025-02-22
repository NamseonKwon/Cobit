package com.finalproject.cobit.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalproject.cobit.model.Stage;

@Repository
public interface StageRepo extends JpaRepository<Stage, Long>{
	List<Stage> findAll();

	List<Stage> getStageByType(Long type);
	Optional<Stage> getStageByTypeAndMap(Long type, Long map);
}