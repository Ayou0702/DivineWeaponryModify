package top.prefersmin.prmdwm.mixin.client;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.prefersmin.prmdwm.init.ModEffects;

import java.util.Random;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = {"m_109093_"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;m_6182_(Ljava/lang/String;)V", ordinal = 0, shift = At.Shift.AFTER)}, remap = false)
    public void updateCameraAndRender(float partialTicks, long nanoTime, boolean renderWorldIn, CallbackInfo ci) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (player == null) {
            return;
        }

        MobEffectInstance blindedEffect = player.getEffect(ModEffects.BLINDED.get());
        MobEffectInstance darkEffect = player.getEffect(ModEffects.PURE_DARKNESS.get());
        if (blindedEffect != null || darkEffect != null) {
            Window window = minecraft.getWindow();
            GuiGraphics graphics = new GuiGraphics(minecraft, minecraft.renderBuffers().bufferSource());
            if (darkEffect != null) {
                float percent = Math.min((darkEffect.getDuration() / (float) 40), 1);
                graphics.fill(0, 0, window.getScreenWidth(), window.getScreenHeight(), ((int) (percent * 255 + 0.5) << 24));
            } else {
                float percent = Math.min((blindedEffect.getDuration() / (float) 40), 1);
                graphics.fill(0, 0, window.getScreenWidth(), window.getScreenHeight(), ((int) (percent * 255 + 0.5) << 24) | new Random().nextInt(16777215 + 1));
            }
        }
    }

}
