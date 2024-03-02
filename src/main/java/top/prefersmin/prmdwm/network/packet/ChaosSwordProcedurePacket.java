package top.prefersmin.prmdwm.network.packet;


import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class ChaosSwordProcedurePacket extends PlayMessage<ChaosSwordProcedurePacket> {

    UUID playerUUID;

    public ChaosSwordProcedurePacket() {
    }

    public ChaosSwordProcedurePacket(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    @Override
    public void encode(ChaosSwordProcedurePacket chaosSwordProcedurePacket, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeUUID(chaosSwordProcedurePacket.playerUUID);
    }

    @Override
    public ChaosSwordProcedurePacket decode(FriendlyByteBuf friendlyByteBuf) {
        return new ChaosSwordProcedurePacket(friendlyByteBuf.readUUID());
    }

    @Override
    public void handle(ChaosSwordProcedurePacket chaosSwordProcedurePacket, MessageContext messageContext) {
        System.out.println("已收到发包！");
        messageContext.execute(() ->
        {

            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            ServerPlayer player = messageContext.getPlayer();

            if (player == null) {
                System.out.println("为null");
                messageContext.setHandled(false);
                return;
            }

            if (player.getUUID() != chaosSwordProcedurePacket.playerUUID) {
                System.out.println("不一致");
                messageContext.setHandled(false);
                return;
            }

            if (server.getPlayerList().getPlayer(player.getUUID()) == null) {
                System.out.println("玩家不存在");
                messageContext.setHandled(false);
                return;
            }

            System.out.println("执行！");
            Vec3 _center = new Vec3(player.getX(), player.getY(), player.getZ());
            List<Entity> entityList = player.level()
                    .getEntitiesOfClass(Entity.class, (new AABB(_center, _center))
                            .inflate(8), (e) -> !(e instanceof Player))
                    .stream()
                    .sorted(Comparator.comparingDouble((entity) -> entity.distanceToSqr(_center)))
                    .toList();

            for (Entity ent : entityList) {
                if (ent instanceof LivingEntity) {
                    ent.teleportTo(ent.getX(), ent.getY() - 400, ent.getZ());
                }
            }

        });
        messageContext.setHandled(true);
    }
}
