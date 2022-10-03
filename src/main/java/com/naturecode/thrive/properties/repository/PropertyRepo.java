package com.naturecode.thrive.properties.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.naturecode.thrive.properties.model.Property;

@Repository
public interface PropertyRepo extends JpaRepository<Property, String> {
  @Query("SELECT p FROM Property p WHERE p.id = ?1")
  List<Property> findByPropId(String id);

  @Modifying
  @Query("DELETE FROM Property p where p.id=:id AND p.key=:key")
  void deleteByIdAndIndex(@Param("id") String id, @Param("key") String key);
}
