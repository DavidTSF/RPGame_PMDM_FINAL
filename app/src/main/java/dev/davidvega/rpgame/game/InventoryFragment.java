package dev.davidvega.rpgame.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.davidvega.rpgame.data.viewmodel.GameViewModel;
import dev.davidvega.rpgame.databinding.FragmentInventoryBinding;
import dev.davidvega.rpgame.databinding.ViewholderItemBinding;
import dev.davidvega.rpgame.game.model.Inventory;
import dev.davidvega.rpgame.game.model.Item;


public class InventoryFragment extends Fragment {
    FragmentInventoryBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentInventoryBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GameViewModel gameModelView = new ViewModelProvider(getActivity()).get(GameViewModel.class);
        ItemAdapter itemAdapter = new ItemAdapter();
        binding.itemRecyclerView.setAdapter(itemAdapter);

        gameModelView.getAllItemsFromDatabase();

        gameModelView.getInventory().observe(getViewLifecycleOwner(), new Observer<Inventory>() {
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
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

            Item elemento = elementos.get(position);

            holder.binding.nameItem.setText(elemento.getItemName());
        }

        @Override
        public int getItemCount() {
            return elementos != null ? elementos.size() : 0;
        }

        public void setList(List<Item> elementos){
            this.elementos = elementos;
            notifyDataSetChanged();
        }

        public Item getItem(int posicion){
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