package com.JBCosmetics.jbqrscannerapp.entities;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationResponseEntity {

	public class Response {

		private String auth;
		private int result;
		private String msg;
		private int claimScans;
		private int scanCount;
		private List<QrCode> qrCodes = new ArrayList<QrCode>();

		public String getAuth() {
			return auth;
		}

		public void setAuth(String auth) {
			this.auth = auth;
		}

		public int getResult() {
			return result;
		}

		public void setResult(int result) {
			this.result = result;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public int getClaimScans() {
			return claimScans;
		}

		public void setClaimScans(int claimScans) {
			this.claimScans = claimScans;
		}

		public int getScanCount() {
			return scanCount;
		}

		public void setScanCount(int scanCount) {
			this.scanCount = scanCount;
		}

		public List<QrCode> getQrCodes() {
			return qrCodes;
		}

		public void setQrCodes(List<QrCode> qrCodes) {
			this.qrCodes = qrCodes;
		}

	}

	public class QrCode {

		private int id;
		private String code;
		private String pin;
		private int locationID;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getPin() {
			return pin;
		}

		public void setPin(String pin) {
			this.pin = pin;
		}

		public int getLocationID() {
			return locationID;
		}

		public void setLocationID(int locationID) {
			this.locationID = locationID;
		}

	}
}
