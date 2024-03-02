package top.prefersmin.prmdwm.events;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.prefersmin.prmdwm.DivineWeaponryModify;
import top.prefersmin.prmdwm.init.ModEffects;

import java.util.Comparator;
import java.util.List;

@Mod.EventBusSubscriber(modid = DivineWeaponryModify.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerTickEvent {

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (player.getEffect(ModEffects.CURE.get()) != null) {
            Vec3 _center = new Vec3(player.getX(), player.getY(), player.getZ());
            List<Entity> entityList = player.level()
                    .getEntitiesOfClass(Entity.class, (new AABB(_center, _center))
                            .inflate(64), (e) -> true)
                    .stream()
                    .sorted(Comparator.comparingDouble((entcnd) -> entcnd.distanceToSqr(_center)))
                    .toList();

            for (Entity entity : entityList) {
                LivingEntity _entity;
                if (entity instanceof LivingEntity) {
                    _entity = (LivingEntity) entity;
                    if (!_entity.level().isClientSide()) {
                        _entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 20, 0, false, false));
                    }
                }
            }
        }
    }

}
