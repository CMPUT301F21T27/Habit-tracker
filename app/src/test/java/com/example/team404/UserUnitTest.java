package com.example.team404;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.example.team404.Account.User;

import org.junit.Test;

import java.util.ArrayList;

public class UserUnitTest {
    private ArrayList<User> followingList = new ArrayList<User>();
    private ArrayList<User> requestedList = new ArrayList<User>();
    private ArrayList<User> followingListNew = new ArrayList<User>();
    private ArrayList<User> requestedListNew = new ArrayList<User>();
    private User user = new User("test","test@test.com", followingList, requestedList);

    /**
     * Test User Name getter is working
     */
    @Test
    public void testGetUserName(){
        assertTrue(user.getName().equals("test"));

    }
    /**
     * Test User email getter is working
     */
    @Test
    public void testGetUserEmail(){
        assertTrue(user.getEmail().equals("test@test.com"));

    }
    /**
     * Test User followingList getter is working
     */
    @Test
    public void testGetFollowingList(){
        assertTrue(user.getFollowingList().equals(followingList));

    }
    /**
     * Test User requestedList getter is working
     */
    @Test
    public void testGetRequestList(){
        assertTrue(user.getRequestedList().equals(requestedList));

    }
    /**
     * Test user name setter is working
     */
    @Test
    public void testSetUserName(){
        user.setName("testtest");
        assertTrue(user.getName().equals("testtest"));
    }
    /**
     * Test user email setter is working
     */
    @Test
    public void testSetUserEmail(){
        user.setEmail("testtest@test.com");
        assertTrue(user.getEmail().equals("testtest@test.com"));
    }
    /**
     * Test user following list setter is working
     */
    @Test
    public void testSetFollowList(){
        user.setFollowingList(followingListNew);
        assertTrue(user.getFollowingList().equals(followingListNew));
    }
    /**
     * Test user requested list setter is working
     */
    @Test
    public void testSetRequestList(){
        user.setRequestedList(requestedListNew);
        assertTrue(user.getRequestedList().equals(requestedListNew));
    }


}
