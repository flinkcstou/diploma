package kz.greetgo.diploma.register.test.beans.develop;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.diploma.controller.register.model.Booking;
import kz.greetgo.diploma.register.beans.all.IdGenerator;
import kz.greetgo.diploma.register.test.dao.BookingTestDao;
import kz.greetgo.util.RND;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

@Bean
public class DbBookingLoader {

	public BeanGetter<BookingTestDao> bookingTestDao;

	final Logger logger = Logger.getLogger(getClass());

	public BeanGetter<IdGenerator> idGenerator;

	public void loadTestData() throws Exception {

		loadBooking();
		logger.info("FINISH");
	}

	@SuppressWarnings("SpellCheckingInspection")
	private void loadBooking() throws Exception {

		logger.info("Start loading restaurant order...");
		booking();
		booking();
		booking();
		booking();
		logger.info("Finish loading persons");
	}


	private void booking() throws Exception {

		String id = idGenerator.get().newId();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date recordDate = sdf.parse("2019-05-05");

		Booking booking = new Booking();
		booking.bookingId = RND.plusInt(1000); ;
		booking.numberOfPeople = RND.plusInt(1000);
		booking.recordTime = new Date();
		booking.recordDateDay = "2019-05-05";
		booking.recordDateFrom = String.valueOf(DateUtils.addHours(new Date(), 0));
		booking.recordDateTo = String.valueOf(DateUtils.addHours(new Date(), 2));
		booking.tableType = "left";
		booking.phoneNumber = "8787777777777777";
		booking.customerId = 1;
		bookingTestDao.get().insertBooking(booking);
	}


}


