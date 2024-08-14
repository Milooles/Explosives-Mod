package net.descroissant.explosivesmod.item.custom.throwables;

import net.descroissant.explosivesmod.ExplosivesMod;
import net.descroissant.explosivesmod.entity.custom.GrenadeProjectileEntity;
import net.descroissant.explosivesmod.item.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;

public class GrenadeItem extends ThrowableItem {
    public static float MAX_DRAW_DURATION = 20.0f;
    public static float DEFAULT_RANGE = 15.0f;

    public GrenadeItem(Item.Properties pProperties) {
        super(pProperties);
    }

    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if (!(pEntityLiving instanceof Player player)) return;

        boolean hasInfinite = player.getAbilities().instabuild;
        ItemStack itemstack = player.getProjectile(pStack);
        int i = this.getUseDuration(pStack) - pTimeLeft;
        i = ForgeEventFactory.onArrowLoose(pStack, pLevel, player, i, !itemstack.isEmpty() || hasInfinite);
        if (i < 0) {
            return;
        }

        if (itemstack.isEmpty() && !hasInfinite) return;
        if (itemstack.isEmpty()) { itemstack = new ItemStack(ModItems.GRENADE.get()); }

        float force = getPowerForTime(i);
//        if (((double)force < 0.1)) return;

        boolean isInfinite = player.getAbilities().instabuild;
        if (!pLevel.isClientSide) {
            GrenadeItem grenadeItem;
            if (itemstack.getItem() instanceof  GrenadeItem) grenadeItem = (GrenadeItem) itemstack.getItem();
            else grenadeItem = (GrenadeItem) ModItems.GRENADE.get();

            GrenadeProjectileEntity grenadeEntity = new GrenadeProjectileEntity(pLevel, player);
            grenadeEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, force, 1.0F);
            pLevel.addFreshEntity(grenadeEntity);
        }

        pLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + force * 0.5F);

        if (!isInfinite && !player.getAbilities().instabuild) {
            itemstack.shrink(1);
            if (itemstack.isEmpty()) {
                player.getInventory().removeItem(itemstack);
            }
        }

        player.awardStat(Stats.ITEM_USED.get(this));
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        boolean flag = !pPlayer.getProjectile(itemstack).isEmpty();
        InteractionResultHolder<ItemStack> ret = ForgeEventFactory.onArrowNock(itemstack, pLevel, pPlayer, pHand, flag);
        if (ret != null) {
            return ret;
        } else if (!pPlayer.getAbilities().instabuild && !flag) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            pPlayer.startUsingItem(pHand);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    public static float getPowerForTime(int pCharge) {
        float force = (float)pCharge / MAX_DRAW_DURATION;
        force = (force * force + force * 2.0F) / 3.0F;
        force = Math.min(force, 0.6875f);
        ExplosivesMod.LOGGER.debug(String.valueOf(force));

        return force;
    }

    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    public float getDefaultProjectileRange() {
        return DEFAULT_RANGE;
    }
}
