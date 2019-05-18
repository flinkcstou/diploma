package kz.greetgo.diploma.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.diploma.controller.register.RestaurantOrderRegister;
import kz.greetgo.diploma.controller.register.model.*;
import kz.greetgo.diploma.register.dao.RestaurantOrderDao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.joining;

@Bean
public class RestaurantOrderRegisterImpl implements RestaurantOrderRegister {

	public BeanGetter<RestaurantOrderDao> restaurantOrderDao;
	public BeanGetter<AntAlgorithmRegisterImpl> antAlgorithmRegister;

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

			}
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
		orderLists = restaurantOrderDao.get().selectOrderListById(personId);

		return orderLists;
	};

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
	};
	@Override
	public List<Item> prepareOfferAlgorithmAnt (List<OrderItem> orderItems) {

		antAlgorithmRegister.get().AntColonyOptimization(orderItems.size(),0,orderItems.size());

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
