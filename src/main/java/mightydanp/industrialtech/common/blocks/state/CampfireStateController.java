package mightydanp.industrialtech.common.blocks.state;

import net.minecraft.nbt.CompoundNBT;

/**
 * Created by MightyDanp on 5/9/2021.
 */
public class CampfireStateController {
    private CompoundNBT campfireNBT;

    public CampfireStateController(CompoundNBT compoundNBTIn){
        campfireNBT = compoundNBTIn;
    }

    public CompoundNBT getCampfireNBT(){
        return campfireNBT;
    }

    public int getLogAmount() {
        return campfireNBT.contains("log_amount") ? campfireNBT.getInt("log_amount") : 0;
    }

    public void putLogAmount(int logAmountIn) {
        campfireNBT.putInt("log_amount", logAmountIn);
    }

    public boolean getIsLit(){
        return campfireNBT.contains("is_lit") && campfireNBT.getBoolean("is_lit");
    }

    public void putIsLit(boolean isLitIn){
        campfireNBT.putBoolean("is_lit", isLitIn);
    }

    public boolean getHaveAsh(){
        return campfireNBT.contains("have_ash") && campfireNBT.getBoolean("have_ash");
    }

    public void putHaveAsh(boolean haveAshIn){
        campfireNBT.putBoolean("have_ash", haveAshIn);
    }

    public String getDirection(){
        return campfireNBT.contains("direction") ? campfireNBT.getString("direction") : "north";
    }

    public void putDirection(String haveDirectionIn){
        campfireNBT.putString("direction", haveDirectionIn);
    }

    public boolean getSignalFire() {
        return campfireNBT.contains("signal_fire") && campfireNBT.getBoolean("signal_fire");
    }

    public void putSignalFire(boolean signalFireIn) {
        campfireNBT.putBoolean("signal_fire", signalFireIn);
    }

    public boolean getKeepLogsFormed() {
        return campfireNBT.contains("keep_logs_formed") && campfireNBT.getBoolean("keep_logs_formed");
    }

    public void putKeepLogsFormed(boolean keepLogsFormedIn) {
        campfireNBT.putBoolean("keep_logs_formed", keepLogsFormedIn);
    }

}
