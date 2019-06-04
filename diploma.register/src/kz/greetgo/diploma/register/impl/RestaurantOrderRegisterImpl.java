package kz.greetgo.diploma.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.diploma.controller.register.RestaurantOrderRegister;
import kz.greetgo.diploma.controller.register.model.*;
import kz.greetgo.diploma.register.beans.all.IdGenerator;
import kz.greetgo.diploma.register.dao.RestaurantOrderDao;
import kz.greetgo.security.password.PasswordEncoder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

@Bean
public class RestaurantOrderRegisterImpl implements RestaurantOrderRegister {

	public BeanGetter<RestaurantOrderDao> restaurantOrderDao;

	public BeanGetter<AntAlgorithmRegisterImpl> antAlgorithmRegister;

	public BeanGetter<IdGenerator> idGenerator;

	public BeanGetter<PasswordEncoder> passwordEncoder;


	@Override
	public ArrayList<Item> getItemList() {

		return restaurantOrderDao.get().selectItem();

	}

	@Override
	public ArrayList<Person> getCustomerList() {

		return restaurantOrderDao.get().selectCustomer();
	}

	@Override
	public void postOrderItems(Orders orders) {

		List<Order> orderList = new ArrayList<>();
		Integer orderItemId;
		OrderStatus orderStatus = new OrderStatus();
		try
			{
				insertPerson(orders.personId);
			} catch(Exception e)
			{
				e.printStackTrace();
			}

		if(orders.orderId == 0)
			{
				int id = restaurantOrderDao.get().insertOorder(orders);
				for(OrderItem orderItem : orders.orderItems)
					{
						orderItem.orderId = id;
						orderItemId = restaurantOrderDao.get().inserOrderItem(orderItem);
						{
							orderStatus.orderId = id;
							orderStatus.orderItemId = orderItemId;
							orderStatus.updateDate = new Timestamp(new Date().getTime());
							orderStatus.status = 1;

							restaurantOrderDao.get().insertOrderStatus(orderStatus);
						}
						orderStatus = new OrderStatus();
					}
			} else
			{
				restaurantOrderDao.get().updateOorder(orders);
				for(OrderItem orderItem : orders.orderItems)
					{
						if(orderItem.orderItemId == null)
							{
								orderItem.orderItemId = restaurantOrderDao.get().inserOrderItem(orderItem);

								{
									orderStatus.orderId = orders.orderId;
									orderStatus.orderItemId = orderItem.orderItemId;
									orderStatus.updateDate = new Timestamp(new Date().getTime());
									orderStatus.status = 1;
									restaurantOrderDao.get().insertOrderStatus(orderStatus);
								}

							} else
							{
								restaurantOrderDao.get().updaterderItem(orderItem);

								{
									orderStatus.orderId = orders.orderId;
									orderStatus.orderItemId = orderItem.orderItemId;
									orderStatus.updateDate = new Timestamp(new Date().getTime());
									restaurantOrderDao.get().updateOrderStatus(orderStatus);
								}
							}


					}

				deleteOrderItems(orders);

			}
	}

	private void deleteOrderItems(Orders order) {

		List<Integer> ids = restaurantOrderDao.get().selectorOrderItemsById(order.orderId).stream().map(v -> v.orderItemId).collect(Collectors.toList());
		for(OrderItem orderItem : order.orderItems)
			{
				if(ids.contains(orderItem.orderItemId))
					{
						ids.remove(orderItem.orderItemId);
					}
			}
		if(ids.size() != 0)
			{
				for(Integer id : ids)
					{
						restaurantOrderDao.get().deleteOrderStatusByOrderItemId(id);
					}
				for(Integer id : ids)
					{
						restaurantOrderDao.get().deleteorderItemsById(id);
					}
			}

		System.out.println(ids);
	}

	@Override
	public List<OrderList> getOrderList() {

		List<OrderList> orderLists = new ArrayList<>();
		orderLists = restaurantOrderDao.get().selectOrderList();
		return orderLists;
	}

	@Override
	public List<OrderList> getOrderListById(String personId) {

		List<OrderList> orderLists = new ArrayList<>();
		String id = restaurantOrderDao.get().getPersonId(personId);
		if(isNullOrEmpty(id))
			{
				orderLists = restaurantOrderDao.get().selectOrderListById(personId);

			} else
			{
				orderLists = restaurantOrderDao.get().selectOrderListById(id);
			}
		return orderLists;
	}

	private boolean isNullOrEmpty(String str) {

		if(str != null && !str.isEmpty())
			return false;
		return true;
	}

	@Override
	public List<Comments> getCommentsByItemId(Integer itemId) {

		return restaurantOrderDao.get().getCommentsByItemId(itemId);
	}

