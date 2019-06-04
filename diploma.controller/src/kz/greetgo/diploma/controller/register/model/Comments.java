package kz.greetgo.diploma.controller.register.model;

import java.util.Date;

public class Comments {

	public Integer id;

	public Integer itemId;


	public String itemName;

	public String personId;

	public String personName;

	public String messages;

	public Date date;

	public String dateFormatter;

	public Integer liked;

	public Integer disliked;

	@Override
	public String toString() {

		return "Comments{" +
			"id=" + id +
			", itemId=" + itemId +
			", itemName='" + itemName + '\'' +
			", personId='" + personId + '\'' +
			", personName='" + personName + '\'' +
			", messages='" + messages + '\'' +
			", date=" + date +
			", dateFormatter='" + dateFormatter + '\'' +
			", liked=" + liked +
			", disliked=" + disliked +
			'}';
	}
}
