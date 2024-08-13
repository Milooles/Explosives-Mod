package net.descroissant.explosivesmod.entity.custom;

import net.descroissant.explosivesmod.entity.ModEntities;
import net.descroissant.explosivesmod.item.ModItems;
import net.descroissant.explosivesmod.item.custom.GrenadeItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

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

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        if(!this.level().isClientSide()){
            this.level().broadcastEntityEvent(this, ((byte) 3));
            this.sendSystemMessage(Component.literal("Grenade hit a block!"));
        }

        super.onHitBlock(pResult);
    }
}
