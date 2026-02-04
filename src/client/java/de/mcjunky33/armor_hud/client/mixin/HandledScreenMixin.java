package de.mcjunky33.armor_hud.client.mixin;

import de.mcjunky33.armor_hud.client.ArmorHudOverlay;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public abstract class HandledScreenMixin {

    @Shadow protected int leftPos;
    @Shadow protected int topPos;

    @Unique
    private static final ArmorHudOverlay DURABILITY_RENDERER = new ArmorHudOverlay();

    @Inject(method = "renderTooltip", at = @At("TAIL"))
    private void onRenderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY, CallbackInfo ci) {
        Slot hoveredSlot = ((HandledScreenAccessor) this).getFocusedSlot();

        if (hoveredSlot != null && hoveredSlot.hasItem()) {
            ItemStack stack = hoveredSlot.getItem();

            if (stack.isDamageableItem()) {
                DURABILITY_RENDERER.renderInventoryDurability(guiGraphics, hoveredSlot, this.leftPos, this.topPos);
            }
        }
    }
}
