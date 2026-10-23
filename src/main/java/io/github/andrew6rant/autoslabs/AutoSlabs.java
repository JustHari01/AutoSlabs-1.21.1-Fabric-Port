package io.github.andrew6rant.autoslabs;

import pers.solid.brrp.v1.fabric.api.RRPCallback;
import pers.solid.brrp.v1.api.RuntimeResourcePack;
import net.fabricmc.api.ModInitializer;
import io.github.andrew6rant.autoslabs.config.CommonConfig;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;


public class AutoSlabs implements ModInitializer {
	public static final RuntimeResourcePack AUTO_SLABS_RESOURCES = RuntimeResourcePack.create(Identifier.of("autoslabs", "resources"));

	public static final Map<PlayerEntity, SlabLockEnum> slabLockPosition = new HashMap<>();

	@Override
	public void onInitialize() {
		CommonConfig.init("auto_slabs", CommonConfig.class);
		for (Block block : Registries.BLOCK) {
			Util.registerSlab(block);
		}

		RegistryEntryAddedCallback.event(Registries.BLOCK).register((raw, id, block) -> {
			Util.registerSlab(block);
		});

		// Keep generated resources above Minecraft and mod assets, but below user resource packs.
		RRPCallback.AFTER_VANILLA.register(a -> a.add(AUTO_SLABS_RESOURCES));
		if (CommonConfig.dumpResources) {
			AUTO_SLABS_RESOURCES.dump(RuntimeResourcePack.DEFAULT_OUTPUT);
		}

		PayloadTypeRegistry.playC2S().register(SlabLockPayload.ID, SlabLockPayload.CODEC);
		ServerPlayNetworking.registerGlobalReceiver(SlabLockPayload.ID, (payload, context) ->
			slabLockPosition.put(context.player(), payload.position())
		);
	}
}
