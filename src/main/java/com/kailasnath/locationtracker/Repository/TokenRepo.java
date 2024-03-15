package com.kailasnath.locationtracker.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kailasnath.locationtracker.Model.TokenManager;

public interface TokenRepo extends JpaRepository<TokenManager, Integer> {

}
