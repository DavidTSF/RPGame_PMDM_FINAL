package dev.davidvega.rpgame.data.viewmodel;

import android.util.Log;

import dev.davidvega.rpgame.game.model.GameEntity;

/**
 * The type Encounter model.
 */
public class EncounterModel {

    /**
     * The K.
     */
    double K = 5;

    /**
     * The type Battle ground.
     */
    public static class BattleGround {
        /**
         * The Player.
         */
        public GameEntity player; // El jugador en la batalla
        /**
         * The Enemy.
         */
        public GameEntity enemy;  // El enemigo en la batalla
        /**
         * The Resolve data.
         */
        public ResolveData resolveData; // Datos del resultado del último ataque

        /**
         * Instantiates a new Battle ground.
         *
         * @param player the player
         * @param enemy  the enemy
         */
// Constructor
        public BattleGround(GameEntity player, GameEntity enemy) {
            this.player = player;
            this.enemy = enemy;
            this.resolveData = new ResolveData(); // Inicializamos un ResolveData vacío
        }
    }

    /**
     * The type Resolve data.
     */
    public static class ResolveData {
        /**
         * The Killed.
         */
        public boolean killed;
        /**
         * The Combat message.
         */
        public String combatMessage;

        /**
         * Instantiates a new Resolve data.
         */
        public ResolveData() {}

        /**
         * Instantiates a new Resolve data.
         *
         * @param killed        the killed
         * @param combatMessage the combat message
         */
        public ResolveData(boolean killed, String combatMessage) {
            this.killed = killed;
            this.combatMessage = combatMessage;
        }
    }

    /**
     * The interface Combat callback.
     */
    public interface CombatCallback {
        /**
         * Calculate damage.
         *
         * @param bg the bg
         */
        void calculateDamage( BattleGround bg);

        /**
         * On combat finished.
         *
         * @param bg the bg
         */
        void onCombatFinished( BattleGround bg );

        /**
         * On player dead.
         *
         * @param bg the bg
         */
        void onPlayerDead( BattleGround bg );
    }

    /**
     * Make attack.
     *
     * @param bg             the bg
     * @param combatCallback the combat callback
     */
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


    /**
     * Resolve damage resolve data.
     *
     * @param attacker the attacker
     * @param defender the defender
     * @return the resolve data
     */
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
