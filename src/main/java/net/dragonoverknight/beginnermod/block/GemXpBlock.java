package net.dragonoverknight.beginnermod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class GemXpBlock extends DropExperienceBlock {
    private final IntProvider xpRange;

    public GemXpBlock(IntProvider xpRange, BlockBehaviour.Properties properties) {
        super(xpRange, properties);
        this.xpRange = xpRange;
    }

    @Override
    protected void spawnAfterBreak(BlockState state, ServerLevel level, BlockPos pos, ItemStack stack, boolean dropExperience) {
        super.spawnAfterBreak(state, level, pos, stack, true);

        // Explicitly spawn XP orbs
        int xp = this.xpRange.sample(level.random);
        if (xp > 0) {
            this.popExperience(level, pos, xp);
        }
    }
}
