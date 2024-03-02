package top.prefersmin.prmdwm.events;

import divineweaponry.init.DivineWeaponryModMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.prefersmin.prmdwm.DivineWeaponryModify;
import top.prefersmin.prmdwm.init.DWMModEffects;

import java.util.Comparator;
import java.util.List;

@Mod.EventBusSubscriber(modid = DivineWeaponryModify.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerTickEvent {

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {

        Player player = event.player;

        if (player.getEffect(DWMModEffects.CURE.get()) != null) {
            Vec3 _center = new Vec3(player.getX(), player.getY(), player.getZ());
            List<Player> entityList = player.level()
                    .getEntitiesOfClass(Player.class, (new AABB(_center, _center))
                            .inflate(64))
                    .stream()
                    .sorted(Comparator.comparingDouble((entcnd) -> entcnd.distanceToSqr(_center)))
                    .toList();

            for (Player entityPlayer : entityList) {
                if (!entityPlayer.level().isClientSide()) {
                    entityPlayer.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 20, 0, false, false));
                    entityPlayer.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20, 0, false, false));
                    entityPlayer.addEffect(new MobEffectInstance(DivineWeaponryModMobEffects.POISON_RESISTANCE.get(), 20, 0, false, false));
                    entityPlayer.addEffect(new MobEffectInstance(DivineWeaponryModMobEffects.WITHER_RESISTANCE.get(), 20, 0, false, false));
                    entityPlayer.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20, 1, false, false));
                }
            }
        }

    }

}
