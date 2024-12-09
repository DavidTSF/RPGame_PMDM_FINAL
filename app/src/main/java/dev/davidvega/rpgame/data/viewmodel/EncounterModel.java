package dev.davidvega.rpgame.data.viewmodel;

import android.util.Log;

import dev.davidvega.rpgame.game.model.GameEntity;

public class EncounterModel {

    double K = 5;

    public static class BattleGround {
        public GameEntity player; // El jugador en la batalla
        public GameEntity enemy;  // El enemigo en la batalla
        public ResolveData resolveData; // Datos del resultado del último ataque

        // Constructor
        public BattleGround(GameEntity player, GameEntity enemy) {
            this.player = player;
            this.enemy = enemy;
            this.resolveData = new ResolveData(); // Inicializamos un ResolveData vacío
        }
    }

    public static class ResolveData {
        public boolean killed;
        public String combatMessage;

        public ResolveData() {}

        public ResolveData(boolean killed, String combatMessage) {
            this.killed = killed;
            this.combatMessage = combatMessage;
        }
    }

    public interface CombatCallback {
        void calculateDamage( BattleGround bg);
        void onCombatFinished( BattleGround bg );
        void onPlayerDead( BattleGround bg );
    }

    public void makeAttack(BattleGround bg, CombatCallback combatCallback) {
        // Resolver el daño del jugador al enemigo
        ResolveData playerAttackResult = resolveDamage(bg.player, bg.enemy);
        bg.resolveData = playerAttackResult; // Guardar el resultado en BattleGround

        if (playerAttackResult.killed) {
            combatCallback.onCombatFinished(bg);
            return;
        }

        ResolveData enemyAttackResult = resolveDamage(bg.enemy, bg.player);
        // Si el jugador murió
        bg.resolveData.combatMessage += "\n" + enemyAttackResult.combatMessage;
        if (enemyAttackResult.killed) {
            combatCallback.onPlayerDead(bg);
            return;
        }

        // Notificar que se calcularon los daños
        combatCallback.calculateDamage(bg);
    }



    public ResolveData resolveDamage(GameEntity attacker, GameEntity defender) {
        double damage = K * attacker.getAttack() / (K + defender.getDefense());
        int finalDamage = (int) damage;

        boolean isKilled;
        String combatMessage;

        if (finalDamage >= defender.getHp()) {
            defender.setHp(0);
            isKilled = true;
            combatMessage = attacker.getName() + " hizo " + finalDamage + " daño. " + defender.getName() + " ha muerto.";
        } else {
            defender.setHp(defender.getHp() - finalDamage);
            isKilled = false;
            combatMessage = attacker.getName() + " hizo " + finalDamage + " daño. " + defender.getName() + " tiene " + defender.getHp() + " HP restantes.";
        }

        return new ResolveData(isKilled, combatMessage);
    }



}
