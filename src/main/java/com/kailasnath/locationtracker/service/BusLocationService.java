package com.kailasnath.locationtracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.kailasnath.locationtracker.Model.BusLocation;
import com.kailasnath.locationtracker.Model.PassengerDetails;
import com.kailasnath.locationtracker.Model.TokenManager;
import com.kailasnath.locationtracker.Repository.BusLocationRepo;
import com.kailasnath.locationtracker.Repository.PassengerDetailRepo;
import com.kailasnath.locationtracker.Repository.TokenRepo;

@Service
public class BusLocationService {

    @Autowired
    BusLocationRepo busLocationRepo;

    @Autowired
    PassengerDetailRepo passengerDetailRepo;

    @Autowired
    TokenRepo tokenRepo;

    public void updateLocation(@NonNull BusLocation busLocation) {
        busLocationRepo.save(busLocation);
    }

    public BusLocation getLocation(int id) {
        return busLocationRepo.findById(id).orElse(null);
    }

    public boolean validate(int id, String password) {
        PassengerDetails passengerDetails = passengerDetailRepo.findById(id).orElse(null);
        // System.out.println(passengerDetails.getId()+",
        // "+passengerDetails.getPassword());
        if (passengerDetails == null)
            return false;

        if (passengerDetails != null && !passengerDetails.getPassword().equals(password))
            return false;

        return true;
    }

    public String generateAndAssignToken(int clientId) {
        String token = generateToken();
        TokenManager tokenManager = new TokenManager(clientId, token);
        tokenRepo.save(tokenManager);

        return token;
    }

    public String generateToken() {
        StringBuffer sb = new StringBuffer();

        String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        for (int i = 0; i < 2; i++) {
            int ind = (int) (Math.random() * 26);
            sb.append(ind);
            sb.append(alphabets.charAt(ind));
        }

        return sb.toString();
    }

    public boolean validateToken(int clientId, String token) {
        TokenManager tokenManager = tokenRepo.findById(clientId).orElse(null);

        if (tokenManager == null || !tokenManager.getToken().equals(token))
            return false;

        return true;
    }

    public int getBusId(int clientId) {
        PassengerDetails passengerDetails = passengerDetailRepo.findById(clientId).orElse(null);
        if (passengerDetails == null)
            return -1;

        return passengerDetails.getBusId();
    }
}
