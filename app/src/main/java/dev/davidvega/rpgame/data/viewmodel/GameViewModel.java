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

/**
 * The type Game view model.
 */
public class GameViewModel extends AndroidViewModel {

    /**
     * The Xp factor.
     */
    static final int XPFactor = 10;

    /**
     * The Executor.
     */
    Executor executor;

    /**
     * The User.
     */
    MutableLiveData<User> user = new MutableLiveData<>(new User());
    /**
     * The Encounter.
     */
    MutableLiveData<Encounter> encounter = new MutableLiveData<>();

    /**
     * The Items game.
     */
    MutableLiveData<List<Item>> itemsGame = new MutableLiveData<>(new ArrayList<>());

    /**
     * The Game model.
     */
    GameModel gameModel;
    /**
     * The User model.
     */
    UserModel userModel;

    /**
     * The Encounter model.
     */
    EncounterModel encounterModel;


    /**
     * The Calculate damage.
     */
    MutableLiveData<Boolean> calculateDamage = new MutableLiveData<>();
    /**
     * The Encounter locked.
     */
    MutableLiveData<Boolean> encounterLocked = new MutableLiveData<>();
    /**
     * The Player dead.
     */
    MutableLiveData<Boolean> playerDead = new MutableLiveData<>(false);
    /**
     * The Attack lock.
     */
    MutableLiveData<Boolean> attackLock = new MutableLiveData<>();
    /**
     * The Button visible.
     */
    MutableLiveData<Boolean> buttonVisible = new MutableLiveData<>(true);
    /**
     * The Won encounter.
     */
    MutableLiveData<Boolean> wonEncounter = new MutableLiveData<>(false);

    /**
     * The Retrofit.
     */
    Retrofit retrofit;
    /**
     * The Rpg service.
     */
    RPGApiService rpgService;
    /**
     * The Login api service.
     */
    LoginApiService loginApiService;


    /**
     * Instantiates a new Game view model.
     *
     * @param application the application
     */
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

    /**
     * Use consumable.
     *
     * @param consumable the consumable
     */
    public void useConsumable(Consumable consumable) {
        getUser().getValue().getPlayerdataLiveData().setValue(consumable.healPlayer
                (getUser().getValue().getPlayerCharacter()));
        updateUser();
    }

    /**
     * Equip weapon weapon.
     *
     * @param weapon the weapon
     * @return the weapon
     */
    public Weapon equipWeapon(Weapon weapon) {
        Weapon currentEquipedWeapon = getUser().getValue().getPlayerdataLiveData().getValue().getCurrentWeapon();
        getUser().getValue().getPlayerdataLiveData().getValue().setCurrentWeapon(weapon);
        updateUser();
        return currentEquipedWeapon;
    }

    /**
     * Update user.
     */
    public void updateUser() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                userModel.updateUserFromGame(loginApiService, getUser().getValue());
            }
        });

    }

    /**
     * Make attack.
     */
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

    /**
     * Reward player item.
     *
     * @return the item
     */
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

    /**
     * Gets all items from database.
     *
     * @param context the context
     */
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

    /**
     * Gets encounter from api.
     */
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

    /**
     * Gets user inventory.
     *
     * @return the user inventory
     */
    public MutableLiveData<Inventory> getUserInventory() {
        return user.getValue().getPlayerdataLiveData().getValue().getInventoryLiveData();
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public MutableLiveData<User> getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(MutableLiveData<User> user) {
        this.user = user;
    }

    /**
     * Gets encounter.
     *
     * @return the encounter
     */
    public MutableLiveData<Encounter> getEncounter() {
        return encounter;
    }

    /**
     * Sets encounter.
     *
     * @param encounter the encounter
     */
    public void setEncounter(MutableLiveData<Encounter> encounter) {
        this.encounter = encounter;
    }

    /**
     * Gets encounter locked.
     *
     * @return the encounter locked
     */
    public MutableLiveData<Boolean> getEncounterLocked() {
        return encounterLocked;
    }

    /**
     * Gets player dead.
     *
     * @return the player dead
     */
    public MutableLiveData<Boolean> getPlayerDead() {
        return playerDead;
    }

    /**
     * Gets calculate damage.
     *
     * @return the calculate damage
     */
    public MutableLiveData<Boolean> getCalculateDamage() {
        return calculateDamage;
    }

    /**
     * Gets button visible.
     *
     * @return the button visible
     */
    public MutableLiveData<Boolean> getButtonVisible() {
        return buttonVisible;
    }

    /**
     * Sets button visible.
     *
     * @param isVisible the is visible
     */
    public void setButtonVisible(Boolean isVisible) {
        buttonVisible.setValue(isVisible);
    }

    /**
     * Sets calculate damage.
     *
     * @param calculateDamage the calculate damage
     */
    public void setCalculateDamage(MutableLiveData<Boolean> calculateDamage) {
        this.calculateDamage = calculateDamage;
    }

    /**
     * Sets encounter locked.
     *
     * @param encounterLocked the encounter locked
     */
    public void setEncounterLocked(Boolean encounterLocked) {
        this.encounterLocked.setValue(encounterLocked);
    }

    /**
     * Sets player dead.
     *
     * @param playerDead the player dead
     */
    public void setPlayerDead(MutableLiveData<Boolean> playerDead) {
        this.playerDead = playerDead;
    }

    /**
     * Gets attack lock.
     *
     * @return the attack lock
     */
    public MutableLiveData<Boolean> getAttackLock() {
        return attackLock;
    }

    /**
     * Sets attack lock.
     *
     * @param attackLock the attack lock
     */
    public void setAttackLock(MutableLiveData<Boolean> attackLock) {
        this.attackLock = attackLock;
    }

    /**
     * Sets button visible.
     *
     * @param buttonVisible the button visible
     */
    public void setButtonVisible(MutableLiveData<Boolean> buttonVisible) {
        this.buttonVisible = buttonVisible;
    }


    /**
     * Gets items game.
     *
     * @return the items game
     */
    public MutableLiveData<List<Item>> getItemsGame() {
        return itemsGame;
    }

    /**
     * Sets items game.
     *
     * @param itemsGame the items game
     */
    public void setItemsGame(MutableLiveData<List<Item>> itemsGame) {
        this.itemsGame = itemsGame;
    }

    /**
     * Gets won encounter.
     *
     * @return the won encounter
     */
    public MutableLiveData<Boolean> getWonEncounter() {
        return wonEncounter;
    }

    /**
     * Sets won encounter.
     *
     * @param wonEncounter the won encounter
     */
    public void setWonEncounter(MutableLiveData<Boolean> wonEncounter) {
        this.wonEncounter = wonEncounter;
    }

    /**
     * Empty inventory.
     */
    public void emptyInventory() {
        getUser().getValue().getPlayerdataLiveData().getValue().getInventoryLiveData().postValue(new Inventory());
    }
}
