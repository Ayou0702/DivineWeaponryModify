package top.prefersmin.prmdwm;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.prefersmin.prmdwm.events.PlayerLoggedInEvent;
import top.prefersmin.prmdwm.init.ModEffects;
import top.prefersmin.prmdwm.network.PacketHandler;

@Mod(DivineWeaponryModify.MODID)
public class DivineWeaponryModify {
    public static final String MODID = "prmdwm";

    public DivineWeaponryModify() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::onCommonSetup);
        ModEffects.REGISTER.register(bus);
        MinecraftForge.EVENT_BUS.register(PlayerLoggedInEvent.class);
    }


    private void onCommonSetup(FMLCommonSetupEvent event)
    {
        event.enqueueWork(() ->
        {
            PacketHandler.init();
        });
    }

}
