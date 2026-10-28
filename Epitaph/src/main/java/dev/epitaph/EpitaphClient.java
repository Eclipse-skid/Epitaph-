package dev.epitaph;

import dev.epitaph.config.EpitaphConfig;
import dev.epitaph.gui.EpitaphScreen;
import dev.epitaph.gui.ScreenmatesHud;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public final class EpitaphClient implements ClientModInitializer {
    private static boolean wasDown;

    @Override
    public void onInitializeClient() {
        EpitaphConfig.load();
        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> ScreenmatesHud.render(drawContext));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.getWindow() == null) return;
            long handle = client.getWindow().getHandle();
            boolean down = InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_LEFT_SHIFT)
                    || InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_RIGHT_SHIFT);
            boolean enter = InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_ENTER)
                    || InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_KP_ENTER);
            boolean combo = down && enter;
            if (combo && !wasDown && client.currentScreen == null) {
                client.setScreen(new EpitaphScreen());
            }
            wasDown = combo;
        });
    }
}
