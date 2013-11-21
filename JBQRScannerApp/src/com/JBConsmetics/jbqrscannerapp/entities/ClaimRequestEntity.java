package com.JBConsmetics.jbqrscannerapp.entities;

import com.JBCosmetics.jbqrscannerapp.common.JBConstants;

public class ClaimRequestEntity {

	private int id;
	private String auth;
	private int qr_code_id;
	private String lat;
	private String longs;
	private String hor_accuracy;
	private String var_accuracy;
	private String deviceid;
	private long scanTime;
	private int isVarified = JBConstants.INACTIVE;

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public int getQr_code_id() {
		return qr_code_id;
	}

	public void setQr_code_id(int qr_code_id) {
		this.qr_code_id = qr_code_id;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLongs() {
		return longs;
	}

	public void setLongs(String longs) {
		this.longs = longs;
	}

	public String getHor_accuracy() {
		return hor_accuracy;
	}

	public void setHor_accuracy(String hor_accuracy) {
		this.hor_accuracy = hor_accuracy;
	}

	public String getVar_accuracy() {
		return var_accuracy;
	}

	public void setVar_accuracy(String var_accuracy) {
		this.var_accuracy = var_accuracy;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getScanTime() {
		return scanTime;
	}

	public void setScanTime(long scanTime) {
		this.scanTime = scanTime;
	}

	public int getIsVarified() {
		return isVarified;
	}

	public void setIsVarified(int isVarified) {
		this.isVarified = isVarified;
	}

}
