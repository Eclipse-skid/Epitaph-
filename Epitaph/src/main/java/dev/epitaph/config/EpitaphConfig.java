package dev.epitaph.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class EpitaphConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path PATH = FabricLoader.getInstance().getConfigDir().resolve("epitaph.json");

    public boolean screenmatesEnabled = true;
    public boolean boykisserEnabled = true;
    public boolean mikeyEnabled = true;
    public boolean showTotemVisuals = true;
    public boolean showShieldVisuals = true;
    public boolean showWeaponVisuals = true;
    public boolean showCustomHotbar = true;
    public boolean showVisualEffects = false;
    public double screenmateScale = 1.0;
    public double boykisserX = -48;
    public double boykisserY = -54;
    public double mikeyX = 48;
    public double mikeyY = -54;
    public double weaponScale = 1.0;
    public double shieldScale = 1.0;
    public double totemScale = 1.0;

    public static EpitaphConfig INSTANCE = new EpitaphConfig();

    public static void load() {
        try {
            if (Files.exists(PATH)) {
                EpitaphConfig loaded = GSON.fromJson(Files.readString(PATH), EpitaphConfig.class);
                if (loaded != null) INSTANCE = loaded;
            } else save();
        } catch (Exception e) {
            System.err.println("[Epitaph] Could not load config: " + e.getMessage());
        }
    }

    public static void save() {
        try {
            Files.createDirectories(PATH.getParent());
            Files.writeString(PATH, GSON.toJson(INSTANCE));
        } catch (IOException e) {
            System.err.println("[Epitaph] Could not save config: " + e.getMessage());
        }
    }
}
