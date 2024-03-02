package top.prefersmin.prmdwm.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import top.prefersmin.prmdwm.init.ModEffects;
import top.prefersmin.prmdwm.network.PacketHandler;
import top.prefersmin.prmdwm.network.packet.ChaosSwordProcedurePacket;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static top.prefersmin.prmdwm.util.EffectUtil.getRandomEffect;
import static top.prefersmin.prmdwm.util.EntityUtil.getRandomHurtEntity;
import static top.prefersmin.prmdwm.util.KeyInputUtil.isLeftCtrlPressed;

public class ChaosSwordRightClickedProcedure {

    public static void execute(LevelAccessor world, Entity entity, ItemStack itemstack) {
        if (entity != null) {
            Player player;
            double x = entity.getX();
            double y = entity.getY();
            double z = entity.getZ();

            if (isLeftCtrlPressed()) {
                Level _level;
                System.out.println("Left Ctrl");

                if (world instanceof Level) {
                    _level = (Level) world;
                    if (entity instanceof Player) {
                        player = (Player) entity;
                        // 60
                        player.getCooldowns().addCooldown(itemstack.getItem(), 1);

                        System.out.println("发包！");
                        PacketHandler.getPlayChannel().sendToServer(new ChaosSwordProcedurePacket(player.getUUID()));
                    }
                    if (!_level.isClientSide()) {
                        _level.playSound(null, BlockPos.containing(x, y, z), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.agitated"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        _level.playLocalSound(x, y, z, Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.agitated"))), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }

            } else if (entity.isShiftKeyDown()) {
                if (entity instanceof Player) {
                    player = (Player) entity;
                    // 60
                    player.getCooldowns().addCooldown(itemstack.getItem(), 1);

                    Level projectileLevel = entity.level();
                    if (!projectileLevel.isClientSide()) {
                        projectileLevel.addFreshEntity(getRandomHurtEntity(player));
                    }
                }

                if (world instanceof Level _level) {
                    if (!_level.isClientSide()) {
                        _level.playSound(null, BlockPos.containing(x, y, z), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither.shoot"))), SoundSource.PLAYERS, 0.8F, 1.0F);
                    } else {
                        _level.playLocalSound(x, y, z, Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither.shoot"))), SoundSource.PLAYERS, 0.8F, 1.0F, false);
                    }
                }
            } else {
                Level _level;
                LivingEntity _entity;
                Vec3 _center;
                List _entfound;
                Iterator var13;
                Entity entityiterator;
                ServerLevel serverLevel1;
                double raytrace_distance = 0.0;
                if (entity instanceof Player) {
                    player = (Player) entity;
                    // 400
                    player.getCooldowns().addCooldown(itemstack.getItem(), 1);
                }

                if (world instanceof Level) {
                    _level = (Level) world;
                    if (!_level.isClientSide()) {
                        _level.playSound(null, BlockPos.containing(x, y, z), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.cast_spell"))), SoundSource.PLAYERS, 4.0F, 0.8F);
                    } else {
                        _level.playLocalSound(x, y, z, Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.cast_spell"))), SoundSource.PLAYERS, 4.0F, 0.8F, false);
                    }
                }

                while (raytrace_distance <= 128.0F) {
                    raytrace_distance += 0.2;

                    if (world instanceof ServerLevel) {
                        serverLevel1 = (ServerLevel) world;
                        serverLevel1.sendParticles(ParticleTypes.GLOW, entity.getX() + Math.cos(Math.toRadians(entity.getYRot() + 90.0F)) * raytrace_distance, entity.getY() + (double) entity.getEyeHeight() + Math.sin(Math.toRadians(0.0F - entity.getXRot())) * raytrace_distance, entity.getZ() + Math.sin(Math.toRadians(entity.getYRot() + 90.0F)) * raytrace_distance, 1, 0.0, 0.0, 0.0, 0.0);
                    }

                    _center = entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance));
                    Vec3 final_center = _center;
                    _entfound = world.getEntitiesOfClass(Entity.class, (new AABB(_center, _center)).inflate(0.75), (e) -> true).stream().sorted(Comparator.comparingDouble((_entcnd) -> _entcnd.distanceToSqr(final_center))).toList();
                    var13 = _entfound.iterator();

                    while (var13.hasNext()) {
                        entityiterator = (Entity) var13.next();
                        if (entityiterator != entity) {

                            if (entityiterator instanceof LivingEntity) {
                                _entity = (LivingEntity) entityiterator;
                                if (!_entity.level().isClientSide()) {
                                    if (_entity.getEffect(ModEffects.CHAOS.get()) == null) {
                                        _entity.addEffect(new MobEffectInstance(ModEffects.CHAOS.get(), 100, 0, false, false));
                                        _entity.addEffect(new MobEffectInstance(getRandomEffect(), 100, new Random().nextInt(256), false, false));
                                        _entity.addEffect(new MobEffectInstance(getRandomEffect(), 100, new Random().nextInt(256), false, false));
                                        _entity.addEffect(new MobEffectInstance(getRandomEffect(), 100, new Random().nextInt(256), false, false));
                                    }
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
    }

}
