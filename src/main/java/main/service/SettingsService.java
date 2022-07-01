package main.service;

import main.api.response.SettingsResponse;
import main.respositories.SettingsRepository;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {
    private SettingsRepository settingsRepository;

    public SettingsResponse getGlobalSettings() {
        SettingsResponse settingsResponse = new SettingsResponse();
        settingsResponse.setMultiuserMode(true);
        return settingsResponse;
    }

    public boolean findByValue(String value,String equalsTo) {
        return settingsRepository.findSettingValue(value).equals(equalsTo);
    }

}
