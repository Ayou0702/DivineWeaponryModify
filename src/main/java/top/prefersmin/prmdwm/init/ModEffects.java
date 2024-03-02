package top.prefersmin.prmdwm.init;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import top.prefersmin.prmdwm.DivineWeaponryModify;
import top.prefersmin.prmdwm.effect.IncurableEffect;

public class ModEffects
{
    public static final DeferredRegister<MobEffect> REGISTER = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, DivineWeaponryModify.MODID);

    public static final RegistryObject<IncurableEffect> BLINDED = REGISTER.register("blinded", () -> new IncurableEffect(MobEffectCategory.HARMFUL, 0));

    public static final RegistryObject<IncurableEffect> PURE_DARKNESS = REGISTER.register("dark", () -> new IncurableEffect(MobEffectCategory.HARMFUL, 0));

    public static final RegistryObject<IncurableEffect> CURE = REGISTER.register("cure", () -> new IncurableEffect(MobEffectCategory.HARMFUL, 0));

    public static final RegistryObject<IncurableEffect> CHAOS = REGISTER.register("chaos", () -> new IncurableEffect(MobEffectCategory.HARMFUL, 0));


}
