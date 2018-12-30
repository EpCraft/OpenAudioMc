package com.craftmend.openaudiomc;

import com.craftmend.openaudiomc.modules.api.ApiModule;
import com.craftmend.openaudiomc.modules.authentication.AuthenticationModule;
import com.craftmend.openaudiomc.modules.configuration.ConfigurationModule;
import com.craftmend.openaudiomc.modules.media.MediaModule;
import com.craftmend.openaudiomc.modules.networking.NetworkingModule;
import com.craftmend.openaudiomc.modules.networking.addapter.AbstractPacketAddapter;
import com.craftmend.openaudiomc.modules.networking.abstracts.AbstractPacketPayload;
import com.craftmend.openaudiomc.modules.players.PlayerModule;

import com.craftmend.openaudiomc.modules.players.objects.Client;
import com.craftmend.openaudiomc.modules.regions.RegionModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

@Getter
public final class OpenAudioMc extends JavaPlugin {

    //modules
    private ConfigurationModule configurationModule;
    private AuthenticationModule authenticationModule;
    private MediaModule mediaModule;
    private PlayerModule playerModule;
    private NetworkingModule networkingModule;
    private ApiModule apiModule;
    private RegionModule regionModule;

    //instance
    @Getter private static OpenAudioMc instance;


    //static strings
    @Getter private static final String LOG_PREFIX = "[OpenAudioMc-Log] ";
    @Getter private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(AbstractPacketPayload.class, new AbstractPacketAddapter())
            .create();


    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        //startup modules
        this.configurationModule = new ConfigurationModule(this);
        this.authenticationModule = new AuthenticationModule(this);
        this.mediaModule = new MediaModule(this);
        this.playerModule = new PlayerModule(this);
        this.networkingModule = new NetworkingModule(this);
        this.apiModule = new ApiModule(this);

        //optional modules
        if (getServer().getPluginManager().isPluginEnabled("WorldGuard")) {
            regionModule = new RegionModule(this);
        }
    }

    @Override
    public void onDisable() {
        try {
            configurationModule.saveAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}