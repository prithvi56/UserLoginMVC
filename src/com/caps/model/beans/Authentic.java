package com.caps.model.beans;

public class Authentic {
	
	private static int Attempt;
	private static boolean lock;
	private static int lockTime;
	
	public static int getAttempt() {
		return Attempt;
	}
	public static void setAttempt(int attempt) {
		Attempt = attempt;
	}
	
	public static boolean isLock() {
		return lock;
	}
	public static void setLock(boolean lock) {
		Authentic.lock = lock;
	}
	public static int getLockTime() {
		return lockTime;
	}
	public static void setLockTime(int lockTime) {
		Authentic.lockTime = lockTime;
	}
	
	
}
