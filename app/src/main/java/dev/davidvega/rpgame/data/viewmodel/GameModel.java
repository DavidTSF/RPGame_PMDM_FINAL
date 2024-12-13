package dev.davidvega.rpgame.data.viewmodel;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


import dev.davidvega.rpgame.data.model.AllItemList;
import dev.davidvega.rpgame.game.model.Weapon;
import dev.davidvega.rpgame.data.model.WeaponList;
import dev.davidvega.rpgame.game.encounter.Encounter;
import dev.davidvega.rpgame.game.model.Item;
import dev.davidvega.rpgame.net.api.RPGApiService;
import dev.davidvega.rpgame.utils.ImageUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * The type Game model.
 */
public class GameModel {

    /**
     * The interface Encounter callback.
     */
    public interface EncounterCallback {
        /**
         * On finish retrieving encounter.
         *
         * @param encounter the encounter
         */
        void onFinishRetrievingEncounter(Encounter encounter);
    }

    /**
     * The interface Item callback.
     */
    public interface ItemCallback {
        /**
         * On finish retrieving items.
         *
         * @param itemList the item list
         */
        void onFinishRetrievingItems(List<Item> itemList);
    }

    /**
     * Gets all items.
     *
     * @param service  the service
     * @param callback the callback
     * @param context  the context
     */
    public void getAllItems(RPGApiService service, ItemCallback callback, Context context) {
        Call<AllItemList> weaponCall = service.getAllItems();
        weaponCall.enqueue(new Callback<AllItemList>() {
            @Override
            public void onResponse(Call<AllItemList> call, Response<AllItemList> response) {
                if (response.isSuccessful() && response.body() != null) {


                    // Agregar las armas obtenidas de la API
                    List<Item> allItems = response.body().getAllItems();


                    for (Item item : allItems) {
                        // Descargar y guardar la imagen localmente
                        Log.d("DEBUG_GAME_MODEL", item.toString());

                        if (item.getImage() != null) {
                            String fileName = item.getItemType().toString() + "_" + item.getItemName() + ".png";

                            ImageUtils.saveImageFromUrl(context, item.getImage(), fileName);


                            // Actualizar la ruta local de la imagen en el objeto Weapon
                            String localImagePath = context.getDir("images", Context.MODE_PRIVATE).getAbsolutePath() + "/" + fileName;
                            item.setImage(localImagePath);
                        }


                    }

                    // Llamar al callback con la lista actualizada
                    callback.onFinishRetrievingItems(allItems);
                } else {
                    Log.e("Error", "Respuesta fallida al obtener armas: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<AllItemList> call, Throwable t) {
                Log.e("Error", "Error al llamar a la API: " + t.toString());
            }
        });
    }


    /*
    public void getAllItems(RPGApiService service, ItemCallback callback ) {
        Call<WeaponList> weaponCall = service.getAllWeapons();
        weaponCall.enqueue(new Callback<WeaponList>() {
            @Override
            public void onResponse(Call<WeaponList> call, Response<WeaponList> response) {



                List<Item> tmpList = new ArrayList<>();

                tmpList.addAll(response.body().getResults());
                callback.onFinishRetrievingItems(tmpList);
            }
            @Override
            public void onFailure(Call<WeaponList> call, Throwable t) {
                Log.d("Error", "NO SE HA PODIDO AGARRAR LA API?" + t.toString());
            }
        });
    }*/

    /**
     * Gets encounter.
     *
     * @param service  the service
     * @param callback the callback
     */
    public void getEncounter(RPGApiService service, EncounterCallback callback ) {
        Call<Encounter> weaponCall = service.getRandomEncounter();
        weaponCall.enqueue(new Callback<Encounter>() {
            @Override
            public void onResponse(Call<Encounter> call, Response<Encounter> response) {
                callback.onFinishRetrievingEncounter( response.body() );
            }
            @Override
            public void onFailure(Call<Encounter> call, Throwable t) {
                Log.d("Error", "NO SE HA PODIDO AGARRAR LA API?" + t.toString());
            }
        });
    }

    /**
     * Fetch and save weapon image.
     *
     * @param context the context
     * @param weapon  the weapon
     */
    public void fetchAndSaveWeaponImage(Context context, Weapon weapon) {
        String fileName = "weapon_" + weapon.getId_weapon() + ".png";

        if (!ImageUtils.imageExists(context, fileName)) {
            // Descarga y guarda la imagen
            ImageUtils.saveImageFromUrl(context, weapon.getImage(), fileName);
        }

        // Actualiza la ruta local de la imagen
        weapon.setImage(context.getDir("images", Context.MODE_PRIVATE).getAbsolutePath() + "/" + fileName);
    }

}
