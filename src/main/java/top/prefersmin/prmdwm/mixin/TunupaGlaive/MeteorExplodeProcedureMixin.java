package top.prefersmin.prmdwm.mixin.TunupaGlaive;

import divineweaponry.DivineWeaponryMod;
import divineweaponry.procedures.MeteorExplodeProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MeteorExplodeProcedure.class)
public class MeteorExplodeProcedureMixin {

    @Inject(at = @At("HEAD"), method = "execute", cancellable = true, remap = false)
    private static void execute(LevelAccessor world, double x, double y, double z, CallbackInfo ci) {
        world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
        DivineWeaponryMod.queueServerWork(3, () -> {
            if (world instanceof Level _level) {
                if (!_level.isClientSide()) {
                    _level.explode(null, x, y, z, 2.0F, Level.ExplosionInteraction.NONE);
                }
            }

        });
        ci.cancel();
    }

}
