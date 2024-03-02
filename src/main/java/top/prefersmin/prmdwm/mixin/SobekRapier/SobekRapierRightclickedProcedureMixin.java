package top.prefersmin.prmdwm.mixin.SobekRapier;

import divineweaponry.procedures.SobekRapierRightclickedProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SobekRapierRightclickedProcedure.class)
public class SobekRapierRightclickedProcedureMixin {

    @Inject(at = @At("HEAD"), method = "execute", cancellable = true, remap = false)
    private static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack, CallbackInfo ci) {
        if (entity != null) {
            if (world instanceof Level) {
                Level _lvl0 = (Level)world;
                if (_lvl0.isDay()) {
                    Player _player;
                    if (entity.isShiftKeyDown()) {
                        if (-58.0 <= entity.getY()) {
                            if (entity instanceof Player) {
                                _player = (Player)entity;
                                // 1000
                                _player.getCooldowns().addCooldown(itemstack.getItem(), 1);
                            }

                            if (world instanceof ServerLevel _serverworld) {
                                StructureTemplate template = _serverworld.getStructureManager().getOrCreate(new ResourceLocation("divine_weaponry", "sobek_bubble"));
                                template.placeInWorld(_serverworld, BlockPos.containing(x - 3.0, y - 1.0, z - 3.0), BlockPos.containing(x - 3.0, y - 1.0, z - 3.0), (new StructurePlaceSettings()).setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false), _serverworld.random, 3);
                            }
                        }
                    } else {
                        if (entity instanceof Player) {
                            _player = (Player)entity;
                            // 400
                            _player.getCooldowns().addCooldown(itemstack.getItem(), 1);
                        }

                        entity.getPersistentData().putBoolean("WaterWay", true);
                        entity.getPersistentData().putDouble("WaterWayExpire", 0.0);
                    }
                }
            }

        }
        ci.cancel();
    }

}
