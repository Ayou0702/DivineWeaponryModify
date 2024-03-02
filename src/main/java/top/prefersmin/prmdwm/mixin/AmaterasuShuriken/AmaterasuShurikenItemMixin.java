package top.prefersmin.prmdwm.mixin.AmaterasuShuriken;

import divineweaponry.item.AmaterasuShurikenItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(AmaterasuShurikenItem.class)
public class AmaterasuShurikenItemMixin extends Item {

    public AmaterasuShurikenItemMixin(Properties pProperties) {
        super(pProperties);
    }

    @Inject(at = @At("HEAD"), method = "m_7373_", cancellable = true, remap = false)
    public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag, CallbackInfo ci) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.literal("§6神圣武器"));
        ci.cancel();
    }

}
