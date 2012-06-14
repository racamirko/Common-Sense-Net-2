package com.commonsensenet.realfarm.model;

public class Spraying {

	private int mactionid;
	private String mactionType;
		private int mQuantity1;
		private String mUnits;
	private String mday;
	private int muserid;
	private int mplotid;
	private String mprobType;
	private int msend;
	private int mIsadmin;
	private String mActionPerformedDate;
	private String mPestcideType;;


	public Spraying(int actionid,String actionType,int quantity1,
			String Units, String day,int userid, int plotid, String probType,
			  int send,int Isadmin,String ActionPerformedDate,
			  String PestcideType ) {

		 mactionid=actionid;
		 mQuantity1=quantity1;
	
		mUnits=Units;
		 mday=day;
		 muserid=userid;
		 mplotid=plotid;
		
		 mprobType=probType;
	
		msend=send;
		mIsadmin=Isadmin;
		mActionPerformedDate=ActionPerformedDate;
		mPestcideType=PestcideType;
		
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
				 
				 public String getProbtype() {
						return mprobType;
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
							 public String getPesticidtype() {
									return mPestcideType;
								}

							
}
