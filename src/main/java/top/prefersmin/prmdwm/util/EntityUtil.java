package top.prefersmin.prmdwm.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.LlamaSpit;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.entity.projectile.ThrownExperienceBottle;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.level.Level;

import java.util.Random;
import java.util.UUID;

public class EntityUtil {

    public static Entity getRandomHurtEntity(Player player) {

        Level level = player.level();

        // 玩家的x坐标、z坐标、视线y坐标
        double x = player.getX();
        double z = player.getZ();
        double eyeY = player.getEyeY();

        // 玩家的角度朝向
        double ax = player.getLookAngle().x;
        double ay = player.getLookAngle().y;
        double az = player.getLookAngle().z;

        switch (new Random().nextInt(11)) {

            case 0:
                ThrownEgg egg = new ThrownEgg(EntityType.EGG, level);
                egg.setPos(x, eyeY - 0.1, z);
                egg.shoot(ax, ay, az, 3.0F, 0.0F);
                egg.setNoGravity(true);
                egg.setUUID(UUID.randomUUID());
                return egg;

            case 1:
                Snowball snowball = new Snowball(EntityType.SNOWBALL, level);
                snowball.setPos(x, eyeY - 0.1, z);
                snowball.shoot(ax, ay, az, 3.0F, 0.0F);
                snowball.setNoGravity(true);
                snowball.setUUID(UUID.randomUUID());
                return snowball;

            case 2:
                Arrow arrow = new Arrow(EntityType.ARROW, level);
                arrow.setPos(x, eyeY - 0.1, z);
                arrow.shoot(ax, ay, az, 3.0F, 0.0F);
                arrow.setNoGravity(true);
                arrow.setUUID(UUID.randomUUID());
                return arrow;

            case 3:
                ThrownTrident trident = new ThrownTrident(EntityType.TRIDENT, level);
                trident.setPos(x, eyeY - 0.1, z);
                trident.shoot(ax, ay, az, 3.0F, 0.0F);
                trident.setNoGravity(true);
                trident.setUUID(UUID.randomUUID());
                level.addFreshEntity(trident);
                return trident;

            case 4:
                Projectile fireball = ((new Object() {
                    public Projectile getFireball(Level level, Entity shooter, double ax, double ay, double az) {
                        AbstractHurtingProjectile entityToSpawn = new LargeFireball(EntityType.FIREBALL, level);
                        entityToSpawn.setOwner(shooter);
                        entityToSpawn.xPower = ax;
                        entityToSpawn.yPower = ay;
                        entityToSpawn.zPower = az;
                        return entityToSpawn;
                    }
                })).getFireball(level, player, ax, ay, az);
                fireball.setPos(x, eyeY - 0.1, z);
                fireball.ignoreExplosion();
                fireball.shoot(ax, ay, az, 2.0F, 0.0F);
                return fireball;

            case 5:
                Projectile witherSkull = ((new Object() {
                    public Projectile getFireball(Level level, Entity shooter, double ax, double ay, double az) {
                        AbstractHurtingProjectile entityToSpawn = new WitherSkull(EntityType.WITHER_SKULL, level);
                        entityToSpawn.setOwner(shooter);
                        entityToSpawn.xPower = ax;
                        entityToSpawn.yPower = ay;
                        entityToSpawn.zPower = az;
                        return entityToSpawn;
                    }
                })).getFireball(level, player, ax, ay, az);
                witherSkull.setPos(x, eyeY - 0.1, z);
                witherSkull.ignoreExplosion();
                witherSkull.shoot(ax, ay, az, 2.0F, 0.0F);
                return witherSkull;

            case 6:
                Projectile dragonFireball = ((new Object() {
                    public Projectile getFireball(Level level, Entity shooter, double ax, double ay, double az) {
                        AbstractHurtingProjectile entityToSpawn = new DragonFireball(EntityType.DRAGON_FIREBALL, level);
                        entityToSpawn.setOwner(shooter);
                        entityToSpawn.xPower = ax;
                        entityToSpawn.yPower = ay;
                        entityToSpawn.zPower = az;
                        return entityToSpawn;
                    }
                })).getFireball(level, player, ax, ay, az);
                dragonFireball.setPos(x, eyeY - 0.1, z);
                dragonFireball.ignoreExplosion();
                dragonFireball.shoot(ax, ay, az, 2.0F, 0.0F);
                return dragonFireball;

            case 7:
                ShulkerBullet shulkerBullet = new ShulkerBullet(EntityType.SHULKER_BULLET, level);
                shulkerBullet.setPos(x, eyeY - 0.1, z);
                shulkerBullet.shoot(ax, ay, az, 3.0F, 0.0F);
                shulkerBullet.setNoGravity(true);
                shulkerBullet.setUUID(UUID.randomUUID());
                level.addFreshEntity(shulkerBullet);
                return shulkerBullet;

            case 8:
                LlamaSpit llamaSpit = new LlamaSpit(EntityType.LLAMA_SPIT, level);
                llamaSpit.setOwner(player);
                llamaSpit.setPos(x, eyeY - 0.1, z);
                llamaSpit.shoot(ax, ay, az, 3.0F, 0.0F);
                llamaSpit.setNoGravity(true);
                llamaSpit.setUUID(UUID.randomUUID());
                level.addFreshEntity(llamaSpit);
                return llamaSpit;

            case 9:
                ThrownExperienceBottle experienceBottle = new ThrownExperienceBottle(EntityType.EXPERIENCE_BOTTLE, level);
                experienceBottle.setPos(x, eyeY - 0.1, z);
                experienceBottle.shoot(ax, ay, az, 3.0F, 0.0F);
                experienceBottle.setNoGravity(true);
                experienceBottle.setUUID(UUID.randomUUID());
                level.addFreshEntity(experienceBottle);
                return experienceBottle;

            case 10:
                SpectralArrow spectralArrow = new SpectralArrow(EntityType.SPECTRAL_ARROW, level);
                spectralArrow.setPos(x, eyeY - 0.1, z);
                spectralArrow.shoot(ax, ay, az, 3.0F, 0.0F);
                spectralArrow.setNoGravity(true);
                spectralArrow.setUUID(UUID.randomUUID());
                level.addFreshEntity(spectralArrow);
                return spectralArrow;

        }

        EvokerFangs evokerFangs = new EvokerFangs(EntityType.EVOKER_FANGS, level);
        evokerFangs.setOwner(player);
        evokerFangs.setPos(x, eyeY - 0.1, z);
        evokerFangs.setNoGravity(true);
        level.addFreshEntity(evokerFangs);
        return evokerFangs;

    }

}
