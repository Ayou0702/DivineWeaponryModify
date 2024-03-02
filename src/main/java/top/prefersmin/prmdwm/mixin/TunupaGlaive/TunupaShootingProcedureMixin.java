package top.prefersmin.prmdwm.mixin.TunupaGlaive;

import divineweaponry.init.DivineWeaponryModBlocks;
import divineweaponry.procedures.TunupaShootingProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(TunupaShootingProcedure.class)
public class TunupaShootingProcedureMixin {

    @Inject(at = @At("HEAD"), method = "execute", cancellable = true, remap = false)
    private static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack, CallbackInfo ci) {
        if (entity != null) {
            double raytrace_distance;
            double X = 0.0;
            double Y = 0.0;
            double Z = 0.0;
            Player _player;
            int index1;
            ServerLevel serverLevel = null;
            if (entity.isShiftKeyDown()) {
                if (entity instanceof Player) {
                    _player = (Player) entity;
                    _player.getCooldowns().addCooldown(itemstack.getItem(), 400);
                }

                for (index1 = 0; index1 < 50; ++index1) {
                    if (world instanceof ServerLevel _serverLevel) {
                        FallingBlockEntity.fall(_serverLevel, BlockPos.containing(x + Mth.nextDouble(RandomSource.create(), -4.0, 4.0), y + Mth.nextDouble(RandomSource.create(), 24.0, 27.0), z + Mth.nextDouble(RandomSource.create(), -4.0, 4.0)), DivineWeaponryModBlocks.METEOR.get().defaultBlockState());
                    }
                }

                if (world instanceof ServerLevel) {
                    serverLevel = (ServerLevel) world;
                    if (!serverLevel.isClientSide()) {
                        serverLevel.playSound(null, BlockPos.containing(x, y, z), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.basalt.fall"))), SoundSource.PLAYERS, 3.0F, 0.5F);
                    } else {
                        serverLevel.playLocalSound(x, y, z, Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.basalt.fall"))), SoundSource.PLAYERS, 3.0F, 0.5F, false);
                    }
                    if (!serverLevel.isClientSide()) {
                        serverLevel.explode(null, x, y + 24.0, z, 4.0F, Level.ExplosionInteraction.BLOCK);
                    }

                }

            } else {
                if (world instanceof ServerLevel) {
                    serverLevel = (ServerLevel) world;
                    if (!serverLevel.isClientSide()) {
                        serverLevel.playSound(null, BlockPos.containing(x, y, z), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.firecharge.use"))), SoundSource.PLAYERS, 1.0F, 1.0F);
                    } else {
                        serverLevel.playLocalSound(x, y, z, Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.firecharge.use"))), SoundSource.PLAYERS, 1.0F, 1.0F, false);
                    }
                }

                if (entity instanceof Player) {
                    _player = (Player) entity;
                    // 100
                    _player.getCooldowns().addCooldown(itemstack.getItem(), 60);
                }

                raytrace_distance = 1.0;

                for (index1 = 0; index1 < 36; ++index1) {

                    boolean canOcclude = world.getBlockState(new BlockPos(entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getBlockPos().getX(), entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getBlockPos().getY(), entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getBlockPos().getZ())).canOcclude();

                    if (!canOcclude || raytrace_distance < 36.0) {
                        ++raytrace_distance;
                    }

                    if (canOcclude || raytrace_distance >= 36.0) {
                        X = entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getBlockPos().getX();
                        Y = entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getBlockPos().getY();
                        Z = entity.level().clip(new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getBlockPos().getZ();
                    }
                }

                if (world instanceof ServerLevel) {
                    FallingBlockEntity.fall(serverLevel, BlockPos.containing(X, Y + 14.0, Z), DivineWeaponryModBlocks.METEOR.get().defaultBlockState());
                    FallingBlockEntity.fall(serverLevel, BlockPos.containing(X, Y + 15.0, Z), DivineWeaponryModBlocks.METEOR.get().defaultBlockState());
                    FallingBlockEntity.fall(serverLevel, BlockPos.containing(X, Y + 16.0, Z), DivineWeaponryModBlocks.METEOR.get().defaultBlockState());
                    FallingBlockEntity.fall(serverLevel, BlockPos.containing(X + 1.0, Y + 15.0, Z), DivineWeaponryModBlocks.METEOR.get().defaultBlockState());
                    FallingBlockEntity.fall(serverLevel, BlockPos.containing(X - 1.0, Y + 15.0, Z), DivineWeaponryModBlocks.METEOR.get().defaultBlockState());
                    FallingBlockEntity.fall(serverLevel, BlockPos.containing(X, Y + 15.0, Z + 1.0), DivineWeaponryModBlocks.METEOR.get().defaultBlockState());
                    FallingBlockEntity.fall(serverLevel, BlockPos.containing(X, Y + 15.0, Z - 1.0), DivineWeaponryModBlocks.METEOR.get().defaultBlockState());
                }

            }

        }

        ci.cancel();
    }

}
