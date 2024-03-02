package top.prefersmin.prmdwm.mixin.AmaterasuShuriken;

import divineweaponry.DivineWeaponryMod;
import divineweaponry.entity.AmaterasuShurikenEntity;
import divineweaponry.init.DivineWeaponryModEntities;
import divineweaponry.procedures.AmaterasuShurikenFireProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(AmaterasuShurikenFireProcedure.class)
public class AmaterasuShurikenFireProcedureMixin {

    @Inject(at = @At("HEAD"), method = "execute", cancellable = true, remap = false)
    private static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack, CallbackInfo ci) {

        if (entity != null) {
            Player _player;
            if (entity.isShiftKeyDown()) {
                if (entity instanceof Player) {
                    _player = (Player) entity;
                    _player.getCooldowns().addCooldown(itemstack.getItem(), 15);
                }

                DivineWeaponryMod.queueServerWork(3, () -> {
                    Level projectileLevel = entity.level();
                    if (!projectileLevel.isClientSide()) {
                        Projectile _entityToSpawn = ((new Object() {
                            public Projectile getArrow(Level level, float damage, int knockback) {
                                AbstractArrow entityToSpawn = new AmaterasuShurikenEntity(DivineWeaponryModEntities.AMATERASU_SHURIKEN.get(), level);
                                entityToSpawn.setBaseDamage(damage);
                                entityToSpawn.setKnockback(knockback);
                                entityToSpawn.setSilent(true);
                                entityToSpawn.pickup = AbstractArrow.Pickup.ALLOWED;
                                return entityToSpawn;
                            }
                        })).getArrow(projectileLevel, 7.0F, 1);
                        _entityToSpawn.setPos(entity.getX(), entity.getEyeY() - 0.1, entity.getZ());
                        _entityToSpawn.setNoGravity(true);
                        _entityToSpawn.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, 3.5F, 0.0F);
                        projectileLevel.addFreshEntity(_entityToSpawn);
                    }

                    if (world instanceof Level _level) {
                        if (!_level.isClientSide()) {
                            _level.playSound(null, BlockPos.containing(x, y, z), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.firecharge.use"))), SoundSource.PLAYERS, 1.0F, 1.0F);
                        } else {
                            _level.playLocalSound(x, y, z, Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.firecharge.use"))), SoundSource.PLAYERS, 1.0F, 1.0F, false);
                        }
                    }

                    DivineWeaponryMod.queueServerWork(3, () -> {
                        if (!projectileLevel.isClientSide()) {
                            Projectile _entityToSpawn = ((new Object() {
                                public Projectile getArrow(Level level, float damage, int knockback) {
                                    AbstractArrow entityToSpawn = new AmaterasuShurikenEntity(DivineWeaponryModEntities.AMATERASU_SHURIKEN.get(), level);
                                    entityToSpawn.setBaseDamage(damage);
                                    entityToSpawn.setKnockback(knockback);
                                    entityToSpawn.setSilent(true);
                                    entityToSpawn.pickup = AbstractArrow.Pickup.ALLOWED;
                                    return entityToSpawn;
                                }
                            })).getArrow(projectileLevel, 7.0F, 1);
                            _entityToSpawn.setPos(entity.getX(), entity.getEyeY() - 0.1, entity.getZ());
                            _entityToSpawn.setNoGravity(true);
                            _entityToSpawn.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, 3.5F, 0.0F);
                            projectileLevel.addFreshEntity(_entityToSpawn);
                        }

                        if (world instanceof Level _level) {
                            if (!_level.isClientSide()) {
                                _level.playSound(null, BlockPos.containing(x, y, z), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.firecharge.use"))), SoundSource.PLAYERS, 1.0F, 1.0F);
                            } else {
                                _level.playLocalSound(x, y, z, Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.firecharge.use"))), SoundSource.PLAYERS, 1.0F, 1.0F, false);
                            }
                        }

                    });
                });
            } else if (entity instanceof Player) {
                _player = (Player) entity;
                _player.getCooldowns().addCooldown(itemstack.getItem(), 5);
            }

        }

        ci.cancel();
    }

}
