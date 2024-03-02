package top.prefersmin.prmdwm.mixin.ApophisKatana;

import divineweaponry.procedures.ApophisKatanaRightclickedProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
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

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Mixin(ApophisKatanaRightclickedProcedure.class)
public class ApophisKatanaRightclickedProcedureMixin {

    @Inject(at = @At("HEAD"), method = "execute", cancellable = true, remap = false)
    private static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack, CallbackInfo ci) {
        if (entity != null) {
            Player _player;
            Level level1;
            ServerLevel serverLevel1;
            double raytrace_distance = 0.0;
            List _entfound;
            LivingEntity _entity;
            Iterator var13;
            Entity entityiterator;
            Vec3 _center;
            if (entity.isShiftKeyDown()) {
                if (entity instanceof Player) {
                    _player = (Player) entity;
                    _player.getCooldowns().addCooldown(itemstack.getItem(), 1);
                }

                if (world instanceof Level) {
                    level1 = (Level) world;
                    if (!level1.isClientSide()) {
                        level1.playSound(null, BlockPos.containing(x, y, z), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.cast_spell"))), SoundSource.PLAYERS, 4.0F, 0.8F);
                    } else {
                        level1.playLocalSound(x, y, z, Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.cast_spell"))), SoundSource.PLAYERS, 4.0F, 0.8F, false);
                    }
                }

                while (raytrace_distance <= 64.0F) {
                    raytrace_distance += 0.2;

                    if (world instanceof ServerLevel) {
                        serverLevel1 = (ServerLevel) world;
                        serverLevel1.sendParticles(ParticleTypes.PORTAL, entity.getX() + Math.cos(Math.toRadians(entity.getYRot() + 90.0F)) * raytrace_distance, entity.getY() + (double) entity.getEyeHeight() + Math.sin(Math.toRadians(0.0F - entity.getXRot())) * raytrace_distance, entity.getZ() + Math.sin(Math.toRadians(entity.getYRot() + 90.0F)) * raytrace_distance, 1, 0.0, 0.0, 0.0, 0.0);
                    }

                    _center = entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance));
                    Vec3 final_center = _center;
                    _entfound = world.getEntitiesOfClass(Entity.class, (new AABB(_center, _center)).inflate(0.75), (e) -> true)
                            .stream()
                            .sorted(Comparator.comparingDouble((_entcnd) -> _entcnd.distanceToSqr(final_center)))
                            .toList();
                    var13 = _entfound.iterator();

                    while (var13.hasNext()) {
                        entityiterator = (Entity) var13.next();
                        if (entityiterator != entity) {

                            if (entityiterator instanceof LivingEntity) {
                                _entity = (LivingEntity) entityiterator;
                                if (!_entity.level().isClientSide()) {
                                    if (_entity instanceof Player) {
                                        if (entity instanceof Player) {
                                            _player = (Player) entity;
                                            _player.getCooldowns().addCooldown(itemstack.getItem(), 1);
                                        }
                                    }
                                    _entity.teleportTo(x,y,z);
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
                    serverLevel1 = (ServerLevel) world;
                    serverLevel1.sendParticles(ParticleTypes.PORTAL, entity.getX() + Math.cos(Math.toRadians(entity.getYRot() + 90.0F)) * raytrace_distance, entity.getY() + (double) entity.getEyeHeight() + Math.sin(Math.toRadians(0.0F - entity.getXRot())) * raytrace_distance, entity.getZ() + Math.sin(Math.toRadians(entity.getYRot() + 90.0F)) * raytrace_distance, 5, 0.0, 0.0, 0.0, 0.1);
                }

            } else {
                if (entity instanceof Player) {
                    _player = (Player) entity;
                    // 400
                    _player.getCooldowns().addCooldown(itemstack.getItem(), 1);
                }

                if (world instanceof Level) {
                    level1 = (Level) world;
                    if (!level1.isClientSide()) {
                        level1.playSound(null, BlockPos.containing(x, y, z), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.cast_spell"))), SoundSource.PLAYERS, 4.0F, 0.8F);
                    } else {
                        level1.playLocalSound(x, y, z, Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.cast_spell"))), SoundSource.PLAYERS, 4.0F, 0.8F, false);
                    }
                }

                while (raytrace_distance <= Math.abs(Math.floor(entity.getX() - (double) entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(64.0)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getX())) || raytrace_distance <= Math.abs(Math.floor(entity.getZ() - (double) entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(64.0)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getZ())) || raytrace_distance <= Math.abs(Math.floor(entity.getY() - (double) entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(64.0)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getY()))) {
                    raytrace_distance += 0.2;
                    if (world.getBlockState(BlockPos.containing(entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getBlockPos().getX(), (double) entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getBlockPos().getY() + 1.2, entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getBlockPos().getZ())).canOcclude()) {
                        break;
                    }

                    if (world instanceof ServerLevel) {
                        serverLevel1 = (ServerLevel) world;
                        serverLevel1.sendParticles(ParticleTypes.GLOW, entity.getX() + Math.cos(Math.toRadians(entity.getYRot() + 90.0F)) * raytrace_distance, entity.getY() + (double) entity.getEyeHeight() + Math.sin(Math.toRadians(0.0F - entity.getXRot())) * raytrace_distance, entity.getZ() + Math.sin(Math.toRadians(entity.getYRot() + 90.0F)) * raytrace_distance, 1, 0.0, 0.0, 0.0, 0.0);

                    }

                    _center = new Vec3(entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getBlockPos().getX(), entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getBlockPos().getY() + 1, entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getBlockPos().getZ());
                    Vec3 final_center = _center;
                    _entfound = world.getEntitiesOfClass(Entity.class, (new AABB(_center, _center)).inflate(0.75), (e) -> true).stream().sorted(Comparator.comparingDouble((_entcnd) -> _entcnd.distanceToSqr(final_center))).toList();
                    var13 = _entfound.iterator();

                    while (var13.hasNext()) {
                        entityiterator = (Entity) var13.next();
                        if (entityiterator != entity) {

                            if (entityiterator instanceof LivingEntity) {
                                _entity = (LivingEntity) entityiterator;
                                if (!_entity.level().isClientSide()) {
                                    _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 255, false, false));
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
                    serverLevel1 = (ServerLevel) world;
                    serverLevel1.sendParticles(ParticleTypes.GLOW, entity.getX() + Math.cos(Math.toRadians(entity.getYRot() + 90.0F)) * raytrace_distance, entity.getY() + (double) entity.getEyeHeight() + Math.sin(Math.toRadians(0.0F - entity.getXRot())) * raytrace_distance, entity.getZ() + Math.sin(Math.toRadians(entity.getYRot() + 90.0F)) * raytrace_distance, 5, 0.0, 0.0, 0.0, 0.1);
                }

            }

        }

        ci.cancel();
    }

}
