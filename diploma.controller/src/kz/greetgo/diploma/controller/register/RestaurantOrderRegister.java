package kz.greetgo.diploma.controller.register;

import kz.greetgo.diploma.controller.register.model.*;

import java.util.ArrayList;
import java.util.List;

public interface RestaurantOrderRegister {

	ArrayList<Item> getItemList();

	ArrayList<Person> getCustomerList();

	void postOrderItems(Orders orders);

	List<OrderList> getOrderList();

	void updateOrderStatus(OrderList orderList);

	Orders getOrdersbyId(Integer id);

	String deleteOrderbyId(Integer id);

	List<Item> prepareOffer(List<OrderItem> orderItems);

	List<Item> prepareOfferAlgorithmAnt(List<OrderItem> orderItems);

	List<OrderList> getOrderListById(String personId);

	List<Comments> getCommentsByItemId(Integer itemId);

	String setComments(Comments comments);

	String setCommentsLike(CommentsLike commentsLike);

	List<CommentsLike> setCommentsLikeByPersonId(String personId);

	List<Item> getItemListByCategory(String category);

	List<String> getListCategory();
}
