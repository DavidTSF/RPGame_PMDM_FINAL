package dev.davidvega.rpgame.data.viewmodel;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


import dev.davidvega.rpgame.data.model.Weapon;
import dev.davidvega.rpgame.data.model.WeaponList;
import dev.davidvega.rpgame.game.encounter.Encounter;
import dev.davidvega.rpgame.game.model.Item;
import dev.davidvega.rpgame.net.api.RPGApiService;
import dev.davidvega.rpgame.utils.ImageUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class GameModel {

    public interface EncounterCallback {
        void onFinishRetrievingEncounter(Encounter encounter);
    }

    public interface ItemCallback {
        void onFinishRetrievingItems(List<Item> itemList);
    }

    public void getAllItems(RPGApiService service, ItemCallback callback, Context context) {
        Call<WeaponList> weaponCall = service.getAllWeapons();
        weaponCall.enqueue(new Callback<WeaponList>() {
            @Override
            public void onResponse(Call<WeaponList> call, Response<WeaponList> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Item> tmpList = new ArrayList<>();

                    // Agregar las armas obtenidas de la API
                    List<Weapon> weapons = response.body().getResults();

                    for (Weapon weapon : weapons) {
                        // Descargar y guardar la imagen localmente
                        if (weapon.getImage() != null) {
                            String fileName = "weapon_" + weapon.getId_weapon() + ".png";


                            ImageUtils.saveImageFromUrl(context, weapon.getImage(), fileName);


                            // Actualizar la ruta local de la imagen en el objeto Weapon
                            String localImagePath = context.getDir("images", Context.MODE_PRIVATE).getAbsolutePath() + "/" + fileName;
                            weapon.setImage(localImagePath);
                        }

                        tmpList.add(weapon); // Añadir el arma con la ruta local actualizada
                    }

                    // Llamar al callback con la lista actualizada
                    callback.onFinishRetrievingItems(tmpList);
                } else {
                    Log.e("Error", "Respuesta fallida al obtener armas: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<WeaponList> call, Throwable t) {
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
