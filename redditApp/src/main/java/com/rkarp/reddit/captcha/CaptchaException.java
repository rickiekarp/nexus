package com.rkarp.reddit.captcha;

public class CaptchaException extends Exception {
	static final long serialVersionUID = 40;
	
	public CaptchaException(String message) {
		super(message);
	}
}
