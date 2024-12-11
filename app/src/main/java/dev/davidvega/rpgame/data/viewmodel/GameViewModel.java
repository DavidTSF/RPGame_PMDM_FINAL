package dev.davidvega.rpgame.data.viewmodel;

import android.app.Application;
import android.content.Context;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dev.davidvega.rpgame.game.encounter.Encounter;
import dev.davidvega.rpgame.game.encounter.Enemy;
import dev.davidvega.rpgame.game.model.Inventory;
import dev.davidvega.rpgame.game.model.Item;
import dev.davidvega.rpgame.data.model.User;
import dev.davidvega.rpgame.game.model.PlayerCharacter;
import dev.davidvega.rpgame.net.api.RPGApiService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GameViewModel extends AndroidViewModel {

    Executor executor;

    MutableLiveData<User> user = new MutableLiveData<>(new User());
    MutableLiveData<Encounter> encounter = new MutableLiveData<>();
    MutableLiveData<Inventory> inventory = user.getValue().getPlayerCharacter().getInventoryLiveData();


    MutableLiveData<List<Item>> itemsGame = new MutableLiveData<>(new ArrayList<>());

    GameModel gameModel;
    EncounterModel encounterModel;


    MutableLiveData<Boolean> calculateDamage = new MutableLiveData<>();
    MutableLiveData<Boolean> encounterLocked = new MutableLiveData<>();
    MutableLiveData<Boolean> playerDead = new MutableLiveData<>(false);
    MutableLiveData<Boolean> attackLock = new MutableLiveData<>();
    MutableLiveData<Boolean> buttonVisible = new MutableLiveData<>(true);
    MutableLiveData<Boolean> wonEncounter = new MutableLiveData<>(false);

    Retrofit retrofit;
    RPGApiService service;


    public GameViewModel(@NonNull Application application) {
        super(application);
        executor = Executors.newSingleThreadExecutor();
        retrofit = new Retrofit.Builder()
                .baseUrl(RPGApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RPGApiService.class);
        gameModel = new GameModel();
        encounterModel = new EncounterModel();
    }


    // METODOS DE COMBATE
    public void makeAttack() {
        attackLock.postValue(true);
        final EncounterModel.BattleGround battleGround = new EncounterModel.BattleGround(
                getUser().getValue().getPlayerCharacter(), getEncounter().getValue().getEnemy()
        );

        executor.execute(new Runnable() {
            @Override
            public void run() {
                encounterModel.makeAttack( battleGround, new EncounterModel.CombatCallback() {
                    @Override
                    public void calculateDamage(EncounterModel.BattleGround bg) {
                        getUser().getValue().getPlayerdataLiveData().postValue((PlayerCharacter) bg.player);
                        getEncounter().getValue().setEnemy((Enemy) bg.enemy);

                        // Obtener el mensaje de resultado de combate desde bg
                        EncounterModel.ResolveData resolveData = bg.resolveData; // Asume que bg ahora tiene una instancia de ResolveData
                        String combatMessage = resolveData.combatMessage;

                        // Actualizar la descripción del encuentro y añadir los daños
                        String updatedDescription = getEncounter().getValue().getDescription() + "\n" + combatMessage;
                        getEncounter().getValue().setDescription(updatedDescription);

                        calculateDamage.postValue(true);
                        attackLock.postValue(false);
                    }

                    @Override
                    public void onCombatFinished(EncounterModel.BattleGround bg) {
                        encounterLocked.postValue(false);
                        wonEncounter.postValue(true);
                    }

                    @Override
                    public void onPlayerDead(EncounterModel.BattleGround bg) {
                        calculateDamage.postValue(true);
                        attackLock.postValue(false);
                        playerDead.postValue(true);
                    }
                });
            }
        });
    }

    public Item rewardPlayer() {

        Random random = new Random();
        List<Item> itemList = getItemsGame().getValue();
        Item randomItem = itemList.get(random.nextInt(itemList.size()));
        inventory.getValue().addItem(randomItem);

        return randomItem;
    }

    // LLAMADAS A LA API
    public void getAllItemsFromDatabase(Context context) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                gameModel.getAllItems(service, new GameModel.ItemCallback() {
                    @Override
                    public void onFinishRetrievingItems(List<Item> itemList) {
                        itemsGame.postValue(itemList);
                    }
                }, context);
            }
        });
    }

    public void getEncounterFromApi() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                gameModel.getEncounter(service, new GameModel.EncounterCallback() {
                    @Override
                    public void onFinishRetrievingEncounter(Encounter encounter) {
                        getEncounter().postValue(encounter);
                    }
                });
            }
        });
    }





    // GETTER Y SETTERS

    public MutableLiveData<User> getUser() {
        return user;
    }

    public void setUser(MutableLiveData<User> user) {
        this.user = user;
    }

    public MutableLiveData<Encounter> getEncounter() {
        return encounter;
    }

    public void setEncounter(MutableLiveData<Encounter> encounter) {
        this.encounter = encounter;
    }

    public MutableLiveData<Boolean> getEncounterLocked() {
        return encounterLocked;
    }

    public MutableLiveData<Boolean> getPlayerDead() {
        return playerDead;
    }

    public MutableLiveData<Boolean> getCalculateDamage() {
        return calculateDamage;
    }

    public MutableLiveData<Inventory> getInventory() {
        return inventory;
    }
    public MutableLiveData<Boolean> getButtonVisible() {
        return buttonVisible;
    }

    public void setButtonVisible(Boolean isVisible) {
        buttonVisible.setValue(isVisible);
    }

    public void setCalculateDamage(MutableLiveData<Boolean> calculateDamage) {
        this.calculateDamage = calculateDamage;
    }

    public void setEncounterLocked(Boolean encounterLocked) {
        this.encounterLocked.setValue(encounterLocked);
    }

    public void setPlayerDead(MutableLiveData<Boolean> playerDead) {
        this.playerDead = playerDead;
    }

    public MutableLiveData<Boolean> getAttackLock() {
        return attackLock;
    }

    public void setAttackLock(MutableLiveData<Boolean> attackLock) {
        this.attackLock = attackLock;
    }

    public void setButtonVisible(MutableLiveData<Boolean> buttonVisible) {
        this.buttonVisible = buttonVisible;
    }

    public void setInventory(MutableLiveData<Inventory> inventory) {
        this.inventory = inventory;
    }

    public MutableLiveData<List<Item>> getItemsGame() {
        return itemsGame;
    }

    public void setItemsGame(MutableLiveData<List<Item>> itemsGame) {
        this.itemsGame = itemsGame;
    }

    public MutableLiveData<Boolean> getWonEncounter() {
        return wonEncounter;
    }

    public void setWonEncounter(MutableLiveData<Boolean> wonEncounter) {
        this.wonEncounter = wonEncounter;
    }
}
