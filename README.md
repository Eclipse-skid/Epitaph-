# Epitaph 1.0-SNAPSHOT — Minecraft 1.21.11 Fabric source

Target: Minecraft Java Edition 1.21.11, Fabric Loader, Java 21.

## Included
- Epitaph client-side mod source and dark-purple settings screen.
- Shift + Enter shortcut.
- Persistent JSON configuration in `.minecraft/config/epitaph.json`.
- Screenmates HUD hooks and transparent PNG cutouts made from the two images supplied by the user:
  - `boykisser.png`
  - `mikey.png`
- Toggles for screenmates, each character, item visuals and hotbar options.
- Saved scale settings for screenmates and held items.

## Important status
This is a source project, not a verified compiled JAR. It has not been compiled or tested against the user's installation in this environment. Some Minecraft/Fabric APIs and build coordinates may need adjustment after the first Gradle build.

The current source includes the Screenmates overlay and settings UI. Weapon/shield/totem scale values are stored, but are not yet connected to first-person item rendering. Custom texture overrides and frame-by-frame GIF animation are also not fully implemented. Spears are not a vanilla item in 1.21.11; custom spear appearance needs the relevant mod's item identifier or item class.

## Build instructions (Windows)
1. Install JDK 21 (check `java -version` in PowerShell).
2. Extract this ZIP to a normal folder, e.g. `C:\Users\<you>\Projects\Epitaph`.
3. Open that folder in IntelliJ IDEA or VS Code with Java support.
4. Install Gradle, or add a Gradle wrapper using an installed Gradle.
5. Open PowerShell in the project folder and run:
   `gradle build`
6. If build succeeds, find the remapped mod JAR under `build\libs\`.
7. Copy the JAR into the `mods` folder for your Modrinth 1.21.11 Fabric profile. Make sure Fabric API compatible with 1.21.11 is installed if the build metadata requires it.
8. Launch the profile and press Shift + Enter.

If `gradle build` fails, copy the first error block from the terminal; do not send API keys or other secrets.

## Screenmates assets
The supplied images have had near-white pixels removed and are included as transparent PNGs. The BoyKisser source was a GIF preview/screenshot, so this asset is a still frame; the watermark cannot be perfectly removed without redrawing the affected area. The Mikey cutout may retain small white edge artifacts because automatic background removal is not perfect.

## Feature roadmap for full implementation
- Connect separate global/item-specific scale sliders to item renderer mixins.
- Add custom first-person transformations for shield, totem, mace, axe, sword, and supported modded spear.
- Add texture override resources and UI selectors.
- Add animated Screenmates frame playback for BoyKisser GIF frames.
- Test on the exact 1.21.11 profile.
