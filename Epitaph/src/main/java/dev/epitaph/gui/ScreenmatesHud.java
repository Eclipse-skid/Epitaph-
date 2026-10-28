package dev.epitaph.gui;

import dev.epitaph.config.EpitaphConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public final class ScreenmatesHud {
    private static final Identifier BOYKISSER = Identifier.of("epitaph", "textures/gui/screenmates/boykisser.png");
    private static final Identifier MIKEY = Identifier.of("epitaph", "textures/gui/screenmates/mikey.png");

    private ScreenmatesHud() {}

    public static void render(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.options.hudHidden || !EpitaphConfig.INSTANCE.screenmatesEnabled) return;

        int centerX = client.getWindow().getScaledWidth() / 2;
        int hotbarY = client.getWindow().getScaledHeight() - 22;
        int size = (int) Math.round(32 * EpitaphConfig.INSTANCE.screenmateScale);

        if (EpitaphConfig.INSTANCE.boykisserEnabled) {
            int x = centerX - 92 - size / 2;
            int y = hotbarY - size + (int) EpitaphConfig.INSTANCE.boykisserY;
            drawIfPresent(client, context, BOYKISSER, x, y, size);
        }
        if (EpitaphConfig.INSTANCE.mikeyEnabled) {
            int x = centerX + 60 - size / 2;
            int y = hotbarY - size + (int) EpitaphConfig.INSTANCE.mikeyY;
            drawIfPresent(client, context, MIKEY, x, y, size);
        }
    }

    private static void drawIfPresent(MinecraftClient client, DrawContext context, Identifier id, int x, int y, int size) {
        if (client.getResourceManager().getResource(id).isPresent()) {
            context.drawTexture(id, x, y, 0, 0, size, size, size, size);
        }
    }
}
