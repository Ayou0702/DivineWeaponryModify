package top.prefersmin.prmdwm.mixin.AmaterasuShuriken;

import divineweaponry.item.AmaterasuShurikenItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.prefersmin.prmdwm.procedures.AmaterasuShurikenRightClickerProcedure;

import java.util.List;

@Mixin(AmaterasuShurikenItem.class)
public class AmaterasuShurikenItemMixin extends Item {

    public AmaterasuShurikenItemMixin(Properties pProperties) {
        super(pProperties);
    }

    @Inject(at = @At("HEAD"), method = "m_7203_", cancellable = true, remap = false)
    public void m_7203_(Level world, Player entity, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        AmaterasuShurikenRightClickerProcedure.execute(world, entity, ar.getObject());
        cir.setReturnValue(ar);
    }

    @Inject(at = @At("HEAD"), method = "m_7373_", cancellable = true, remap = false)
    public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag, CallbackInfo ci) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.literal("§6神圣武器"));
        ci.cancel();
    }

    @Inject(at = @At("HEAD"), method = "m_5929_", cancellable = true, remap = false)
    public void m_5929_(Level world, LivingEntity entityLiving, ItemStack itemstack, int count, CallbackInfo ci) {
        // if (!world.isClientSide() && entityLiving instanceof ServerPlayer entity) {
        //     double x = entity.getX();
        //     double y = entity.getY();
        //     double z = entity.getZ();
        //     AmaterasuShurikenEntity entityarrow = AmaterasuShurikenEntity.shoot(world, entity, world.getRandom(), 1.0F, 6.5, 1);
        //     itemstack.hurtAndBreak(1, entity, (e) -> {
        //         e.broadcastBreakEvent(entity.getUsedItemHand());
        //     });
        //     entityarrow.pickup = AbstractArrow.Pickup.DISALLOWED;
        //     AmaterasuShurikenFireProcedure.execute(world, x, y, z, entity, itemstack);
        //     entity.releaseUsingItem();
        // }
        ci.cancel();

    }

}
