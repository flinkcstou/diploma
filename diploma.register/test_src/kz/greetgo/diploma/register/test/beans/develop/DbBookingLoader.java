package kz.greetgo.diploma.register.test.beans.develop;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.diploma.controller.register.model.Booking;
import kz.greetgo.diploma.controller.register.model.Comments;
import kz.greetgo.diploma.controller.register.model.CommentsLike;
import kz.greetgo.diploma.controller.register.model.Table;
import kz.greetgo.diploma.register.beans.all.IdGenerator;
import kz.greetgo.diploma.register.test.dao.BookingTestDao;
import kz.greetgo.util.RND;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
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
		restaurantTable();
		booking();
		booking();
		booking();
		booking();
		addComments();
		addCommentsLike();
		logger.info("Finish loading persons");
	}


	private void booking() throws Exception {

		Table table = new Table();
		table.status = 1;
		table.name = "left";


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
		booking.tableType = table.name;
		booking.phoneNumber = "8787777777777777";
		booking.personId = bookingTestDao.get().getPersonId();
		bookingTestDao.get().insertBooking(booking);
	}

	private void restaurantTable() throws Exception {

		Table table = new Table();
		table.status = 1;

		table.name = "table-one";
		table.personNumber =14;
		bookingTestDao.get().insertRestaurantTable(table);
		table.name = "table-two";
		table.personNumber =6;
		bookingTestDao.get().insertRestaurantTable(table);
		table.name = "table-three";
		table.personNumber =6;
		bookingTestDao.get().insertRestaurantTable(table);
		table.name = "table-four";
		table.personNumber =6;
		bookingTestDao.get().insertRestaurantTable(table);
		table.name = "table-five";
		table.personNumber =4;
		bookingTestDao.get().insertRestaurantTable(table);
		table.name = "table-six";
		table.personNumber =4;
		bookingTestDao.get().insertRestaurantTable(table);
		table.name = "table-seven";
		table.personNumber =6;
		bookingTestDao.get().insertRestaurantTable(table);
		table.name = "table-eight";
		table.personNumber =4;
		bookingTestDao.get().insertRestaurantTable(table);
		table.name = "table-nine";
		table.personNumber =4;
		bookingTestDao.get().insertRestaurantTable(table);
		table.name = "table-ten";
		table.personNumber =4;
		bookingTestDao.get().insertRestaurantTable(table);
		table.name = "table-eleven";
		table.personNumber =4;
		bookingTestDao.get().insertRestaurantTable(table);
		table.name = "table-twelve";
		table.personNumber =4;
		bookingTestDao.get().insertRestaurantTable(table);
		table.name = "table-thirteen";
		table.personNumber =4;
		bookingTestDao.get().insertRestaurantTable(table);
		table.name = "table-fourteen";
		table.personNumber =4;
		bookingTestDao.get().insertRestaurantTable(table);
		table.name = "table-fifteen";
		table.personNumber =4;
		bookingTestDao.get().insertRestaurantTable(table);
		table.name = "table-sixteen";
		table.personNumber =4;
		bookingTestDao.get().insertRestaurantTable(table);
		table.name = "table-seventeen";
		table.personNumber =6;
		bookingTestDao.get().insertRestaurantTable(table);
	}

	private void addComments() {

		Comments comments = new Comments();
		comments.personId = bookingTestDao.get().getPersonId();
		comments.messages = "Зашли с семьей 23 января поужинать. Заказали сет шашлычный. " +
			"Во первых официант с недовольным видом принимала заказ и без конца намекала на спиртное, " +
			"зачем навязывать если люди не пьют. В вторых заказ ожидали долго, " +
			"в третьих еще весь шашлык имел запах неприятный, тухлый, но ели через силу. " +
			"И в итоге из нашей семьи 2 человек из 4 отравились сразу вечером этого же дня. " +
			"3 дня была рвота и понос. Позвонила администратору сказала помет меры. " +
			"И после этого директор заведения даже не извенился за такое. Видимо туда заходят все выпивать, " +
			"а таким людям пойдёт и тухлое мясо все равно не поймут потом от чего отравились. Я считаю, " +
			"что в первую очередь нужно контролировать свою кухню так может человек и умереть от не свежей продукции. " +
			"Теперь от вида этого ПАБА меня тошнит. Больше не приду никогда туда и другим не советую.";

		comments.date = new Date();
		comments.date = new Timestamp(comments.date.getTime());

		for(Integer itemId : bookingTestDao.get().getItemId())
			{
				comments.itemId = itemId;
				bookingTestDao.get().insertComments(comments);
			}
	}

	private void addCommentsLike() {

		int i = 1;
		CommentsLike commentsLike = new CommentsLike();
		commentsLike.commentsId = 1;
		for(String personId : bookingTestDao.get().listPersonId())
			{
				commentsLike.personId = personId;
				for(int index = 1; index < 5; index++)
					{
						commentsLike.commentsId = index;
						commentsLike.liked = 1;
						commentsLike.disliked = 1;
						bookingTestDao.get().insertCommentsLike(commentsLike);
					}

			}

	}

}



