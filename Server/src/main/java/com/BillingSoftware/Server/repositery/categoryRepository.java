package com.BillingSoftware.Server.repositery;

import com.BillingSoftware.Server.entity.categoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface categoryRepository extends JpaRepository<categoryEntity,Long> {

    //this interface will provide all the required methods to make transactions with the database


    Optional<categoryEntity> findByCategoryId(String CategoryId);
}
