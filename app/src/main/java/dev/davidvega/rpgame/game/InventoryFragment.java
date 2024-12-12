package dev.davidvega.rpgame.game;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.helper.widget.Grid;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import dev.davidvega.rpgame.R;
import dev.davidvega.rpgame.data.viewmodel.GameViewModel;
import dev.davidvega.rpgame.databinding.FragmentInventoryBinding;
import dev.davidvega.rpgame.databinding.ViewholderItemBinding;
import dev.davidvega.rpgame.game.model.Consumable;
import dev.davidvega.rpgame.game.model.Inventory;
import dev.davidvega.rpgame.game.model.Item;
import dev.davidvega.rpgame.game.model.Weapon;


public class InventoryFragment extends Fragment {
    FragmentInventoryBinding binding;
    GameViewModel gameModelView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        gameModelView = new ViewModelProvider(getActivity()).get(GameViewModel.class);
        return (binding = FragmentInventoryBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ItemAdapter itemAdapter = new ItemAdapter();

        binding.itemRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 5));
        binding.itemRecyclerView.setAdapter(itemAdapter);

        gameModelView.getUser().getValue().getPlayerdataLiveData().getValue().getInventoryLiveData().observe(getViewLifecycleOwner(), new Observer<Inventory>() {
            @Override
            public void onChanged(Inventory inventory) {
                itemAdapter.setList(inventory.getInventoryList());
            }
        });




    }

    class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {

        List<Item> elementos;

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemViewHolder(ViewholderItemBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position ) {
            Item elemento = elementos.get(position);
            Item.ItemType elementType = elemento.getItemType();

            Glide.with(holder.itemView.getContext())
                    .load(elemento.getImage()) // Ruta local
                    .placeholder(R.drawable.arrow_up)// Imagen por defecto mientras se carga
                    .into(holder.binding.itemIcon);

            holder.binding.itemIconBackground.getDrawable().setFilterBitmap(false);
            holder.itemView.setOnClickListener(v -> {
                binding.selectedItemImage.setImageDrawable(holder.binding.itemIcon.getDrawable());
                binding.selectedItemDescription.setText(elemento.getDescription());

                switch (elementType){
                    case CONSUMABLE:
                        binding.selectedItemUseButton.setText("Usar");
                        binding.selectedItemUseButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                gameModelView.useConsumable((Consumable) elemento);
                                elementos.remove(holder.getAdapterPosition());
                                binding.cardSelectedItem.setVisibility(View.GONE);
                                notifyDataSetChanged();
                            }
                        });
                        break;
                    case WEAPON:
                        binding.selectedItemUseButton.setText("Equipar");
                        binding.selectedItemUseButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                elementos.add(gameModelView.equipWeapon((Weapon) elemento));
                                elementos.remove(holder.getAdapterPosition());
                                binding.cardSelectedItem.setVisibility(View.GONE);
                                notifyDataSetChanged();
                            }
                        });

                }


                binding.cardSelectedItem.setVisibility(View.VISIBLE);
            });
        }

        @Override
        public int getItemCount() {
            return elementos != null ? elementos.size() : 0;
        }

        public void setList(List<Item> elementos) {
            this.elementos = elementos;
            notifyDataSetChanged();
        }

        public Item getItem(int posicion) {
            return elementos.get(posicion);
        }

        public void addItem(Item item) {
            elementos.add(item);
            notifyDataSetChanged();
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderItemBinding binding;

        public ItemViewHolder(ViewholderItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }



}