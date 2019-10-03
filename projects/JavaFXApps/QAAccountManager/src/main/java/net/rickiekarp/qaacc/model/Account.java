package net.rickiekarp.qaacc.model;

import net.rickiekarp.qaacc.view.AccountEditDialog;
import javafx.beans.property.SimpleStringProperty;

public class Account {
	private final SimpleStringProperty name;
	private final SimpleStringProperty mail;
	private final SimpleStringProperty level;
	private final SimpleStringProperty alliance;

	public Account(String aName, String aMail, String aLevel, String aAlli) {

		this.name = new SimpleStringProperty(aName);
		this.mail = new SimpleStringProperty(aMail);
		this.level = new SimpleStringProperty(aLevel);
		this.alliance = new SimpleStringProperty(aAlli);
	}

	public static void setAccount(Account acc) {

		acc.setAccName(AccountEditDialog.accNameTF.getText());
		acc.setAccMail(AccountEditDialog.accMailTF.getText());
		acc.setAccLevel(AccountEditDialog.accLevelTF.getText());
		acc.setAccAlliance(AccountEditDialog.accAllianceTF.getText());
	}

	public static void showAccountDetails(Account acc) {

		AccountEditDialog.accNameTF.setText(acc.getAccName());
		AccountEditDialog.accMailTF.setText(acc.getAccMail());
		AccountEditDialog.accLevelTF.setText(acc.getAccLevel());
		AccountEditDialog.accAllianceTF.setText(acc.getAccAlliance());
	}

	public String getAccName() {
		return name.get();
	}
	public void setAccName(String aName) {
		name.set(aName);
	}

	public String getAccMail() {
		return mail.get();
	}
	public void setAccMail(String aMail) {
		mail.set(aMail);
	}

	public String getAccLevel() {
		return level.get();
	}
	public void setAccLevel(String aLevel) {
		level.set(aLevel);
	}

	public String getAccAlliance() {
		return alliance.get();
	}
	public void setAccAlliance(String aAlli) {
		alliance.set(aAlli);
	}
}
