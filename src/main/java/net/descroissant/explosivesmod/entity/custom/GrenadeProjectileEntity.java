package net.descroissant.explosivesmod.entity.custom;

import net.descroissant.explosivesmod.ExplosivesMod;
import net.descroissant.explosivesmod.entity.ModEntities;
import net.descroissant.explosivesmod.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.List;

public class GrenadeProjectileEntity extends ThrowableItemProjectile {
    public GrenadeProjectileEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public GrenadeProjectileEntity(Level pLevel) {
        super(ModEntities.GRENADE_PROJECTILE.get(), pLevel);
    }

    public GrenadeProjectileEntity(Level pLevel, LivingEntity livingEntity) {
        super(ModEntities.GRENADE_PROJECTILE.get(), livingEntity, pLevel);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.GRENADE.get();
    }

    public static final float explosionPower = 5.0f;

    @Override
    protected void onHit(HitResult pResult) {
        if (!this.level().isClientSide) {
            boolean flag = ForgeEventFactory.getMobGriefingEvent(this.level(), this.getOwner());
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), explosionPower, flag, Level.ExplosionInteraction.MOB);

            if (pResult.getType() != HitResult.Type.ENTITY || !this.ownedBy(((EntityHitResult)pResult).getEntity())) {
                if(!this.level().isClientSide) {
                    List<LivingEntity> affectedEntities = this.level()
                            .getEntitiesOfClass(LivingEntity.class,
                                    this.getBoundingBox().inflate(4.0, 2.0, 4.0));

                    if (!affectedEntities.isEmpty()){
                        for (int i = 0; i < affectedEntities.size(); i++) {
                            ExplosivesMod.sendMessage(affectedEntities.get(i).getName().toString());
                        }
                    }
                }
            }

            this.discard();
        }

        super.onHit(pResult);

        if (!this.level().isClientSide) {
            this.discard();
        }
    }
}
