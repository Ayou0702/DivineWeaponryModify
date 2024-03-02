package top.prefersmin.prmdwm.mixin.ZeusCutlass;

import divineweaponry.DivineWeaponryMod;
import divineweaponry.init.DivineWeaponryModAttributes;
import divineweaponry.init.DivineWeaponryModParticleTypes;
import divineweaponry.procedures.ZeusCutlassRightclickedProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Mixin(ZeusCutlassRightclickedProcedure.class)
public class ZeusCutlassRightclickedProcedureMixin {

    @Inject(at = @At("HEAD"), method = "execute", cancellable = true, remap = false)
    private static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack, CallbackInfo ci) {
        if (entity != null) {
            double raytrace_distance = 0.0;
            Player _player;
            Level _level;
            if (entity.isShiftKeyDown()) {
                if (entity instanceof Player) {
                    _player = (Player) entity;
                    // 900
                    _player.getCooldowns().addCooldown(itemstack.getItem(), 1);
                }

                if (world instanceof Level) {
                    _level = (Level) world;
                    if (!_level.isClientSide()) {
                        _level.playSound(null, BlockPos.containing(x, y, z), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.prepare_attack"))), SoundSource.PLAYERS, 4.0F, 0.8F);
                    } else {
                        _level.playLocalSound(x, y, z, Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.prepare_attack"))), SoundSource.PLAYERS, 4.0F, 0.8F, false);
                    }
                }

                Objects.requireNonNull(((LivingEntity) entity).getAttribute(DivineWeaponryModAttributes.ZEUSRELAY.get())).setBaseValue(2.0);
                DivineWeaponryMod.queueServerWork(600, () -> Objects.requireNonNull(((LivingEntity) entity).getAttribute(DivineWeaponryModAttributes.ZEUSRELAY.get())).setBaseValue(0.0));

                while (raytrace_distance <= Math.abs(Math.floor(entity.getX() - (double) entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(64.0)), Block.OUTLINE, Fluid.NONE, entity)).getBlockPos().getX())) || raytrace_distance <= Math.abs(Math.floor(entity.getZ() - (double) entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(64.0)), Block.OUTLINE, Fluid.NONE, entity)).getBlockPos().getZ())) || raytrace_distance <= Math.abs(Math.floor(entity.getY() - (double) entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(64.0)), Block.OUTLINE, Fluid.NONE, entity)).getBlockPos().getY()))) {
                    raytrace_distance += 0.2;
                    if (world.getBlockState(BlockPos.containing(entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), Block.COLLIDER, Fluid.NONE, entity)).getBlockPos().getX(), (double) entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), Block.COLLIDER, Fluid.NONE, entity)).getBlockPos().getY() + 1.2, entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), Block.COLLIDER, Fluid.NONE, entity)).getBlockPos().getZ())).canOcclude()) {
                        return;
                    }

                    if (world instanceof ServerLevel _serverLevel) {
                        _serverLevel.sendParticles(DivineWeaponryModParticleTypes.BOLT.get(), entity.getX() + Math.cos(Math.toRadians(entity.getYRot() + 90.0F)) * raytrace_distance, entity.getY() + (double) entity.getEyeHeight() + Math.sin(Math.toRadians(0.0F - entity.getXRot())) * raytrace_distance, entity.getZ() + Math.sin(Math.toRadians(entity.getYRot() + 90.0F)) * raytrace_distance, 2, 0.0, 0.0, 0.0, 0.0);
                    }

                    Vec3 _center = new Vec3(entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), Block.COLLIDER, Fluid.NONE, entity)).getBlockPos().getX(), entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), Block.COLLIDER, Fluid.NONE, entity)).getBlockPos().getY() + 1, entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), Block.COLLIDER, Fluid.NONE, entity)).getBlockPos().getZ());
                    List<LivingEntity> _entfound = world.getEntitiesOfClass(LivingEntity.class, (new AABB(_center, _center)).inflate(0.75), (e) -> true).stream().sorted(Comparator.comparingDouble((_entcnd) -> _entcnd.distanceToSqr(_center))).toList();

                    for (Entity entityiterator : _entfound) {
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
                            }, 50.0F);

                            Objects.requireNonNull(((LivingEntity) entityiterator).getAttribute(DivineWeaponryModAttributes.ZEUSRELAY.get())).setBaseValue(1.0);
                            break;
                        }
                    }
                }

            } else {
                if (entity instanceof Player) {
                    _player = (Player) entity;
                    // 200
                    _player.getCooldowns().addCooldown(itemstack.getItem(), 1);
                }

                raytrace_distance = 0.0;

                for (int index1 = 0; index1 < 56; ++index1) {
                    if (!world.getBlockState(new BlockPos(entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), Block.COLLIDER, Fluid.NONE, entity)).getBlockPos().getX(), entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), Block.COLLIDER, Fluid.NONE, entity)).getBlockPos().getY(), entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), Block.COLLIDER, Fluid.NONE, entity)).getBlockPos().getZ())).canOcclude() || raytrace_distance < 76.0) {
                        raytrace_distance += 0.2;
                        if (world instanceof ServerLevel serverLevel) {
                            serverLevel.sendParticles(DivineWeaponryModParticleTypes.BOLT.get(), entity.getX() + Math.cos(Math.toRadians(entity.getYRot() + 90.0F)) * raytrace_distance, entity.getY() + 0.8, entity.getZ() + Math.sin(Math.toRadians(entity.getYRot() + 90.0F)) * raytrace_distance, 16, 0.0, 0.4, 0.0, 0.0);
                        }

                        entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * 2.8, 0.1, entity.getLookAngle().z * 2.8));
                    }
                }

                if (world instanceof Level) {
                    _level = (Level) world;
                    if (!_level.isClientSide()) {
                        _level.playSound(null, new BlockPos(entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), Block.COLLIDER, Fluid.NONE, entity)).getBlockPos().getX(), entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), Block.COLLIDER, Fluid.NONE, entity)).getBlockPos().getY(), entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), Block.COLLIDER, Fluid.NONE, entity)).getBlockPos().getZ()), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.lightning_bolt.thunder"))), SoundSource.PLAYERS, 1.0F, 0.5F);
                    } else {
                        _level.playLocalSound(entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), Block.COLLIDER, Fluid.NONE, entity)).getBlockPos().getX(), entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), Block.COLLIDER, Fluid.NONE, entity)).getBlockPos().getY(), entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), Block.COLLIDER, Fluid.NONE, entity)).getBlockPos().getZ(), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.lightning_bolt.thunder"))), SoundSource.PLAYERS, 1.0F, 0.5F, false);
                    }
                }
            }

        }
        ci.cancel();
    }

}
