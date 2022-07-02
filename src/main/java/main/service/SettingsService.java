package main.service;

import main.api.response.SettingsResponse;
import main.respositories.SettingsRepository;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {
    private SettingsRepository settingsRepository;

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

}