	@Override
	public String setComments(Comments comments) {


		String id = restaurantOrderDao.get().getPersonId(comments.personId);
		comments.date = new Timestamp(comments.date.getTime());
		if(isNullOrEmpty(id))
			{
				restaurantOrderDao.get().setComments(comments);

			} else
			{
				comments.personId = id;
				restaurantOrderDao.get().setComments(comments);
			}
		return "set comments";
	}

	@Override
	public String setCommentsLike(CommentsLike commentsLike) {

		String id = restaurantOrderDao.get().getPersonId(commentsLike.personId);
		if(!isNullOrEmpty(id))
			{
				commentsLike.personId = id;
			}
		if(!isNullOrEmpty(restaurantOrderDao.get().selectCommentsByLiked(commentsLike)))
			{
				commentsLike.liked = null;
			}
		if(!isNullOrEmpty(restaurantOrderDao.get().selectCommentsByDisliked(commentsLike)))
			{
				commentsLike.disliked = null;
			}

		restaurantOrderDao.get().setCommentsLike(commentsLike);


		return "set comments";
	}

	@Override
	public List<CommentsLike> setCommentsLikeByPersonId(String personId) {

		String id = restaurantOrderDao.get().getPersonId(personId);
		if(!isNullOrEmpty(id))
			{
				personId = id;
			}

		return restaurantOrderDao.get().setCommentsLikeByPersonId(personId);
	}

	@Override
	public List<Item> getItemListByCategory(String category) {

		return restaurantOrderDao.get().selectItemByCategory(category);
	}

	@Override
	public List<String> getListCategory() {

		return  restaurantOrderDao.get().getListCategory();

	}


	@Override
	public void updateOrderStatus(OrderList orderList) {

		restaurantOrderDao.get().updateOrderStatusById(orderList);
	}


	@Override
	public Orders getOrdersbyId(Integer id) {

		Orders orders = new Orders();
		List<OrderItem> orderItemList = new ArrayList<>();
		orders = restaurantOrderDao.get().selectorOrdersById(id);
		orderItemList = restaurantOrderDao.get().selectorOrderItemsById(id);
		for(OrderItem orderItem : orderItemList)
			{
				orders.orderItems.add(orderItem);
			}

		return orders;

	}

	@Override
	public String deleteOrderbyId(Integer id) {


		restaurantOrderDao.get().deleteOrderStatusByOrderId(id);
		restaurantOrderDao.get().deleteOrderItemByorderId(id);
		restaurantOrderDao.get().deleteOrdeeById(id);
		System.out.println("deleted: " + id);
		return "deleted: " + id;
	}

	@Override
	public List<Item> prepareOffer(List<OrderItem> orderItems) {


		String collect = orderItems.stream().map(orderItem -> orderItem.itemId + "").collect(joining(","));
		List<Item> items = new ArrayList<>();
		List<ItemCount> itemCounts = new ArrayList<>();
		itemCounts = restaurantOrderDao.get().prepareOffer(collect);
		if(itemCounts.size() == 0)
			{
				return items;
			}
		for(ItemCount itemCount : itemCounts)
			{
				System.out.println(itemCount);
				items.add(restaurantOrderDao.get().selectItemById(itemCount.itemId));
			}
		return items;
	}

	;

	@Override
	public List<Item> prepareOfferAlgorithmAnt(List<OrderItem> orderItems) {

		antAlgorithmRegister.get().AntColonyOptimization(orderItems.size(), 0, orderItems.size());

		String collect = orderItems.stream().map(orderItem -> orderItem.itemId + "").collect(joining(","));
		List<Item> items = new ArrayList<>();
		List<ItemCount> itemCounts = new ArrayList<>();
		itemCounts = restaurantOrderDao.get().prepareOffer(collect);
		if(itemCounts.size() == 0)
			{
				return items;
			}
		for(ItemCount itemCount : itemCounts)
			{
				System.out.println(itemCount);
				items.add(restaurantOrderDao.get().selectItemById(itemCount.itemId));
			}
		return items;
	}


	private void user(String id) throws Exception {

		String accountName = idGenerator.get().newId();
		String encryptPassword = passwordEncoder.get().encode("111");
		restaurantOrderDao.get().insertPerson(id, accountName, encryptPassword);
	}

	private void insertPerson(String personId) throws Exception {

		String id = restaurantOrderDao.get().selectPersonID(personId);
		if(id == null)
			{
				user(personId);
			}
	}


	public static void main(String[] args) {

		List<Item> list = new ArrayList<>();
		{
			Item item = new Item();
			item.itemId = 1;
			list.add(item);
		}

		{
			Item item = new Item();
			item.itemId = 2;
			list.add(item);
		}

		{
			Item item = new Item();
			item.itemId = 3;
			list.add(item);
		}

		System.out.println(list);


		String collect = list.stream().map(item -> item.itemId + "").collect(joining(","));

		System.out.println(collect);

	}

}
