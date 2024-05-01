package com.kailasnath.locationtracker.service;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.kailasnath.locationtracker.Model.BusLocation;
import com.kailasnath.locationtracker.Model.PassengerDetails;
import com.kailasnath.locationtracker.Model.TokenManager;
import com.kailasnath.locationtracker.Repository.BusLocationRepo;
import com.kailasnath.locationtracker.Repository.PassengerDetailRepo;
import com.kailasnath.locationtracker.Repository.TokenManagerRepo;

@Service
public class BusLocationService {

    BusLocationRepo busLocationRepo;

    PassengerDetailRepo passengerDetailRepo;

    TokenManagerRepo tokenManagerRepo;

    public BusLocationService(BusLocationRepo busLocationRepo, PassengerDetailRepo passengerDetailRepo,
            TokenManagerRepo tokenManagerRepo) {

        this.busLocationRepo  = busLocationRepo;
        this.passengerDetailRepo = passengerDetailRepo;
        this.tokenManagerRepo = tokenManagerRepo;
    }

    public void updateLocation(@NonNull BusLocation busLocation) {
        BusLocation dbBusLocation = busLocationRepo.findById(busLocation.getBusId()).orElse(null);

        if(dbBusLocation == null) 
            busLocationRepo.save(busLocation);
        else
            busLocationRepo.updateBusLocation(busLocation.getBusId(), busLocation.getLatitude(),busLocation.getLongitude());
    }

    public BusLocation getLocation(int busId) {
        return busLocationRepo.findById(busId).orElse(null);
    }

    public boolean validate(int clientId, String password) {
        PassengerDetails passengerDetails = passengerDetailRepo.findById(clientId).orElse(null);

        if (passengerDetails == null || !passengerDetails.getPassword().equals(password))
            return false;

        return true;
    }

    public String generateAndAssignToken(int clientId) {
        String token = generateToken();
        TokenManager tokenManager = new TokenManager(clientId, token);
        tokenManagerRepo.save(tokenManager);

        return token;
    }

    public String generateToken() {
        StringBuffer sb = new StringBuffer();

        String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        for (int i = 0; i < 10; i++) {
            int ind = (int) (Math.random() * 26);
            sb.append(ind);
            sb.append(alphabets.charAt(ind));
        }

        return sb.toString();
    }

    public boolean validateToken(int clientId, String token) {
        TokenManager tokenManager = tokenManagerRepo.findById(clientId).orElse(null);

        if (tokenManager == null || !tokenManager.getToken().equals(token))
            return false;

        return true;
    }

    public void removeToken(int clientId) {
        tokenManagerRepo.deleteById(clientId);
    }

    public String getToken(int clientId) {
        TokenManager tokenManager = tokenManagerRepo.findById(clientId).orElse(null);

        if (tokenManager != null)
            return tokenManager.getToken();

        else
            return (null);
    }
}
