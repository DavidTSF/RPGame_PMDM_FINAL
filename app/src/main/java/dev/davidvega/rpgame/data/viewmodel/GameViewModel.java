package dev.davidvega.rpgame.data.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dev.davidvega.rpgame.data.model.UserModel;
import dev.davidvega.rpgame.game.encounter.Encounter;
import dev.davidvega.rpgame.game.encounter.Enemy;
import dev.davidvega.rpgame.game.manager.ExperienceManager;
import dev.davidvega.rpgame.game.model.Consumable;
import dev.davidvega.rpgame.game.model.Inventory;
import dev.davidvega.rpgame.game.model.Item;
import dev.davidvega.rpgame.data.model.User;
import dev.davidvega.rpgame.game.model.PlayerCharacter;
import dev.davidvega.rpgame.game.model.Weapon;
import dev.davidvega.rpgame.net.api.LoginApiService;
import dev.davidvega.rpgame.net.api.RPGApiService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class GameViewModel extends AndroidViewModel {

    static final int XPFactor = 10;

    Executor executor;

    MutableLiveData<User> user = new MutableLiveData<>(new User());
    MutableLiveData<Encounter> encounter = new MutableLiveData<>();

    MutableLiveData<List<Item>> itemsGame = new MutableLiveData<>(new ArrayList<>());

    GameModel gameModel;
    UserModel userModel;

    EncounterModel encounterModel;


    MutableLiveData<Boolean> calculateDamage = new MutableLiveData<>();
    MutableLiveData<Boolean> encounterLocked = new MutableLiveData<>();
    MutableLiveData<Boolean> playerDead = new MutableLiveData<>(false);
    MutableLiveData<Boolean> attackLock = new MutableLiveData<>();
    MutableLiveData<Boolean> buttonVisible = new MutableLiveData<>(true);
    MutableLiveData<Boolean> wonEncounter = new MutableLiveData<>(false);

    Retrofit retrofit;
    RPGApiService rpgService;
    LoginApiService loginApiService;


    public GameViewModel(@NonNull Application application) {
        super(application);
        executor = Executors.newSingleThreadExecutor();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        retrofit = new Retrofit.Builder()
                .baseUrl(RPGApiService.BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();
        rpgService = retrofit.create(RPGApiService.class);

        retrofit = new Retrofit.Builder()
                .baseUrl(LoginApiService.BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();
        loginApiService = retrofit.create(LoginApiService.class);
        gameModel = new GameModel();
        userModel = new UserModel();
        encounterModel = new EncounterModel();
    }



    // Metodos inventario

    public void useConsumable(Consumable consumable) {
        getUser().getValue().getPlayerdataLiveData().setValue(consumable.healPlayer
                (getUser().getValue().getPlayerCharacter()));
        updateUser();
    }

    public Weapon equipWeapon(Weapon weapon) {
        Weapon currentEquipedWeapon = getUser().getValue().getPlayerdataLiveData().getValue().getCurrentWeapon();
        getUser().getValue().getPlayerdataLiveData().getValue().setCurrentWeapon(weapon);
        updateUser();
        return currentEquipedWeapon;
    }

    public void updateUser() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                userModel.updateUserFromGame(loginApiService, getUser().getValue());
            }
        });

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
                        encounter.postValue(new Encounter(
                                "De vuelta en la mazmorra...",
                                Encounter.typeEncounter.START
                        ));
                        attackLock.postValue(false);
                        playerDead.postValue(true);
                    }
                });
            }
        });
    }

    public Item rewardPlayer() {
        Encounter currentEncounter = getEncounter().getValue();
        Random random = new Random();

        int baseXp = currentEncounter.getDifficulty() * XPFactor;
        double randomMultiplier = 1 + (random.nextDouble() * 0.5); // Variación entre 1x y 1.5x
        int totalXpEarned = (int) (baseXp * randomMultiplier);

        getUser().getValue().getPlayerdataLiveData().getValue().setXp(
            getUser().getValue().getPlayerCharacter().getXp()+totalXpEarned
        );

        ExperienceManager.levelUp(getUser().getValue().getPlayerCharacter());

        List<Item> itemList = getItemsGame().getValue();
        Item randomItem = itemList.get(random.nextInt(itemList.size()));

        getUserInventory().getValue().addItem(randomItem);


        updateUser();
        return randomItem;
    }

    // LLAMADAS A LA API
    public void getAllItemsFromDatabase(Context context) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                gameModel.getAllItems(rpgService, new GameModel.ItemCallback() {
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
                gameModel.getEncounter(rpgService, new GameModel.EncounterCallback() {
                    @Override
                    public void onFinishRetrievingEncounter(Encounter encounter) {
                        int level = getUser().getValue().getPlayerdataLiveData().getValue().getLevel();
                        encounter.setDifficulty(level);


                        getEncounter().postValue(encounter);
                    }
                });
            }
        });
    }




    // GETTER Y SETTERS

    public MutableLiveData<Inventory> getUserInventory() {
        return user.getValue().getPlayerdataLiveData().getValue().getInventoryLiveData();
    }

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

    public void emptyInventory() {
        getUser().getValue().getPlayerdataLiveData().getValue().getInventoryLiveData().postValue(new Inventory());
    }
}
