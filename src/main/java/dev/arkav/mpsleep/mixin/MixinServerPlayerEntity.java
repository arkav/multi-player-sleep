package dev.arkav.mpsleep.mixin;

import com.google.common.collect.Lists;
import dev.arkav.mpsleep.MPSleepConfig;
import dev.arkav.oneliners.AutoConfig;
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
    Random random = new Random();
    @Inject(at = @At("HEAD"), method = "sleep")
    private void sleep(BlockPos pos, CallbackInfo info) {
        @SuppressWarnings("ConstantConditions") ServerPlayerEntity self = (ServerPlayerEntity) (Object) this;
        ServerWorld world = self.getServerWorld();
        if (world.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)) {
            long l = world.getLevelProperties().getTimeOfDay() + 24000L;
            ((LevelProperties)world.getLevelProperties()).method_29035(l - l % 24000);
            LiteralText sleepMessage = new LiteralText(String.format(MPSleepConfig.INSTANCE.messages.get(random.nextInt(MPSleepConfig.INSTANCE.messages.size())),
                    self.getName().asString()));
            for (ServerPlayerEntity pe : world.getPlayers()) {
                pe.sendMessage(sleepMessage, false);
            }
        }
    }
}
