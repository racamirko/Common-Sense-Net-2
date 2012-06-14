package com.commonsensenet.realfarm.model;

public class Fertilizing {

	private int mactionid;
	private String mactionType;
	private int mQuantity1;
	private String mTypeofFert;
	private String mUnits;
	private String mday;
	private int muserid;
	private int mplotid;
	private int msend;
	private int mIsadmin;
	private String mActionPerformedDate;
	private String mTreatment;


	public Fertilizing(int actionid,String actionType,int qua1, String TypeofFert,
			String Units, String day,int userid, int plotid, 
			  int send,int Isadmin,String ActionPerformedDate ) {

		 mactionid=actionid;
		 mactionType=actionType;
		 mQuantity1=qua1;
		 mTypeofFert=TypeofFert;
		mUnits=Units;
		 mday=day;
		 muserid=userid;
		 mplotid=plotid;
		msend=send;
		mIsadmin=Isadmin;
		mActionPerformedDate=ActionPerformedDate;
	
		
	}

	public int getActionId() {
		return  mactionid;
	}

	
	public String getActionType() {
		return  mactionType;
	}
	 
	public int getquantity1() {
		return mQuantity1;
	}
	 public String getTypeFert() {
			return mTypeofFert;
		}
			 
			 public String getUnits() {
					return mUnits;
				}
				
				 public String getday() {
					return mday;
				}

				 
					
				 public int getuserid() {
					return muserid;
				}
				
				 public int getplotid() {
					return mplotid;
				}
				 
				 
				 
						
						 
		 public int getsent() {
				return msend;
					}
							
					 public int getadmin() {
						return mIsadmin;
						}
							
							 public String getactionPerfDate() {
								return mActionPerformedDate;
							}
							
}