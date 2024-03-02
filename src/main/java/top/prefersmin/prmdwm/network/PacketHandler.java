package top.prefersmin.prmdwm.network;

import com.mrcrayfish.framework.api.FrameworkAPI;
import com.mrcrayfish.framework.api.network.FrameworkNetwork;
import com.mrcrayfish.framework.api.network.MessageDirection;
import net.minecraft.resources.ResourceLocation;
import top.prefersmin.prmdwm.DivineWeaponryModify;
import top.prefersmin.prmdwm.network.packet.ChaosSwordProcedurePacket;

public class PacketHandler {

    private static FrameworkNetwork playChannel;

    public static void init()
    {
        playChannel = FrameworkAPI.createNetworkBuilder(new ResourceLocation(DivineWeaponryModify.MODID, "play"), 1)
                .registerPlayMessage(ChaosSwordProcedurePacket.class, MessageDirection.PLAY_SERVER_BOUND)
                .build();
    }

    public static FrameworkNetwork getPlayChannel()
    {
        return playChannel;
    }

}
