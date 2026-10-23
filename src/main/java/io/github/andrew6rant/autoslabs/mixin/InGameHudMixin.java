package io.github.andrew6rant.autoslabs.mixin;

import io.github.andrew6rant.autoslabs.RenderUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static io.github.andrew6rant.autoslabs.AutoSlabsClient.clientSlabLockPosition;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(
            method = "renderCrosshair(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/RenderTickCounter;)V",
            at = @At("TAIL")
    )
    private void autoSlabs$renderSlabCrosshairIcons(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        switch (clientSlabLockPosition) {
            case BOTTOM_SLAB -> RenderUtil.drawSlabIcon(this.client.player, context, 0, 0);
            case TOP_SLAB -> RenderUtil.drawSlabIcon(this.client.player, context, 16, 0);
            case NORTH_SLAB_VERTICAL -> RenderUtil.drawSlabIcon(this.client.player, context, 0, 16);
            case SOUTH_SLAB_VERTICAL -> RenderUtil.drawSlabIcon(this.client.player, context, 16, 16);
            case EAST_SLAB_VERTICAL -> RenderUtil.drawSlabIcon(this.client.player, context, 0, 32);
            case WEST_SLAB_VERTICAL -> RenderUtil.drawSlabIcon(this.client.player, context, 16, 32);
        }
    }
}
