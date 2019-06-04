package kz.greetgo.diploma.controller.register;

import kz.greetgo.diploma.controller.register.model.Booking;
import kz.greetgo.diploma.controller.register.model.Table;

import java.util.List;

public interface BookingRegister {

	String checkTime(Booking booking);

	void insertBooking(Booking booking) throws Exception;

	List<String> getRestaurantTable();

	String getPersonId(String username);

	List<Table> getRestaurantTableList();
}
