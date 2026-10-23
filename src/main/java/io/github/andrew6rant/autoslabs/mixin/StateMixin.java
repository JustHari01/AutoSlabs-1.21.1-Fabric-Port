package io.github.andrew6rant.autoslabs.mixin;

import net.minecraft.state.State;
import net.minecraft.state.property.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(State.class)
public abstract class StateMixin {
    @Inject(
        method = "get",
        cancellable = true,
        at = @At(
            value = "INVOKE",
            target = "Ljava/lang/IllegalArgumentException;<init>(Ljava/lang/String;)V",
            shift = At.Shift.BEFORE
        )
    )
    private <T extends Comparable<T>> void autoslabs$defaultMissingProperty(
        Property<T> property,
        CallbackInfoReturnable<T> cir
    ) {
        cir.setReturnValue(property.getValues().iterator().next());
    }
}
