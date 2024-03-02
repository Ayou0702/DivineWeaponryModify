package top.prefersmin.prmdwm.mixin.Fujindagger;

import divineweaponry.procedures.FujindaggerQuandUneEntiteVivanteEstFrappeeAvecLoutilProcedure;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FujindaggerQuandUneEntiteVivanteEstFrappeeAvecLoutilProcedure.class)
public class FujindaggerQuandUneEntiteVivanteEstFrappeeAvecLoutilProcedureMixin {

    @Inject(at = @At("HEAD"), method = "execute", cancellable = true, remap = false)
    private static void execute(Entity entity, Entity sourceentity, ItemStack itemstack, CallbackInfo ci) {

        if (entity != null && sourceentity != null) {
            if (sourceentity instanceof LivingEntity _entity) {
                if (!_entity.level().isClientSide()) {
                    _entity.addEffect(new MobEffectInstance(MobEffects.HEAL, 1, 0, false, false));
                }
            }
        }

        ci.cancel();
    }

}
