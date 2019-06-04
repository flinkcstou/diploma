package kz.greetgo.diploma.register.test.dao;

import kz.greetgo.diploma.controller.register.model.Booking;
import kz.greetgo.diploma.controller.register.model.Comments;
import kz.greetgo.diploma.controller.register.model.CommentsLike;
import kz.greetgo.diploma.controller.register.model.Table;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BookingTestDao {

	@Insert("insert into booking(booking_id, number_of_people, record_time, record_date_day, record_date_from, " +
		"record_date_to, table_type, phone_number, person_id) " +
		"values (#{booking.bookingId}, #{booking.numberOfPeople}, #{booking.recordTime}, #{booking.recordDateDay}, " +
		"#{booking.recordDateFrom}, #{booking.recordDateTo}, #{booking.tableType}, #{booking.phoneNumber}, #{booking.personId})")
	void insertBooking(@Param("booking") Booking booking);


	@Select("select item_id from item limit 20")
	List<Integer> getItemId();

	@Select("select id from person limit 1")
	String getPersonId();

	@Select("select id from person")
	List<String> listPersonId();


	@Insert("insert into restaurant_table(name, status,person_number) " +
		"values (#{table.name}, #{table.status}, #{table.personNumber})")
	void insertRestaurantTable(@Param("table") Table table);


	@Insert("insert into comments(item_id, person_id, messages,date ) " +
		"values ( #{comments.itemId}, #{comments.personId}, #{comments.messages}, #{comments.date})")
	void insertComments(@Param("comments") Comments comments);

	@Insert("insert into comments_like(comments_id, person_id, liked ,disliked) " +
		"values ( #{commentsLike.commentsId}, #{commentsLike.personId}, #{commentsLike.liked}, #{commentsLike.disliked})")
	void insertCommentsLike(@Param("commentsLike") CommentsLike commentsLike);
}
