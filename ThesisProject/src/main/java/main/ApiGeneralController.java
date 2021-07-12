package main;

import main.Entities.Users;
import main.api.response.CheckResponse;
import main.api.response.InitResponse;
import main.api.response.SettingsResponse;
import main.api.response.TagResponse;
import main.service.SettingsService;
import org.springframework.web.bind.annotation.*;


@RestController
public class ApiGeneralController {
    private final InitResponse initResponse;
    private final SettingsService settingsService;
    private final CheckResponse checkResponse;

    public ApiGeneralController(InitResponse initResponse, SettingsService settingsService, CheckResponse checkResponse) {
        this.initResponse = initResponse;
        this.settingsService = settingsService;
        this.checkResponse = checkResponse;
    }

    @GetMapping("/api/settings")
    private SettingsResponse settings(){
        return settingsService.getGlobalSettings();
    }

    @GetMapping("/api/auth/check")
    private CheckResponse check() {
        return checkResponse;
    }

    @GetMapping("/api/tag")
    private TagResponse tags() {return new TagResponse();
    }

    @GetMapping("/api/init")
    private InitResponse init() {
        return initResponse;
    }
}
