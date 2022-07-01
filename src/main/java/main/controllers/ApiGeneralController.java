package main.controllers;

import main.api.response.InitResponse;
import main.api.response.SettingsResponse;
import main.api.response.TagListResponse;
import main.respositories.SettingsRepository;
import main.service.SettingsService;
import org.springframework.web.bind.annotation.*;


@RestController
public class ApiGeneralController {
    private final InitResponse initResponse;
    private final SettingsService settingsService;

    public ApiGeneralController(InitResponse initResponse, SettingsService settingsService, SettingsRepository settingsRepository) {
        this.initResponse = initResponse;
        this.settingsService = settingsService;
    }

    @GetMapping("/api/init")
    private InitResponse init() {
        return initResponse;
    }

    @GetMapping("/api/settings")
    public SettingsResponse getGlobalSettings() {
        SettingsResponse settingsResponse = new SettingsResponse();
        settingsResponse.setMultiuserMode(
                settingsService.findByValue("MULTIUSER_MODE","YES"));
        settingsResponse.setPostPremoderation(
                settingsService.findByValue("POST_PREMODERATION","YES"));
        settingsResponse.setStatisticIsPublic(
                settingsService.findByValue("MULTIUSER_MODE","YES"));
        return settingsResponse;
    }


    @GetMapping("/api/tag")
    private TagListResponse tags() {return new TagListResponse();
    }
}
