package top.prefersmin.prmdwm.events;

import divineweaponry.DivineWeaponryMod;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.prefersmin.prmdwm.DivineWeaponryModify;
import top.prefersmin.prmdwm.init.DWMModEffects;

@Mod.EventBusSubscriber(modid = DivineWeaponryModify.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerLoggedInEvent {

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        MobEffectInstance instance = player.getEffect(DWMModEffects.CURE.get());
        if (instance != null) {
            PlayerTickEvent playerTickEvent = new PlayerTickEvent();
            MinecraftForge.EVENT_BUS.register(playerTickEvent);
            DivineWeaponryMod.queueServerWork(instance.getDuration(), () -> {
                MinecraftForge.EVENT_BUS.unregister(playerTickEvent);
            });
        }
    }

}
