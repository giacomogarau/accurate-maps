package io.github.lucaargolo.accuratemaps.mixin;

import io.github.lucaargolo.accuratemaps.client.AccurateMapsClient;
import net.minecraft.client.MinecraftClient;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(at = @At("RETURN"), method = "reloadResources(ZLnet/minecraft/client/MinecraftClient$LoadingContext;)Ljava/util/concurrent/CompletableFuture;", cancellable = true)
    public void afterResourceReload(boolean force, MinecraftClient.@Nullable LoadingContext loadingContext, CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        MinecraftClient client = (MinecraftClient) (Object) this;
        cir.setReturnValue(cir.getReturnValue().thenRun(() -> AccurateMapsClient.INSTANCE.paintBlockColorMap(client)));
    }


}
