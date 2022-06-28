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
    private final SettingsRepository settingsRepository;

    public ApiGeneralController(InitResponse initResponse, SettingsService settingsService, SettingsRepository settingsRepository) {
        this.initResponse = initResponse;
        this.settingsService = settingsService;
        this.settingsRepository = settingsRepository;
    }

    @GetMapping("/api/init")
    private InitResponse init() {
        return initResponse;
    }

    @GetMapping("/api/settings")
    public SettingsResponse getGlobalSettings() {
        SettingsResponse settingsResponse = new SettingsResponse();
        settingsResponse.setMultiuserMode(
                settingsRepository.findSettingValue("MULTIUSER_MODE").equals("YES"));
        settingsResponse.setPostPremoderation(
                settingsRepository.findSettingValue("POST_PREMODERATION").equals("YES"));
        settingsResponse.setStatisticIsPublic(
                settingsRepository.findSettingValue("STATISTICS_IS_PUBLIC").equals("YES"));
        return settingsResponse;
    }


    @GetMapping("/api/tag")
    private TagListResponse tags() {return new TagListResponse();
    }
}
