package top.prefersmin.prmdwm.mixin.AmaterasuShuriken;

import divineweaponry.entity.AmaterasuShurikenEntity;
import divineweaponry.init.DivineWeaponryModEntities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(AmaterasuShurikenEntity.class)
public class AmaterasuShurikenEntityMixin {

    @Inject(at = @At("HEAD"), method = "shoot*", cancellable = true, remap = false)
    private static void shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback, CallbackInfoReturnable<AmaterasuShurikenEntity> ci) {
        AmaterasuShurikenEntity entityarrow = new AmaterasuShurikenEntity(DivineWeaponryModEntities.AMATERASU_SHURIKEN.get(), entity, world);
        entityarrow.shoot(entity.getViewVector(1.0F).x, entity.getViewVector(1.0F).y, entity.getViewVector(1.0F).z, power * 3.5F, 0.0F);
        entityarrow.setSilent(true);
        entityarrow.setCritArrow(false);
        entityarrow.setBaseDamage(damage);
        entityarrow.setKnockback(knockback);
        entityarrow.setNoGravity(true);
        entityarrow.setSecondsOnFire(100);
        world.addFreshEntity(entityarrow);
        world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.firecharge.use"))), SoundSource.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.5F + 1.0F) + power / 2.0F);

        ci.setReturnValue(entityarrow);
    }

    @Inject(at = @At("HEAD"), method = "shoot*", cancellable = true, remap = false)
    private static void shoot(LivingEntity entity, LivingEntity target, CallbackInfoReturnable<AmaterasuShurikenEntity> ci) {
        AmaterasuShurikenEntity entityarrow = new AmaterasuShurikenEntity(DivineWeaponryModEntities.AMATERASU_SHURIKEN.get(), entity, entity.level());
        double dx = target.getX() - entity.getX();
        double dy = target.getY() + (double) target.getEyeHeight() - 1.1;
        double dz = target.getZ() - entity.getZ();
        entityarrow.shoot(dx, dy - entityarrow.getY() + Math.hypot(dx, dz) * 0.20000000298023224, dz, 3.5F, 12.0F);
        entityarrow.setSilent(true);
        entityarrow.setCritArrow(false);
        entityarrow.setBaseDamage(6.5);
        entityarrow.setKnockback(1);
        entityarrow.setNoGravity(true);
        entityarrow.setSecondsOnFire(100);
        entity.level().addFreshEntity(entityarrow);
        entity.level().playSound(null, entity.getZ(), entity.getY(), entity.getZ(), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.firecharge.use"))), SoundSource.PLAYERS, 1.0F, 1.0F / (RandomSource.create().nextFloat() * 0.5F + 1.0F));

        ci.setReturnValue(entityarrow);
    }

}
