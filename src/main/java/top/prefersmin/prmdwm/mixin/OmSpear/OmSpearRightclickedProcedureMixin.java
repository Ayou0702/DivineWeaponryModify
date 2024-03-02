package top.prefersmin.prmdwm.mixin.OmSpear;

import divineweaponry.DivineWeaponryMod;
import divineweaponry.procedures.OmSpearRightclickedProcedure;
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
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Mixin(OmSpearRightclickedProcedure.class)
public class OmSpearRightclickedProcedureMixin {

    @Inject(at = @At("HEAD"), method = "execute", cancellable = true, remap = false)
    private static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack, CallbackInfo ci) {
        if (entity != null) {
            double raytrace_distance = 0.0;
            Player _player;
            Level _level;
            if (entity.isShiftKeyDown()) {
                if (entity instanceof Player) {
                    _player = (Player)entity;
                    _player.getCooldowns().addCooldown(itemstack.getItem(), 500);
                }

                if (world instanceof Level) {
                    _level = (Level)world;
                    if (!_level.isClientSide()) {
                        _level.playSound(null, BlockPos.containing(x, y, z), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.agitated"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        _level.playLocalSound(x, y, z, Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.agitated"))), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }

                if (entity instanceof LivingEntity _entity) {
                    if (!_entity.level().isClientSide()) {
                        _entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 420, 1, false, false));
                    }
                }

                DivineWeaponryMod.queueServerWork(5, () -> {
                    Vec3 _center = new Vec3(x, y, z);
                    List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, (new AABB(_center, _center)).inflate(32.5), (e) -> true).stream().sorted(Comparator.comparingDouble((_entcnd) -> _entcnd.distanceToSqr(_center))).toList();

                    for (Entity entityiterator : _entfound) {
                        if (entity != entityiterator) {
                            LivingEntity _entity;
                            if (entityiterator instanceof LivingEntity) {
                                _entity = (LivingEntity) entityiterator;
                                if (!_entity.level().isClientSide()) {
                                    _entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 415, 1, false, false));
                                    _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 415, 0, false, false));
                                    _entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 415, 0, false, false));
                                }
                            }
                        }
                    }

                });
            } else {
                if (entity instanceof Player) {
                    _player = (Player)entity;
                    _player.getCooldowns().addCooldown(itemstack.getItem(), 1000);
                }

                if (world instanceof Level) {
                    _level = (Level)world;
                    if (!_level.isClientSide()) {
                        _level.playSound(null, BlockPos.containing(x, y, z), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.sonic_boom"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        _level.playLocalSound(x, y, z, Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.sonic_boom"))), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }

                Scoreboard _sc;
                Objective _so;
                while(raytrace_distance <= Math.abs(Math.floor(entity.getX() - (double)entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(64.0)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getX())) || raytrace_distance <= Math.abs(Math.floor(entity.getZ() - (double)entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(64.0)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getZ())) || raytrace_distance <= Math.abs(Math.floor(entity.getY() - (double)entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(64.0)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getY()))) {
                    raytrace_distance += 0.2;
                    if (world.getBlockState(BlockPos.containing(entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getX(), (double)entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getY() + 1.2, entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getZ())).canOcclude()) {
                        break;
                    }

                    if (((new Object() {
                        public int getScore(String score, Entity _ent) {
                            Scoreboard _sc = _ent.level().getScoreboard();
                            Objective _so = _sc.getObjective(score);
                            return _so != null ? _sc.getOrCreatePlayerScore(_ent.getScoreboardName(), _so).getScore() : 0;
                        }
                    })).getScore("divine_omspearight", entity) >= 10) {
                        if (world instanceof ServerLevel serverLevel) {
                            serverLevel.sendParticles(ParticleTypes.SONIC_BOOM, entity.getX() + Math.cos(Math.toRadians(entity.getYRot() + 90.0F)) * raytrace_distance, entity.getY() + 1.2 + Math.sin(Math.toRadians(0.0F - entity.getXRot())) * raytrace_distance, entity.getZ() + Math.sin(Math.toRadians(entity.getYRot() + 90.0F)) * raytrace_distance, 1, 0.0, 0.0, 0.0, 0.0);
                        }

                        _sc = entity.level().getScoreboard();
                        _so = _sc.getObjective("divine_omspearight");
                        if (_so == null) {
                            _so = _sc.addObjective("divine_omspearight", ObjectiveCriteria.DUMMY, Component.literal("divine_omspearight"), ObjectiveCriteria.RenderType.INTEGER);
                        }

                        _sc.getOrCreatePlayerScore(entity.getScoreboardName(), _so).setScore(0);
                    } else {
                        _sc = entity.level().getScoreboard();
                        _so = _sc.getObjective("divine_omspearight");
                        if (_so == null) {
                            _so = _sc.addObjective("divine_omspearight", ObjectiveCriteria.DUMMY, Component.literal("divine_omspearight"), ObjectiveCriteria.RenderType.INTEGER);
                        }

                        _sc.getOrCreatePlayerScore(entity.getScoreboardName(), _so).setScore(((new Object() {
                            public int getScore(String score, Entity _ent) {
                                Scoreboard _sc = _ent.level().getScoreboard();
                                Objective _so = _sc.getObjective(score);
                                return _so != null ? _sc.getOrCreatePlayerScore(_ent.getScoreboardName(), _so).getScore() : 0;
                            }
                        })).getScore("divine_omspearight", entity) + 1);
                    }

                    Vec3 _center = new Vec3(entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getX(), (double)entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getY() + 1.2, entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getZ());
                    List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, (new AABB(_center, _center)).inflate(0.75), (e) -> true).stream().sorted(Comparator.comparingDouble((_entcnd) -> _entcnd.distanceToSqr(_center))).toList();

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
                            }, 60.0F);

                            if (entityiterator instanceof LivingEntity _entity) {
                                if (!_entity.level().isClientSide()) {
                                    _entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0, false, false));
                                }
                            }
                        }
                    }
                }

                _sc = entity.level().getScoreboard();
                _so = _sc.getObjective("divine_omspearight");
                if (_so == null) {
                    _so = _sc.addObjective("divine_omspearight", ObjectiveCriteria.DUMMY, Component.literal("divine_omspearight"), ObjectiveCriteria.RenderType.INTEGER);
                }

                _sc.getOrCreatePlayerScore(entity.getScoreboardName(), _so).setScore(0);
            }

        }
        ci.cancel();
    }

}
