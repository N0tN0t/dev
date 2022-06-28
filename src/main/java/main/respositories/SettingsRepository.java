package main.respositories;

import main.api.response.SettingsResponse;
import main.service.SettingsService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SettingsRepository extends JpaRepository<SettingsService, SettingsResponse> {
    @Query(value = "SELECT value FROM global_settings WHERE code = :code",
            nativeQuery = true)
    String findSettingValue(@Param("code") String code);
}
