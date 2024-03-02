package top.prefersmin.prmdwm.mixin.LugLongsword;

import divineweaponry.init.DivineWeaponryModMobEffects;
import divineweaponry.init.DivineWeaponryModParticleTypes;
import divineweaponry.procedures.LugLongswordBeamProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.prefersmin.prmdwm.init.ModEffects;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Mixin(LugLongswordBeamProcedure.class)
public class LugLongswordBeamProcedureMixin {

    @Inject(at = @At("HEAD"), method = "execute", cancellable = true, remap = false)
    private static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack, CallbackInfo ci) {
        if (entity != null) {
            double raytrace_distance = 0.0;
            Player _player;
            List _entfound;
            Iterator var13;
            Entity entityiterator;
            LivingEntity _entity;
            Level _level;
            ServerLevel _serverLevel;
            Vec3 _center;
            if (entity.isShiftKeyDown()) {
                if (entity instanceof Player) {
                    _player = (Player)entity;
                    // 1200
                    _player.getCooldowns().addCooldown(itemstack.getItem(), 1);
                }

                if (world instanceof Level) {
                    _level = (Level)world;
                    if (!_level.isClientSide()) {
                        _level.playSound(null, BlockPos.containing(x, y, z), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.prepare_summon"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        _level.playLocalSound(x, y, z, Objects.requireNonNull(Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.prepare_summon")))), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }

                if (world instanceof ServerLevel) {
                    _serverLevel = (ServerLevel)world;
                    _serverLevel.sendParticles(ParticleTypes.FLASH, x, y, z, 10, 0.0, 0.1, 0.0, 1.0);
                    _serverLevel.sendParticles(DivineWeaponryModParticleTypes.HYPERION_BEAM.get(), x, y, z, 200, 0.065, 50.0, 0.0, 0.065);
                }

                LivingEntity livingEntity;
                if (entity instanceof LivingEntity) {
                    livingEntity = (LivingEntity)entity;
                    if (!livingEntity.level().isClientSide()) {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 480, 1, false, false));
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 480, 0, false, false));
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 240, 1, false, false));
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 480, 0, false, false));

                    }
                }

                _center = new Vec3(x, y, z);
                Vec3 final_center = _center;
                _entfound = world.getEntitiesOfClass(Entity.class, (new AABB(_center, _center)).inflate(16.5), (e) -> true).stream().sorted(Comparator.comparingDouble((_entcnd) -> _entcnd.distanceToSqr(final_center))).toList();
                var13 = _entfound.iterator();

                while(var13.hasNext()) {
                    entityiterator = (Entity)var13.next();
                    if (entityiterator != entity && entityiterator instanceof LivingEntity) {
                        livingEntity = (LivingEntity)entityiterator;
                        if (!livingEntity.level().isClientSide()) {
                            livingEntity.addEffect(new MobEffectInstance(DivineWeaponryModMobEffects.DAZZLING.get(), 480, 0, false, false));
                        }
                    }
                }
            } else {
                if (entity instanceof Player) {
                    _player = (Player)entity;
                    _player.getCooldowns().addCooldown(itemstack.getItem(), 400);
                }

                if (world instanceof Level) {
                    _level = (Level)world;
                    if (!_level.isClientSide()) {
                        _level.playSound(null, BlockPos.containing(x, y, z), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.cast_spell"))), SoundSource.PLAYERS, 4.0F, 0.8F);
                    } else {
                        _level.playLocalSound(x, y, z, Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.cast_spell"))), SoundSource.PLAYERS, 4.0F, 0.8F, false);
                    }
                }

                while(raytrace_distance <= Math.abs(Math.floor(entity.getX() - (double)entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(64.0)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getX())) || raytrace_distance <= Math.abs(Math.floor(entity.getZ() - (double)entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(64.0)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getZ())) || raytrace_distance <= Math.abs(Math.floor(entity.getY() - (double)entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(64.0)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getY()))) {
                    raytrace_distance += 0.2;
                    if (world.getBlockState(BlockPos.containing(entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getBlockPos().getX(), (double)entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getBlockPos().getY() + 1.2, entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getBlockPos().getZ())).canOcclude()) {
                        break;
                    }

                    if (world instanceof ServerLevel) {
                        _serverLevel = (ServerLevel)world;
                        _serverLevel.sendParticles(DivineWeaponryModParticleTypes.HYPERION_BEAM.get(), entity.getX() + Math.cos(Math.toRadians(entity.getYRot() + 90.0F)) * raytrace_distance, entity.getY() + (double)entity.getEyeHeight() + Math.sin(Math.toRadians(0.0F - entity.getXRot())) * raytrace_distance, entity.getZ() + Math.sin(Math.toRadians(entity.getYRot() + 90.0F)) * raytrace_distance, 3, 0.1, 0.1, 0.1, 0.0);
                        _serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK, entity.getX() + Math.cos(Math.toRadians(entity.getYRot() + 90.0F)) * raytrace_distance, entity.getY() + (double)entity.getEyeHeight() + Math.sin(Math.toRadians(0.0F - entity.getXRot())) * raytrace_distance, entity.getZ() + Math.sin(Math.toRadians(entity.getYRot() + 90.0F)) * raytrace_distance, 1, 0.0, 0.0, 0.0, 0.0);

                    }

                    _center = new Vec3(entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getBlockPos().getX(), entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getBlockPos().getY() + 1, entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getBlockPos().getZ());
                    Vec3 final_center1 = _center;
                    _entfound = world.getEntitiesOfClass(Entity.class, (new AABB(_center, _center)).inflate(0.75), (e) -> true).stream().sorted(Comparator.comparingDouble((_entcnd) -> _entcnd.distanceToSqr(final_center1))).toList();
                    var13 = _entfound.iterator();

                    while(var13.hasNext()) {
                        entityiterator = (Entity)var13.next();
                        if (entityiterator != entity) {
                            DamageSource _damageSource = new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.PLAYER_ATTACK), entity) {
                                public Component m_6157_(LivingEntity _livingEntity) {
                                    Component _attackerName = null;
                                    Component _entityName = _livingEntity.getDisplayName();
                                    Component _itemName = null;
                                    Entity _attacker = this.getEntity();
                                    ItemStack _itemStack = ItemStack.EMPTY;
                                    if (_attacker != null) {
                                        _attackerName = _attacker.getDisplayName();
                                    }

                                    if (_attacker instanceof LivingEntity _livingAttacker) {
                                        _itemStack = _livingAttacker.getMainHandItem();
                                    }

                                    if (!_itemStack.isEmpty() && _itemStack.hasCustomHoverName()) {
                                        _itemName = _itemStack.getDisplayName();
                                    }

                                    if (_attacker != null && _itemName != null) {
                                        return Component.translatable("death.attack.player.item", _entityName, _attackerName, _itemName);
                                    } else {
                                        return _attacker != null ? Component.translatable("death.attack.player", _entityName, _attackerName) : Component.translatable("death.attack.player", _entityName);
                                    }
                                }
                            };
                            entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.PLAYER_ATTACK), entity) {
                                public Component m_6157_(LivingEntity _livingEntity) {
                                    Component _attackerName = null;
                                    Component _entityName = _livingEntity.getDisplayName();
                                    Component _itemName = null;
                                    Entity _attacker = this.getEntity();
                                    ItemStack _itemStack = ItemStack.EMPTY;
                                    if (_attacker != null) {
                                        _attackerName = _attacker.getDisplayName();
                                    }

                                    if (_attacker instanceof LivingEntity _livingAttacker) {
                                        _itemStack = _livingAttacker.getMainHandItem();
                                    }

                                    if (!_itemStack.isEmpty() && _itemStack.hasCustomHoverName()) {
                                        _itemName = _itemStack.getDisplayName();
                                    }

                                    if (_attacker != null && _itemName != null) {
                                        return Component.translatable("death.attack.player.item", _entityName, _attackerName, _itemName);
                                    } else {
                                        return _attacker != null ? Component.translatable("death.attack.player", _entityName, _attackerName) : Component.translatable("death.attack.player", _entityName);
                                    }
                                }
                            }, 40.0F);

                            if (entityiterator instanceof LivingEntity) {
                                _entity = (LivingEntity)entityiterator;
                                if (!_entity.level().isClientSide()) {
                                    _entity.addEffect(new MobEffectInstance(ModEffects.BLINDED.get(), 80, 0, false, false));
                                    _entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 80, 0, false, false));
                                }
                            }

                            if (world instanceof Level level) {
                                if (!level.isClientSide()) {
                                    level.playSound(null, BlockPos.containing(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ()), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.firework_rocket.blast"))), SoundSource.PLAYERS, 0.2F, 0.4F);
                                } else {
                                    level.playLocalSound(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ(), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.firework_rocket.blast"))), SoundSource.PLAYERS, 0.2F, 0.4F, false);
                                }
                            }
                        }
                    }
                }

                if (world instanceof ServerLevel) {
                    _serverLevel = (ServerLevel)world;
                    _serverLevel.sendParticles(ParticleTypes.FLASH, entity.getX() + Math.cos(Math.toRadians(entity.getYRot() + 90.0F)) * raytrace_distance, entity.getY() + (double)entity.getEyeHeight() + Math.sin(Math.toRadians(0.0F - entity.getXRot())) * raytrace_distance, entity.getZ() + Math.sin(Math.toRadians(entity.getYRot() + 90.0F)) * raytrace_distance, 5, 0.0, 0.0, 0.0, 0.1);
                    _serverLevel.sendParticles(DivineWeaponryModParticleTypes.HYPERION_BEAM.get(), entity.getX() + Math.cos(Math.toRadians(entity.getYRot() + 90.0F)) * raytrace_distance, entity.getY() + (double)entity.getEyeHeight() + Math.sin(Math.toRadians(0.0F - entity.getXRot())) * raytrace_distance, entity.getZ() + Math.sin(Math.toRadians(entity.getYRot() + 90.0F)) * raytrace_distance, 50, 0.2, 0.2, 0.2, 0.0);

                }

            }

        }
        ci.cancel();
    }

}
