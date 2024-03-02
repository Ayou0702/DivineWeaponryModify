package top.prefersmin.prmdwm.mixin.ApophisKatana;

import divineweaponry.procedures.ApophisKatanaQuandUneEntiteVivanteEstFrappeeAvecLoutilProcedure;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ApophisKatanaQuandUneEntiteVivanteEstFrappeeAvecLoutilProcedure.class)
public class ApophisKatanaQuandUneEntiteVivanteEstFrappeeAvecLoutilProcedureMixin {

    @Inject(at = @At("HEAD"), method = "execute", cancellable = true, remap = false)
    private static void execute(Entity entity, CallbackInfo ci) {
        if (entity != null) {
            if (entity instanceof LivingEntity) {
                LivingEntity _entity = (LivingEntity)entity;
                if (!_entity.level().isClientSide()) {
                    _entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 0, false, false));
                }
            }

        }
        ci.cancel();
    }

}
