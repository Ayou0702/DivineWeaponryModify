package top.prefersmin.prmdwm.util;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.registries.ForgeRegistries;
import top.prefersmin.prmdwm.init.ModEffects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EffectUtil {

    private static final List<MobEffect> effectList = new ArrayList<>(ForgeRegistries.MOB_EFFECTS.getValues());

    public static MobEffect getRandomEffect() {

        if (effectList.isEmpty()) {
            return MobEffects.GLOWING;
        }

        MobEffect effect;

        do {
            Random random = new Random();
            int index = random.nextInt(effectList.size());
            effect = effectList.get(index);
        } while (effect == ModEffects.CHAOS.get());

        System.out.println(effect.getDescriptionId());

        return effect;

    }

}
