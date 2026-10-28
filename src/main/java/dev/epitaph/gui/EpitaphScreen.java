package dev.epitaph.gui;

import dev.epitaph.config.EpitaphConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public final class EpitaphScreen extends Screen {
    private final List<ButtonWidget> toggles = new ArrayList<>();
    private int scroll = 0;
    private int panelX, panelY, panelW, panelH;

    public EpitaphScreen() { super(Text.literal("Epitaph")); }

    @Override
    protected void init() {
        toggles.clear();
        panelW = Math.min(420, this.width - 24);
        panelH = Math.min(330, this.height - 24);
        panelX = (this.width - panelW) / 2;
        panelY = (this.height - panelH) / 2;

        int left = panelX + 18;
        int right = panelX + panelW / 2 + 4;
        int bw = panelW / 2 - 26;
        int y = panelY + 66;

        addToggle("Screenmates", () -> EpitaphConfig.INSTANCE.screenmatesEnabled,
                v -> EpitaphConfig.INSTANCE.screenmatesEnabled = v, left, y, bw);
        addToggle("BoyKisser", () -> EpitaphConfig.INSTANCE.boykisserEnabled,
                v -> EpitaphConfig.INSTANCE.boykisserEnabled = v, right, y, bw);
        y += 28;
        addToggle("Mikey", () -> EpitaphConfig.INSTANCE.mikeyEnabled,
                v -> EpitaphConfig.INSTANCE.mikeyEnabled = v, left, y, bw);
        addToggle("Custom hotbar", () -> EpitaphConfig.INSTANCE.showCustomHotbar,
                v -> EpitaphConfig.INSTANCE.showCustomHotbar = v, right, y, bw);
        y += 28;
        addToggle("Shield visuals", () -> EpitaphConfig.INSTANCE.showShieldVisuals,
                v -> EpitaphConfig.INSTANCE.showShieldVisuals = v, left, y, bw);
        addToggle("Totem visuals", () -> EpitaphConfig.INSTANCE.showTotemVisuals,
                v -> EpitaphConfig.INSTANCE.showTotemVisuals = v, right, y, bw);
        y += 28;
        addToggle("Weapon visuals", () -> EpitaphConfig.INSTANCE.showWeaponVisuals,
                v -> EpitaphConfig.INSTANCE.showWeaponVisuals = v, left, y, bw);
        addToggle("Visual effects", () -> EpitaphConfig.INSTANCE.showVisualEffects,
                v -> EpitaphConfig.INSTANCE.showVisualEffects = v, right, y, bw);
        y += 42;
        addDrawableChild(new SliderWidget(left, y, bw, 20,
                Text.literal("Screenmate size"), (EpitaphConfig.INSTANCE.screenmateScale - 0.5) / 1.5) {
            @Override protected void updateMessage() {
                setMessage(Text.literal("Screenmate size: " + String.format("%.1fx", EpitaphConfig.INSTANCE.screenmateScale)));
            }
            @Override protected void applyValue() {
                EpitaphConfig.INSTANCE.screenmateScale = 0.5 + value * 1.5;
                EpitaphConfig.save();
            }
        });
        addDrawableChild(new SliderWidget(right, y, bw, 20,
                Text.literal("Weapon scale"), (EpitaphConfig.INSTANCE.weaponScale - 0.5) / 1.5) {
            @Override protected void updateMessage() {
                setMessage(Text.literal("Weapon scale: " + String.format("%.1fx", EpitaphConfig.INSTANCE.weaponScale)));
            }
            @Override protected void applyValue() {
                EpitaphConfig.INSTANCE.weaponScale = 0.5 + value * 1.5;
                EpitaphConfig.save();
            }
        });
        addDrawableChild(ButtonWidget.builder(Text.literal("Save settings"), b -> EpitaphConfig.save())
                .dimensions(left, panelY + panelH - 34, bw, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Done"), b -> close())
                .dimensions(right, panelY + panelH - 34, bw, 20).build());
    }

    private interface Getter { boolean get(); }
    private interface Setter { void set(boolean value); }

    private void addToggle(String name, Getter getter, Setter setter, int x, int y, int w) {
        ButtonWidget button = ButtonWidget.builder(label(name, getter.get()), b -> {
            setter.set(!getter.get());
            b.setMessage(label(name, getter.get()));
            EpitaphConfig.save();
        }).dimensions(x, y, w, 20).build();
        toggles.add(button);
        addDrawableChild(button);
    }

    private Text label(String name, boolean on) {
        return Text.literal(name + ": " + (on ? "ON" : "OFF"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        context.fill(0, 0, width, height, 0x99060812);
        context.fill(panelX, panelY, panelX + panelW, panelY + panelH, 0xFF111522);
        context.fill(panelX, panelY, panelX + panelW, panelY + 3, 0xFF9B5CFF);
        context.drawCenteredTextWithShadow(textRenderer, "EPITAPH", width / 2, panelY + 15, 0xFFECE7FF);
        context.drawCenteredTextWithShadow(textRenderer, "VISUAL CLIENT  •  1.0-SNAPSHOT", width / 2, panelY + 32, 0xFFAAA6C0);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override public boolean shouldPause() { return false; }

    @Override
    public void close() {
        EpitaphConfig.save();
        if (client != null) client.setScreen(null);
    }
}
