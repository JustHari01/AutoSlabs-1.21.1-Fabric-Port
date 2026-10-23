package io.github.andrew6rant.autoslabs;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record SlabLockPayload(SlabLockEnum position) implements CustomPayload {
    public static final Id<SlabLockPayload> ID = new Id<>(Identifier.of("autoslabs", "slab_lock"));
    public static final PacketCodec<RegistryByteBuf, SlabLockPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT,
            payload -> payload.position().ordinal(),
            ordinal -> new SlabLockPayload(SlabLockEnum.POSITION_VALUES[ordinal])
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
