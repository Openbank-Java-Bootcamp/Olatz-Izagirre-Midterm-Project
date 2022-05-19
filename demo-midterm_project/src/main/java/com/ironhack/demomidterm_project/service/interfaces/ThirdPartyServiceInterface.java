package com.ironhack.demomidterm_project.service.interfaces;

import com.ironhack.demomidterm_project.model.ThirdParty;

import java.util.List;

public interface ThirdPartyServiceInterface {
    ThirdParty createThirdParty (ThirdParty thirdParty);
    List<ThirdParty> getThirdParties ();

    void deleteUser (Long id);
}
