package main.respositories;

import main.entities.GlobalSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingsRepository extends JpaRepository<GlobalSettings, Integer> {
    @Query(value = "SELECT value FROM global_settings WHERE code = :code",
            nativeQuery = true)
    String findSettingValue(@Param("code") String code);

    @Query(value = "SELECT * FROM global_settings WHERE code = :code",
            nativeQuery = true)
    GlobalSettings findBySettingsCode(@Param("code") String code);
}
