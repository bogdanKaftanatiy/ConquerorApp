package com.example.a1.conq;

/**
 * Created by 1 on 01.06.2017.
 */
public class SingletonUser {
    String name;
    int currentGame;
    private static SingletonUser singletonUser;
    private SingletonUser(){
        name=null;
        currentGame=-1;
    }
    public  static SingletonUser getSingletonUser(){
        if (singletonUser==null){
            singletonUser=new SingletonUser();
        }
        return singletonUser;
    }

    public int getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(int currentGame) {
        this.currentGame = currentGame;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
