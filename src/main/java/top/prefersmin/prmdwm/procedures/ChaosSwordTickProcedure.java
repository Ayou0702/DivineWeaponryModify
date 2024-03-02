package top.prefersmin.prmdwm.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class ChaosSwordTickProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            if (entity instanceof LivingEntity _entity) {
                if (!_entity.level().isClientSide()) {
                    _entity.removeAllEffects();
                }
            }
        }
    }

}
