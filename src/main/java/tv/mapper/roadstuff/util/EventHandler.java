package tv.mapper.roadstuff.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import tv.mapper.roadstuff.block.PaintableBlock;
import tv.mapper.roadstuff.init.ModItems;
import tv.mapper.roadstuff.item.ItemBrush;

@EventBusSubscriber
public class EventHandler
{
    @SubscribeEvent
    public static void onLeftClickBlock(LeftClickBlock event)
    {
        PlayerEntity player = event.getEntityPlayer();
        ItemStack heldItem = ItemStack.EMPTY;

        if(player.getHeldItemMainhand().getItem() == ModItems.PAINT_BRUSH)
            heldItem = player.getHeldItemMainhand();
        else if(player.getHeldItemOffhand().getItem() == ModItems.PAINT_BRUSH)
            heldItem = player.getHeldItemOffhand();

        if(heldItem.getItem() == ModItems.PAINT_BRUSH)
        {
            if(event.getFace() == Direction.UP && event.getWorld().getBlockState(event.getPos()).getBlock() instanceof PaintableBlock)
            {
                event.setCanceled(true);
                if(player.isSneaking())
                    ItemBrush.removeLine(event.getWorld(), event.getPos(), player);
                else
                    ItemBrush.paintLine(event.getFace(), event.getWorld().getBlockState(event.getPos()), event.getWorld(), event.getPos(), player, heldItem);
            }
        }
    }
}