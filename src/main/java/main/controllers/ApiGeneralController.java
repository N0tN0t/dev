package main.controllers;

import main.api.response.CheckResponse;
import main.api.response.InitResponse;
import main.api.response.SettingsResponse;
import main.api.response.TagListResponse;
import main.service.SettingsService;
import org.springframework.web.bind.annotation.*;


@RestController
public class ApiGeneralController {
    private final InitResponse initResponse;
    private final SettingsService settingsService;

    public ApiGeneralController(InitResponse initResponse, SettingsService settingsService) {
        this.initResponse = initResponse;
        this.settingsService = settingsService;
    }

    @GetMapping("/api/init")
    private InitResponse init() {
        return initResponse;
    }

    @GetMapping("/api/settings")
    private SettingsResponse settings(){
        return settingsService.getGlobalSettings();
    }


    @GetMapping("/api/tag")
    private TagListResponse tags() {return new TagListResponse();
    }
}
