package io.github.andrew6rant.autoslabs.mixin;

import io.github.andrew6rant.autoslabs.Util;
import net.minecraft.block.SlabBlock;
import net.minecraft.state.State;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StateManager.Builder.class)
public abstract class StateManagerBuilderMixin<O, S extends State<O, S>> {
    @Shadow
    public abstract StateManager.Builder<O, S> add(Property<?>... properties);

    @Inject(method = "<init>", at = @At("RETURN"))
    private void autoslabs$addVerticalSlabProperty(O owner, CallbackInfo ci) {
        if (owner instanceof SlabBlock) {
            add(Util.VERTICAL_TYPE);
        }
    }
}
