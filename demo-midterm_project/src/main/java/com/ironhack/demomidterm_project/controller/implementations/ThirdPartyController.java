package com.ironhack.demomidterm_project.controller.implementations;

import com.ironhack.demomidterm_project.controller.interfaces.ThirdPartyControllerInterface;
import com.ironhack.demomidterm_project.service.interfaces.ThirdPartyServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ThirdPartyController implements ThirdPartyControllerInterface {
    @Autowired
    private ThirdPartyServiceInterface thirdPartyServiceInterface;
}
