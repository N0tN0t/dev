package main.service;

import main.api.response.SettingsResponse;
import main.entities.GlobalSettings;
import main.requests.SettingsRequest;
import main.respositories.SettingsRepository;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {
    private SettingsRepository settingsRepository;

    public SettingsService(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

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

    public SettingsResponse putGlobalSettings(SettingsRequest settingsRequest) {
        SettingsResponse settingsResponse = new SettingsResponse();
        settingsResponse.setStatisticIsPublic(settingsRequest.isStatisticIsPublic());
        settingsResponse.setMultiuserMode(settingsRequest.isMultiuserMode());
        settingsResponse.setPostPremoderation(settingsRequest.isPostPremoderation());
        GlobalSettings statisticIsPublicSettings = settingsRepository.findBySettingsCode("STATISTICS_IS_PUBLIC");
        statisticIsPublicSettings.setValue(String.valueOf(settingsResponse.isStatisticIsPublic()));
        settingsRepository.save(statisticIsPublicSettings);
        GlobalSettings multiuserModeSettings = settingsRepository.findBySettingsCode("MULTIUSER_MODE");
        multiuserModeSettings.setValue(String.valueOf(settingsResponse.isMultiuserMode()));
        settingsRepository.save(multiuserModeSettings);
        GlobalSettings postPremoderationSettings = settingsRepository.findBySettingsCode("POST_PREMODERATION");
        postPremoderationSettings.setValue(String.valueOf(settingsResponse.isPostPremoderation()));
        settingsRepository.save(postPremoderationSettings);
        return settingsResponse;
    }
}
