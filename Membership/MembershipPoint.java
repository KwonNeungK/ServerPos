package Membership;
import java.sql.Timestamp;

public class MembershipPoint {
	private String name;
	private int contact;
	private int point;

	public MembershipPoint(String name, int contact, int point) {
		this.name = name;
		this.contact = contact;
		this.point = point;

	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getContact() {
		return contact;
	}


	public void setContact(int contact) {
		this.contact = contact;
	}


	public int getPoint() {
		return point;
	}


	public void setPoint(int point) {
		this.point = point;
	}


	



}