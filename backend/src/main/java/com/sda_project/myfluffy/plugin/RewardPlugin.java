package com.sda_project.myfluffy.plugin;


import com.sda_project.myfluffy.user.model.User;

public interface RewardPlugin {
    boolean sendReward(User sender, User recipient, double amount);
}