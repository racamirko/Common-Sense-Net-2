package com.commonsensenet.realfarm;

import android.app.Application;
import android.content.Intent;

/**
 * Main class of application. This class is used to share global information across activities such as the database 
 * @author Julien Freudiger
 *
 */
public class realFarm extends Application {

	  private int id = 0;
	  private ManageDatabase db;

		
	  public ManageDatabase getDatabase(){
		    return db;
		  }
	  
	  public ManageDatabase setDatabase(){
		  db = new ManageDatabase(this);
		  return db;
	  }
	  
	  public void setUserId(int userId){
		  id = userId;
	  }
	  
	  public int getUserId(){
		  return id;
	  }
	}

