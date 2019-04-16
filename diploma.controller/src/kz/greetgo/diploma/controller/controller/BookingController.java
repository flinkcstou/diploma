package kz.greetgo.diploma.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.diploma.controller.register.BookingRegister;
import kz.greetgo.diploma.controller.register.model.Booking;
import kz.greetgo.diploma.controller.security.PublicAccess;
import kz.greetgo.diploma.controller.util.Controller;
import kz.greetgo.mvc.annotations.Json;
import kz.greetgo.mvc.annotations.Par;
import kz.greetgo.mvc.annotations.ToJson;
import kz.greetgo.mvc.annotations.on_methods.ControllerPrefix;
import kz.greetgo.mvc.annotations.on_methods.OnGet;

import java.text.ParseException;

@Bean
@ControllerPrefix("/booking")
public class BookingController implements Controller {

	public BeanGetter<BookingRegister> bookingRegister;

	@ToJson
	@PublicAccess
	@OnGet("/check-time")
	public String checkTime(@Par("booking") @Json Booking booking) {

		return bookingRegister.get().checkTime(booking);
	}

	@ToJson
	@PublicAccess
	@OnGet("/save")
	public void insertBooking(@Par("booking") @Json Booking booking) throws ParseException {

		bookingRegister.get().insertBooking(booking);
	}

}