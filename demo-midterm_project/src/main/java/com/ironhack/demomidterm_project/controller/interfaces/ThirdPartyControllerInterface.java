package com.ironhack.demomidterm_project.controller.interfaces;

import com.ironhack.demomidterm_project.model.ThirdParty;

import java.util.List;

public interface ThirdPartyControllerInterface {
    ThirdParty createThirdParty (ThirdParty thirdParty);
    List<ThirdParty> getThirdParties ();
    void deleteUser (Long id);
}
