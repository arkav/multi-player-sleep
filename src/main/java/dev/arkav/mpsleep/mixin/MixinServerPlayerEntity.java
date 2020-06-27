package dev.arkav.mpsleep.mixin;

import com.google.common.collect.Lists;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Random;

@Mixin(ServerPlayerEntity.class)
public class MixinServerPlayerEntity {
    private static final List<String> sleepMessages = Lists.newArrayList(
            "%s was temporarily slain by bed.",
            "%s went to sleep in the nether, you should try that later!",
            "%s coomed on their waifu body pillow.",
            "uwu mwoning swenpai %s swept in bwed.",
            "%s went fortnightnight.",
            "%s went to sleep. Sweet dreams!",
            "Hey %s can I put my minecraft bed next to yours... Ha ha just kidding... Unless?",
            "%s is based, and quite possibly sleeping pilled.",
            "Oh my god %s, the hot singles in my area suddenly went up in flames!",
            "every time %s closes their eyes, they wake up feelin' so horney.",
            "%s slept with the fish.",
            "%s fell asleep at their computer."
    );

    Random random = new Random();
    @Inject(at = @At("HEAD"), method = "sleep")
    private void sleep(BlockPos pos, CallbackInfo info) {
        @SuppressWarnings("ConstantConditions") ServerPlayerEntity self = (ServerPlayerEntity) (Object) this;
        ServerWorld world = self.getServerWorld();
        if (world.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)) {
            long l = world.getLevelProperties().getTimeOfDay() + 24000L;
            ((LevelProperties)world.getLevelProperties()).method_29035(l - l % 24000);
            LiteralText sleepMessage = new LiteralText(String.format(sleepMessages.get(random.nextInt(sleepMessages.size())), self.getName().asString()));
            for (ServerPlayerEntity pe : world.getPlayers()) {
                pe.sendMessage(sleepMessage, false);
            }
        }
    }
}
