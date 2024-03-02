package top.prefersmin.prmdwm.mixin.CernunnosSickle;

import divineweaponry.DivineWeaponryMod;
import divineweaponry.procedures.EarthSickleLorsqueVousCliquezAvecLeBoutonDroitDeLaSourisSurUnBlocProcedure;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.prefersmin.prmdwm.events.PlayerTickEvent;
import top.prefersmin.prmdwm.init.DWMModEffects;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Mixin(EarthSickleLorsqueVousCliquezAvecLeBoutonDroitDeLaSourisSurUnBlocProcedure.class)
public class EarthSickleLorsqueVousCliquezAvecLeBoutonDroitDeLaSourisSurUnBlocProcedureMixin {

    @Inject(at = @At("HEAD"), method = "execute", cancellable = true, remap = false)
    private static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack, CallbackInfo ci) {
        if (entity != null) {
            Player _player;
            Level _level;

            if (entity.isShiftKeyDown()) {
                if (entity instanceof Player) {
                    _player = (Player) entity;
                    _player.getCooldowns().addCooldown(itemstack.getItem(), 2400);
                }

                if (world instanceof Level) {
                    _level = (Level) world;
                    if (!_level.isClientSide()) {
                        _level.playSound(null, BlockPos.containing(x, y, z), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.agitated"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        _level.playLocalSound(x, y, z, Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.agitated"))), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }

                if (entity instanceof LivingEntity) {
                    LivingEntity _entity = (LivingEntity) entity;
                    if (!entity.level().isClientSide()) {
                        PlayerTickEvent playerTickEvent = new PlayerTickEvent();
                        MinecraftForge.EVENT_BUS.register(playerTickEvent);
                        _entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 1200, 0, false, false));
                        _entity.addEffect(new MobEffectInstance(DWMModEffects.CURE.get(), 600, 0, false, false));
                        DivineWeaponryMod.queueServerWork(600, () -> {
                            MinecraftForge.EVENT_BUS.unregister(playerTickEvent);
                        });
                    }
                }

            } else {
                LivingEntity _entity;
                Vec3 _center;
                List _entfound;
                Iterator var13;
                Entity entityiterator;
                ServerLevel serverLevel1;
                double raytrace_distance = 0.0;
                if (entity instanceof Player) {
                    _player = (Player) entity;
                    _player.getCooldowns().addCooldown(itemstack.getItem(), 500);
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
                        serverLevel1.sendParticles(ParticleTypes.SCRAPE, entity.getX() + Math.cos(Math.toRadians(entity.getYRot() + 90.0F)) * raytrace_distance, entity.getY() + (double) entity.getEyeHeight() + Math.sin(Math.toRadians(0.0F - entity.getXRot())) * raytrace_distance, entity.getZ() + Math.sin(Math.toRadians(entity.getYRot() + 90.0F)) * raytrace_distance, 1, 0.0, 0.0, 0.0, 0.0);
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
                                    _entity.removeAllEffects();
                                    _entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200, 0, false, false));
                                    _entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20, 255, false, false));
                                    _entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 2, false, false));
                                    _entity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 200, 1, false, false));
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
                    serverLevel1.sendParticles(ParticleTypes.SCRAPE, entity.getX() + Math.cos(Math.toRadians(entity.getYRot() + 90.0F)) * raytrace_distance, entity.getY() + (double) entity.getEyeHeight() + Math.sin(Math.toRadians(0.0F - entity.getXRot())) * raytrace_distance, entity.getZ() + Math.sin(Math.toRadians(entity.getYRot() + 90.0F)) * raytrace_distance, 5, 0.0, 0.0, 0.0, 0.1);
                }
            }

        }
        ci.cancel();
    }

}
