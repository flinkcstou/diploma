package kz.greetgo.diploma.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.diploma.controller.register.BookingRegister;
import kz.greetgo.diploma.controller.register.model.Booking;
import kz.greetgo.diploma.controller.register.model.Table;
import kz.greetgo.diploma.register.beans.all.IdGenerator;
import kz.greetgo.diploma.register.dao.BookingDao;
import kz.greetgo.security.password.PasswordEncoder;
import org.apache.commons.lang.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Bean
public class BookingRegisterImpl implements BookingRegister {

	public BeanGetter<BookingDao> bookingDao;

	public BeanGetter<IdGenerator> idGenerator;

	public BeanGetter<PasswordEncoder> passwordEncoder;


	@Override
	public String checkTime(Booking booking) {

		List<Booking> booking1;

		booking1 = bookingDao.get().checkTime(booking);
		System.out.println(booking1);

		if(booking1.size() == 0)
			{
				return "empty";
			} else
			{
				return "full";
			}
	}

	@Override
	public void insertBooking(Booking booking) throws Exception {

		System.out.println(booking);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date recordDate = sdf.parse(booking.recordDateDay);

		String[] timesFrom = booking.recordDateFrom.split(":");
		String[] timesTo = booking.recordDateTo.split(":");

		booking.recordDateFrom = String.valueOf(DateUtils.addHours(recordDate, Integer.parseInt(timesFrom[0])));
		booking.recordDateTo = String.valueOf(DateUtils.addHours(recordDate, Integer.parseInt(timesTo[0])));

		String id = bookingDao.get().selectPersonID(booking.personId);
		if(id == null)
			{
				user(booking.personId);
			}
		bookingDao.get().insertBooking(booking);
	}

	@Override
	public List<String> getRestaurantTable() {

		return bookingDao.get().selectRestaurantTable();
	}

	@Override
	public String getPersonId(String username) {

		String id = bookingDao.get().getPersonId(username);
		System.out.println(id);
		return id;
	}

	@Override
	public List<Table> getRestaurantTableList() {

		return bookingDao.get().getRestaurantTableList();
	}

	public static void main(String[] args) {

		System.out.println(Integer.parseInt("12:00"));
	}

	private void user(String id) throws Exception {

		String accountName = idGenerator.get().newId();
		String encryptPassword = passwordEncoder.get().encode("111");
		bookingDao.get().insertPerson(id, accountName, encryptPassword);
	}

}
