package me.nokko.bexment.client.packet;

import me.nokko.bexment.common.Besmirchment;
import me.nokko.bexment.common.transformation.WerepyreAccessor;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class WerepyreJumpPacket {
    public static final Identifier ID = Besmirchment.id("werepyre_jump");

    @Environment(EnvType.CLIENT)
    public static void send(){
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        ClientPlayNetworking.send(ID, buf);
    }

    public static void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler network, PacketByteBuf buf, PacketSender sender) {
        server.execute(() -> {
            player.fallDistance = 0;
            player.jump();
            ((WerepyreAccessor) player).setLastJumpTicks(0);
        });
    }
}
