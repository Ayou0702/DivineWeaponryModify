package top.prefersmin.prmdwm.mixin.ChaosSword;

import divineweaponry.item.ChaosSwordItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.prefersmin.prmdwm.procedures.ChaosSwordRightClickedProcedure;
import top.prefersmin.prmdwm.procedures.ChaosSwordTickProcedure;

import java.util.List;

@Mixin(ChaosSwordItem.class)
public class ChaosSwordItemMixin extends SwordItem {

    public ChaosSwordItemMixin(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Unique
    public InteractionResultHolder<ItemStack> m_7203_(Level world, Player entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        ChaosSwordRightClickedProcedure.execute(world, entity, ar.getObject());
        return ar;
    }

    @Inject(at = @At("HEAD"), method = "m_7373_", cancellable = true, remap = false)
    public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag, CallbackInfo ci) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.literal("§6神圣武器"));
        ci.cancel();
    }

    @Unique
    public void m_6883_(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        if (selected) {
            ChaosSwordTickProcedure.execute(entity);
        }
    }

}
