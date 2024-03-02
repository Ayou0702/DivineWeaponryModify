package top.prefersmin.prmdwm.mixin.ErebusScythe;

import divineweaponry.DivineWeaponryMod;
import divineweaponry.init.DivineWeaponryModAttributes;
import divineweaponry.init.DivineWeaponryModEntities;
import divineweaponry.procedures.ErebusScytheEvenementAuClicDroitDansLairProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ErebusScytheEvenementAuClicDroitDansLairProcedure.class)
public class ErebusScytheEvenementAuClicDroitDansLairProcedureMixin {

    @Inject(at = @At("HEAD"), method = "execute", cancellable = true, remap = false)
    private static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack, CallbackInfo ci) {
        if (entity != null) {
            Player _player;
            if (entity.isShiftKeyDown()) {
                if (entity instanceof Player) {
                    _player = (Player)entity;
                    // 1200
                    _player.getCooldowns().addCooldown(itemstack.getItem(), 1);
                }

                ((LivingEntity)entity).getAttribute(DivineWeaponryModAttributes.EREBUSFLIGHT.get()).setBaseValue(1.0);
                DivineWeaponryMod.queueServerWork(800, () -> {
                    ((LivingEntity)entity).getAttribute(DivineWeaponryModAttributes.EREBUSFLIGHT.get()).setBaseValue(0.0);
                });
                Entity _entityToSpawn;
                Mob _mobToSpawn;
                Player _owner;
                ServerLevel _level;
                TamableAnimal _toTame;
                if (world instanceof ServerLevel) {
                    _level = (ServerLevel)world;
                    _entityToSpawn = ((EntityType) DivineWeaponryModEntities.EREBUS_WITHER.get()).create(_level);
                    _entityToSpawn.moveTo(x, y, z, world.getRandom().nextFloat() * 360.0F, 0.0F);
                    if (_entityToSpawn instanceof Mob) {
                        _mobToSpawn = (Mob)_entityToSpawn;
                        _mobToSpawn.finalizeSpawn(_level, _level.getCurrentDifficultyAt(_entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                    }

                    if (_entityToSpawn instanceof TamableAnimal) {
                        _toTame = (TamableAnimal)_entityToSpawn;
                        if (entity instanceof Player) {
                            _owner = (Player)entity;
                            _toTame.tame(_owner);
                        }
                    }

                    ((LivingEntity)_entityToSpawn).getAttribute(DivineWeaponryModAttributes.EREBUSWITHERDIRECTION.get()).setBaseValue(1.0);
                    _level.addFreshEntity(_entityToSpawn);
                }

                if (world instanceof ServerLevel) {
                    _level = (ServerLevel)world;
                    _entityToSpawn = ((EntityType)DivineWeaponryModEntities.EREBUS_WITHER.get()).create(_level);
                    _entityToSpawn.moveTo(x, y, z, world.getRandom().nextFloat() * 360.0F, 0.0F);
                    if (_entityToSpawn instanceof Mob) {
                        _mobToSpawn = (Mob)_entityToSpawn;
                        _mobToSpawn.finalizeSpawn(_level, _level.getCurrentDifficultyAt(_entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                    }

                    if (_entityToSpawn instanceof TamableAnimal) {
                        _toTame = (TamableAnimal)_entityToSpawn;
                        if (entity instanceof Player) {
                            _owner = (Player)entity;
                            _toTame.tame(_owner);
                        }
                    }

                    ((LivingEntity)_entityToSpawn).getAttribute(DivineWeaponryModAttributes.EREBUSWITHERDIRECTION.get()).setBaseValue(2.0);
                    _level.addFreshEntity(_entityToSpawn);
                }
            } else {
                if (entity instanceof Player) {
                    _player = (Player)entity;
                    // 60
                    _player.getCooldowns().addCooldown(itemstack.getItem(), 1);
                }

                Level projectileLevel = entity.level();
                if (!projectileLevel.isClientSide()) {
                    Projectile _entityToSpawn = ((new Object() {
                        public Projectile getFireball(Level level, Entity shooter, double ax, double ay, double az) {
                            AbstractHurtingProjectile entityToSpawn = new WitherSkull(EntityType.WITHER_SKULL, level);
                            entityToSpawn.setOwner(shooter);
                            entityToSpawn.xPower = ax;
                            entityToSpawn.yPower = ay;
                            entityToSpawn.zPower = az;
                            return entityToSpawn;
                        }
                    })).getFireball(projectileLevel, entity, entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z);
                    _entityToSpawn.setPos(entity.getX(), entity.getEyeY() - 0.1, entity.getZ());
                    _entityToSpawn.ignoreExplosion();
                    _entityToSpawn.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, 1.0F, 0.0F);
                    projectileLevel.addFreshEntity(_entityToSpawn);
                }

                if (world instanceof Level) {
                    Level _level = (Level)world;
                    if (!_level.isClientSide()) {
                        _level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither.shoot")), SoundSource.PLAYERS, 0.8F, 1.0F);
                    } else {
                        _level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither.shoot")), SoundSource.PLAYERS, 0.8F, 1.0F, false);
                    }
                }
            }

        }
        ci.cancel();
    }

}
