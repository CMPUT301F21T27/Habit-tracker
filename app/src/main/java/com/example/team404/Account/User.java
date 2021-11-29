package com.example.team404.Account;

import java.util.ArrayList;

/**
 * User class to keep track of users following, requests list
 *
 */

// Will add functionality with firebase later

public class User {
    private String name;
    private String email;
    private ArrayList<User> followingList;
    private ArrayList<User> requestedList;

    /**
     * Constructor for new user.
     * @param name
     * @param email
     */
    public User(String name, String email){
        this.name = name;
        this.email = email;
        this.followingList = new ArrayList<User>();
        this.requestedList = new ArrayList<User>();
    }

    /**
     * Constructor for user that is already created.
     * @param name
     * @param email
     * @param followingList
     * @param requestedList
     */
    public User(String name, String email, ArrayList<User> followingList, ArrayList<User> requestedList){
        this.name = name;
        this.email = email;
        this.followingList = followingList;
        this.requestedList = requestedList;
    }

    //Getter Methods
    public String getName(){
        return this.name;
    }
    public String getEmail(){
        return this.email;
    }
    public ArrayList<User> getFollowingList() {
        return followingList;
    }
    public ArrayList<User> getRequestedList() {
        return requestedList;
    }
    //Setter Methods
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setFollowingList(ArrayList<User> followingList) {
        this.followingList = followingList;
    }
    public void setRequestedList(ArrayList<User> requestedList) {
        this.requestedList = requestedList;
    }

    /**
     * Adds user to the following list of another user and removes the follow request
     * @param user
     */
    public void acceptRequest(User user){
        user.followingList.add(this);
        user.requestedList.remove(this);
    }

    /**
     * Removes user from other users follow request list
     * @param user
     */
    public void declineRequest(User user){
        user.requestedList.remove(this);
    }

    /**
     * Adds user to another users follow request list
     * @param user
     */
    public void sendRequest(User user){
        user.requestedList.add(this);
    }

    /**
     * Removes another user from current users following list
     * @param user
     */
    public void unfollow(User user){
        this.followingList.remove(user);
    }
}
