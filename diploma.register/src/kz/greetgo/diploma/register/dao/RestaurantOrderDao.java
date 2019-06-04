package kz.greetgo.diploma.register.dao;

import kz.greetgo.diploma.controller.model.UserCan;
import kz.greetgo.diploma.controller.register.model.*;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;

public interface RestaurantOrderDao {

	@Select("select *from item")
	ArrayList<Item> selectItem();


	@Select("select item_id, name, price, description, image from item where category=#{category};")
	List<Item> selectItemByCategory(@Param("category") String category);

	@Select("select distinct category from item")
	List<String> getListCategory();


	@Select("select * from item where item_id =#{itemId}")
	Item selectItemById(@Param("itemId") Integer itemId);

	@Select("select id as personId, username as name from person")
	ArrayList<Person> selectCustomer();


	@Select("insert into oorder(oorder_no, person_id, p_method, g_total, booking_id) " +
		"values (#{order.orderNo}, #{order.personId}, #{order.pMethod}, #{order.gTotal}, #{order.bookingId}) returning oorder_id")
	Integer insertOorder(@Param("order") Orders orders);

	@Update("update oorder set " +
		"oorder_no =#{orders.orderNo}, person_id= #{orders.personId}, " +
		"p_method= #{orders.pMethod}, g_total= #{orders.gTotal} " +
		"where oorder_id = #{orders.orderId}")
	void updateOorder(@Param("orders") Orders orders);

	@Update("update order_items set " +
		"oorder_id =#{orderItem.orderId}, " +
		"item_id= #{orderItem.itemId}, " +
		"quantity= #{orderItem.quantity} " +
		"where order_item_id = #{orderItem.orderItemId} ")
	void updaterderItem(@Param("orderItem") OrderItem orderItem);


	@Update("update order_status set " +
		"update_date =#{orderStatus.updateDate} " +
		"where order_item_id = #{orderStatus.orderItemId}  and " +
		"oorder_id= #{orderStatus.orderId}")
	void updateOrderStatus(@Param("orderStatus") OrderStatus orderStatus);


	@Update("update order_status\n" +
		" set status = #{orderList.status}\n" +
		" where oorder_id = #{orderList.orderId}")
	void updateOrderStatusById(@Param("orderList") OrderList orderList);

	@Select("insert into order_items( oorder_id, item_id, quantity) " +
		"values (#{order.orderId}, #{order.itemId}, #{order.quantity}) returning order_item_id")
	Integer inserOrderItem(@Param("order") OrderItem orderItem);

	@Select("insert into order_status( oorder_id, order_item_id, update_date , status) " +
		"values (#{orderStatus.orderId}, #{orderStatus.orderItemId}, #{orderStatus.updateDate}, #{orderStatus.status})")
	Integer insertOrderStatus(@Param("orderStatus") OrderStatus orderStatus);

	@Select("select distinct o.oorder_no, \n" +
		"  o.oorder_id   as orderId,\n" +
		"  o.oorder_no   as orderNo,\n" +
		"  c.username        as name,\n" +
		"  o.g_total     as gTotal,\n" +
		"  o.p_method    as pMethod,\n" +
		"  b.record_date_day,\n" +
		"  b.phone_number as phoneNumber,\n" +
		"  status.status as status\n" +
		"from oorder o\n" +
		"  left join person c on o.person_id= c.id\n" +
		"  left join order_status status on o.oorder_id = status.oorder_id" +
		"  left join booking b on o.booking_id = b.booking_id")
	List<OrderList> selectOrderList();

	@Select("select distinct o.oorder_no, \n" +
		"  o.oorder_id   as orderId,\n" +
		"  o.oorder_no   as orderNo,\n" +
		"  c.username        as name,\n" +
		"  o.g_total     as gTotal,\n" +
		"  o.p_method    as pMethod,\n" +
		"  b.record_date_day,\n" +
		"  b.phone_number as phoneNumber,\n" +
		"  status.status as status\n" +
		"from oorder o\n" +
		"  left join person c on o.person_id= c.id\n" +
		"  left join order_status status on o.oorder_id = status.oorder_id" +
		"  left join booking b on o.booking_id = b.booking_id " +
		"where o.person_id = #{personId}")
	List<OrderList> selectOrderListById(@Param("personId") String personId);

	@Select("select o.oorder_id as orderId, o.oorder_no as orderNo," +
		" c.username as name , o.g_total as gTotal, o.p_method as pMethod from\n" +
		"  oorder o inner join person c on o.person_id= c.id" +
		"where o.oorder_id = #{id}\n")
	List<OrderList> selectorderById(@Param("id") Integer id);


	@Select("select id from person where username = #{id}")
	String getPersonId(@Param("id") String id);


	@Select("select \n" +
		"item_id as itemId, count(*) as count \n" +
		"from order_items \n" +
		"where 1 = 1 \n" +
		"      and oorder_id in (select oorder_id from order_items where item_id in (${itemId})) \n" +
		"      and item_id not in (${itemId}) \n" +
		"group by item_id \n" +
		"order by count desc limit 2")
	List<ItemCount> prepareOffer(@Param("itemId") String itemId);

