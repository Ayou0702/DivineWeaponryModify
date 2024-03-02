package top.prefersmin.prmdwm.mixin.OmSpear;

import divineweaponry.procedures.OmSpearQuandUneEntiteVivanteEstFrappeeAvecLoutilProcedure;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OmSpearQuandUneEntiteVivanteEstFrappeeAvecLoutilProcedure.class)
public class OmSpearQuandUneEntiteVivanteEstFrappeeAvecLoutilProcedureMixin {

    @Inject(at = @At("HEAD"), method = "execute", cancellable = true, remap = false)
    private static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity, CallbackInfo ci) {
        if (entity != null) {
            if (entity instanceof LivingEntity _entity) {
                if (!_entity.level().isClientSide()) {
                    _entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0, false, false));
                }
            }

        }
        ci.cancel();
    }

}