	@Delete("delete from oorder where oorder_id = #{id}")
	void deleteOrdeeById(@Param("id") Integer id);

	@Delete("delete from order_items where oorder_id = #{id}")
	void deleteOrderItemByorderId(@Param("id") Integer id);

	@Delete("delete from order_items where order_item_id = #{id}")
	void deleteorderItemsById(@Param("id") Integer id);

	@Delete("delete from order_status where order_item_id = #{id}")
	void deleteOrderStatusByOrderItemId(@Param("id") Integer id);

	@Delete("delete from order_status where oorder_id = #{id}")
	void deleteOrderStatusByOrderId(@Param("id") Integer id);


	@Select("select distinct\n" +
		"  o.oorder_id   as orderId,\n" +
		"  o.oorder_no   as orderNo,\n" +
		"  o.person_id   as personId,\n" +
		"  o.p_method    as pMethod,\n" +
		"  o.g_total     as gTotal,\n" +
		"  o.booking_id  as bookingId,\n" +
		"  status.status as status\n" +
		"  from oorder o\n" +
		"  left join order_status status on o.oorder_id = status.oorder_id\n" +
		"  left join person c on o.person_id = c.id" +
		"  where o.oorder_id = #{id}")
	Orders selectorOrdersById(@Param("id") Integer id);

	@Select("select\n" +
		"  o.order_item_id as orderItemId,\n" +
		"  o.oorder_id as orderId,\n" +
		"  o.item_id as itemId,\n" +
		"  o.quantity as quantity,\n" +
		"  i.name as itemName,\n" +
		"  i.price as price,\n" +
		"  oo.g_total as total\n" +
		"from order_items as o\n" +
		"  inner join  item as i on o.item_id=i.item_id\n" +
		"  inner join oorder as oo on o.oorder_id = oo.oorder_id where o.oorder_id = #{id}")
	List<OrderItem> selectorOrderItemsById(@Param("id") Integer id);

	@Select("" +
		"  select\n" +
		"  c.id,\n" +
		"  c.messages,\n" +
		"  c.person_id      as personId,\n" +
		"  c.date      			as date,\n" +
		"  c.item_id        as itemId,\n" +
		"  p.username       as personName,\n" +
		"  i.name           as itemName,\n" +
		"  sum(cl.liked)    as liked,\n" +
		"  sum(cl.disliked) as disliked\n" +
		"  from public.comments c left join public.person p on c.person_id = p.id\n" +
		"  left join public.item i on c.item_id = i.item_id\n" +
		"  left join comments_like cl on cl.comments_id = c.id\n" +
		"  where c.item_id = #{itemId}\n" +
		"  group by c.id, p.username, i.name;")
	List<Comments> getCommentsByItemId(@Param("itemId") Integer itemId);


	@Select("select user_can from person_cans where person_id = #{personId}")
	List<UserCan> loadCans(String personId);

	@Insert("insert into Person (id, username, encoded_password, blocked) " +
		"values (#{id}, #{username}, #{encodedPassword}, 0)")
	void insertPerson(@Param("id") String id,
										@Param("username") String username,
										@Param("encodedPassword") String encodedPassword
	);

	@Insert("insert into comments (messages, person_id, item_id, date) " +
		"values (#{comments.messages}, #{comments.personId}, #{comments.itemId}, #{comments.date})")
	void setComments(@Param("comments") Comments comments);

	@Insert("insert into comments_like(comments_id, person_id, liked ,disliked) " +
		" values ( #{commentsLike.commentsId}, #{commentsLike.personId}, #{commentsLike.liked}, #{commentsLike.disliked})" +
		" on conflict(comments_id, person_id ) " +
		" do update set liked = #{commentsLike.liked}, disliked = #{commentsLike.disliked}")
	void setCommentsLike(@Param("commentsLike") CommentsLike commentsLike);


	@Update("update Person set ${fieldName} = #{fieldValue} where id = #{id}")
	void updatePersonField(@Param("id") String id,
												 @Param("fieldName") String fieldName,
												 @Param("fieldValue") Object fieldValue);


	@Select("select id from Person where id = #{id}")
	String selectPersonID(@Param("id") String id);

	@Select("select  liked  from comments_like where comments_id = #{commentsLike.commentsId} and  person_id=#{commentsLike.personId} ")
	String selectCommentsByLiked(@Param("commentsLike") CommentsLike commentsLike);

	@Select("select  disliked from comments_like where comments_id =#{commentsLike.commentsId} and  person_id=#{commentsLike.personId}")
	String selectCommentsByDisliked(@Param("commentsLike") CommentsLike commentsLike);

	@Select("select *from comments_like where person_id=#{personId}")
	List<CommentsLike> setCommentsLikeByPersonId(@Param("personId") String personId);

}
