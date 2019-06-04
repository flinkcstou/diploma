package kz.greetgo.diploma.register.test.beans.develop;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.diploma.register.test.dao.RestaurantOrderTestDao;
import org.apache.log4j.Logger;

@Bean
public class DbRestaurantOrderLoader {

	final Logger logger = Logger.getLogger(getClass());


	public BeanGetter<RestaurantOrderTestDao> restaurantOrderTestDao;

	public Integer idIncreased = 1;

	public void loadTestData() throws Exception {

		loadRestaurantOrder();
		loadCustomer();
		logger.info("FINISH");
	}

	@SuppressWarnings("SpellCheckingInspection")
	private void loadRestaurantOrder() throws Exception {

		logger.info("Start loading restaurant order...");


		loadItem();
		logger.info("Finish loading persons");
	}


	private Integer increaseIdincreased() {

		++idIncreased;
		return idIncreased;
	}

	private Integer setidIncreased(Integer id) {

		if(id < 2)
			{
				id = 1;
			}
		idIncreased = id;
		return idIncreased;
	}

	private Integer takeDefaultIdIncreased() {

		idIncreased = 1;
		return idIncreased;
	}


	private void addItem(Integer id, String name, float price, String category, String description, String image) {

		restaurantOrderTestDao.get().insertItem(id, name, price, category, description, image);
	}

	private void addCustomer(String id, String name) {

		restaurantOrderTestDao.get().insertCustomer(id, name);
	}

	private void loadItem() {

		addItem(increaseIdincreased(), "Chicken Tenders w/Fries", (float) 4.59, "menu-2",
			"Butter chicken is traditionally cooked in a tandoor; but may be grilled, roasted or pan-fried in less authentic preparations. The gravy is made by first heating fresh tomato, garlic, and cardamom into a bright red pulp, which is then pureed after cooling, then the chef adds butter, Khoa and various spices, often including asafoetida, cumin, cloves, cinnamon, ",
			"https://thumb.chocofood.kz/20PvwhhqTXIcJZX721n-8YoMfyg=/fit-in/400x0/https://chocofood.kz/media/site/restaurants/food/logo/2019/02/05/food_cQ6KBwR.png");
		addItem(increaseIdincreased(), "Lettuce and Tomato Burger", (float) 1.99, "menu-2",
			"Naan is a leavened, oven-baked flatbread that’s normally served with all meals. Typically, it will be served hot and brushed with ghee or butter. In non-traditional circles, different varieties of naan are available, like garlic naan or cheese naan. However you eat it, naan acts as almost a spoon to soup up sauce or dipped into chutneys. An Indian meal isn’t complete ",
			"https://thumb.chocofood.kz/x8S31dkhtdCS9jYrHEY8VsGJbug=/fit-in/400x0/https://chocofood.kz/media/site/restaurants/food/logo/2014/08/07/seledka_pod_wyboi.jpg");
		addItem(increaseIdincreased(), "Canned Drinks", (float) 1.00, "menu-2",
			"Alu Gobi is a dry dish made with potatoes (aloo), cauliflower (gobi) and Indian spices. It is yellowish in color, due to the use of turmeric, and occasionally contains kalonji and curry leaves. Other common ingredients include garlic, ginger, onion, coriander stalks, tomato, peas, and cumin. It all adds up to one of the most popular dishes ordered in Indian .",
			"https://thumb.chocofood.kz/x5Y5xT1oaGWPi0z34Se6Q-E-fsU=/fit-in/400x0/https://chocofood.kz/media/site/restaurants/food/logo/2019/02/05/food_SFyaO4T.png");
		addItem(increaseIdincreased(), "steak", (float) 2.49, "menu-2",
			"Belgium is home to culinary favorites like Belgian chocolate and beer — but no dish is more iconic than the Belgian waffle. \n" +
				"While in North America, the Belgian waffle is often massive and drowning in syrup, the traditional version is actually a small street food. Eat this treat like it's meant to be enjoyed — sans forks, either plain or with a small helping of whipped cream ",
			"https://thumb.chocofood.kz/To5LOXwBQ8_JTAPJI0XBVqRRqhg=/fit-in/400x0/https://chocofood.kz/media/site/restaurants/food/logo/2019/02/05/food_HzT5FLV.png");
		addItem(increaseIdincreased(), "tuna steak", (float) 1.79, "menu-2",
			"Common in many Central and Eastern European countries, think of these as a slightly thicker and fluffier version of crepes – oh my god – they are seriously delicious.With apricot jam and whipped cream but today mostly enjoyed with Nutella (duh), these are ah-mazing! Head to Austria to get your fix, this is for sure one of the foods you must eat in .",
			"https://thumb.chocofood.kz/R-07NCcZMC2KRfQzDUaJLEt7dzg=/fit-in/400x0/https://chocofood.kz/media/site/restaurants/food/logo/2019/02/05/food_qwy235Y.png");
		addItem(increaseIdincreased(), "shrimp", (float) 1.00, "menu-2",
			"Black risotto is popular along Croatia's coast, and features a tasty mix of cuttlefish or squid, olive oil, garlic, red wine and squid ink. Called \"crni rižot\" by Croatians, it is known for turning people's teeth black. Other beloved regional dishes include pasticada, a mix of tender beer and gnocchi that's a staple in the region of Dalmatia. No matter where you go in.",
			"https://thumb.chocofood.kz/vH2P3hzZ4Mg1RUc2WZgALXrkaDo=/fit-in/400x0/https://chocofood.kz/media/site/restaurants/food/logo/2019/02/05/food_F3l9vNI.png");
		addItem(takeDefaultIdIncreased(), "Chicken Tenders", (float) 3.50, "menu-1",
			"Alu Gobi is a dry dish made with potatoes (aloo), cauliflower (gobi) and Indian spices. It is yellowish in color, due to the use of turmeric, and occasionally contains kalonji and curry leaves. Other common ingredients include garlic, ginger, onion, coriander stalks, tomato, peas, and cumin. It all adds up to one of the most popular dishes ordered in Indian.",
			"https://thumb.chocofood.kz/dGf3FRfiszVfgrXqodcHkleU3Rw=/fit-in/400x0/https://chocofood.kz/media/site/restaurants/food/logo/2019/05/14/food_oXXvLjo.png");
		addItem(increaseIdincreased(), "Chicken Tenders w/Onion", (float) 5.99, "menu-1",
			"Rogan josh (or roghan josh) is a staple of Kashmiri cuisine: originally it was brought to Kashmir by the Mughals. It is one of the main dishes of the Kashmiri multi-course meal (the “Wazwan”). It consists of braised lamb chunks cooked with a gravy based on browned onions or shallots, yogurt, garlic, ginger and aromatic spices (cloves, bay leaves.",
			"https://thumb.chocofood.kz/mHtv3b_NllmDeUwmj1oV71yQk-I=/fit-in/400x0/https://chocofood.kz/media/site/restaurants/food/logo/2019/05/14/food_iN775gu.png");
		addItem(increaseIdincreased(), "Grilled Cheese Sandwich w/Fries", (float) 3.99, "menu-1",
			"Samosas are a fried or baked pastry with savoury filling, such as spiced potatoes, onions, peas, lentils and sometimes ground lamb, ground beef or ground chicken. They were introduced to India during the Muslim Delhi Sultanate when cooks from the Middle East and Central Asia migrated to work in the kitchens of the Sultan and the nobility. Indian samosas.",
			"https://thumb.chocofood.kz/P9wwq5sxJh03q2Fs9zwyP7-7kNE=/fit-in/400x0/https://chocofood.kz/media/site/restaurants/food/logo/2019/05/14/food_7ajwsgW.png");
		addItem(increaseIdincreased(), "Soup", (float) 2.50, "menu-1",
			"Vindaloo itself is a curry dish popular in the region of Goa. It is known globally in its Anglo-Indian form as a staple of curry house menus, often regarded as a fiery spicy dish, though it is not necessarily the hottest dish around. A variety of meats can become a vindaloo dish such as lamb or chicken, but beef vindaloo is one of the most popular versions.",
			"https://thumb.chocofood.kz/DcKzYOe3kj3w9aT2YBLMIVKQdBQ=/fit-in/400x0/https://chocofood.kz/media/site/restaurants/food/logo/2019/02/05/food_7jQZII6.png");
		addItem(increaseIdincreased(), "Onion Rings", (float) 2.99, "menu-1",
			"Butter chicken is traditionally cooked in a tandoor; but may be grilled, roasted or pan-fried in less authentic preparations. The gravy is made by first heating fresh tomato, garlic, and cardamom into a bright red pulp, which is then pureed after cooling, then the chef adds butter, Khoa and various spices, often including asafoetida, cumin, cloves, cinnamon. ",
			"https://thumb.chocofood.kz/srVT0TNF2SBHYV-z566FPabj-Fc=/fit-in/400x0/https://chocofood.kz/media/site/restaurants/food/logo/2019/05/14/food_a5TLYaU.png");
		addItem(increaseIdincreased(), "Fries", (float) 1.99, "menu-1",
			"Its characteristic brilliant red color traditionally comes from liberal amounts of dried Kashmiri chillies that have been de-seeded to reduce their heat: these chillies are considerably milder than the typical dried cayenne chillies of Indian cuisine. The recipe’s spiciness is one of fragrance rather than heat, and the dish is mild enough to be.  ",
			"https://thumb.chocofood.kz/J1YGSDPCTtJNmA7iQCADLSU5fqM=/fit-in/400x0/https://chocofood.kz/media/site/restaurants/food/logo/2019/05/14/food_FQJ4MvW.png");
		addItem(increaseIdincreased(), "Sweet Tea", (float) 1.79, "menu-1",
			"Sometimes called the greatest chef who ever lived, Auguste Escoffier created a dessert of poached peach halves, vanilla ice cream, and raspberry sauce in honor of Australian opera singer Dame Nellie Melba. A Frenchman, Escoffier worked at the Ritz Hotel in London in the early 1900s, the period when Melba performed regularly at the Covent Garden opera.  ",
			"https://thumb.chocofood.kz/w5hfwbQxlNNcLbMX2xSyqhFWVZ8=/fit-in/400x0/https://chocofood.kz/media/site/restaurants/food/logo/2019/05/14/food_qotfjyM.png");
		addItem(increaseIdincreased(), "bread", (float) 1.99, "menu-1",
			"Belgium is home to culinary favorites like Belgian chocolate and beer — but no dish is more iconic than the Belgian waffle.\n" +
				"While in North America, the Belgian waffle is often massive and drowning in syrup, the traditional version is actually a small street food. Eat this treat like it's meant to be enjoyed — sans forks, either plain or with a small  of whipped.",
			"https://thumb.chocofood.kz/n2iWNd6obT6c7ann6b0xH8ChrQM=/fit-in/400x0/https://chocofood.kz/media/site/restaurants/food/logo/2019/05/14/food_rrTWfOM.png");
		addItem(increaseIdincreased(), "Grilled Cheese Sandwich", (float) 2.50, "menu-3",
			"Its characteristic brilliant red color traditionally comes from liberal amounts of dried Kashmiri chillies that have been de-seeded to reduce their heat: these chillies are considerably milder than the typical dried cayenne chillies of Indian cuisine. The recipe’s spiciness is one of fragrance rather than heat, and the dish is mild  to be  by. ",
			"https://thumb.chocofood.kz/	uR4OGHD5KyA_2RozP3j7bTrNPEM=/fit-in/400x0/https://chocofood.kz/media/site/restaurants/food/logo/2019/02/05/food_hJNIjZn.png");
		addItem(increaseIdincreased(), "Grilled Cheese Sandwich w/Onion", (float) 4.99, "menu-3",
			"Tandoori chicken a popular North Indian dish consisting of roasted chicken prepared with yogurt and spices. The name comes from the type of cylindrical clay oven, a tandoor,The chicken is marinated in yogurt and seasoned with the spice mixture tandoori masala. Cayenne pepper, red chili powder or Kashmiri red   is. ",
			"https://thumb.chocofood.kz/07XcWcaC46Ius9HsAKk4bJrK2_0=/fit-in/400x0/https://chocofood.kz/media/site/restaurants/food/logo/2019/02/05/food_M70BUF6.png");
		addItem(increaseIdincreased(), "Sweet Potato Fries", (float) 2.49, "menu-3",
			"Rogan josh (or roghan josh) is a staple of Kashmiri cuisine: originally it was brought to Kashmir by the Mughals. It is one of the main dishes of the Kashmiri multi-course meal (the “Wazwan”). It consists of braised lamb chunks cooked with a gravy based on browned onions or shallots, yogurt, garlic, ginger and aromatic spices (cloves, bay leaves. ).",
			"https://thumb.chocofood.kz/kOEeGoVCiKXRKeV2SnDfLzxigfs=/fit-in/400x0/https://chocofood.kz/media/site/restaurants/food/logo/2019/02/05/food_pctni7F.png");
		addItem(increaseIdincreased(), "Bottle Water", (float) 1.00, "menu-3",
			"Sometimes called the greatest chef who ever lived, Auguste Escoffier created a dessert of poached peach halves, vanilla ice cream, and raspberry sauce in honor of Australian opera singer Dame Nellie Melba. A Frenchman, Escoffier worked at the Ritz Hotel in London in the early 1900s, the period when Melba performed regularly at the Covent Garden opera.  ",
			"https://thumb.chocofood.kz/nFsSwAQMIO7f_1KC__JKs2QFNPM=/fit-in/400x0/https://chocofood.kz/media/site/restaurants/food/logo/2019/02/05/food_DdKh09K.png");
		addItem(increaseIdincreased(), "sandwich", (float) 2.99, "menu-3",
			"Belgium is home to culinary favorites like Belgian chocolate and beer — but no dish is more iconic than the Belgian waffle.\n" +
				"While in North America, the Belgian waffle is often massive and drowning in syrup, the traditional version is actually a small street food. Eat this treat like it's meant to be enjoyed — sans forks,   or with a  helping of whipped cream.",
			"https://thumb.chocofood.kz/5neIfrVXbXy8h3ME3SGRKJJeuhE=/fit-in/400x0/https://chocofood.kz/media/site/restaurants/food/logo/2019/02/05/food_2B0g60e.png");
		addItem(increaseIdincreased(), "fish", (float) 1.00, "menu-3",
			"Common in many Central and Eastern European countries, think of these as a slightly thicker and fluffier version of crepes – oh my god – they are seriously delicious. Traditionally served with apricot jam and whipped cream but today mostly enjoyed with Nutella (duh), these are ah-mazing! Head to Austria to get your fix, this is for sure one of the foods  must eat in.",
			"https://thumb.chocofood.kz/s_wiK1_2_wIkj9fx74AvfVXVymM=/fit-in/400x0/https://chocofood.kz/media/site/restaurants/food/logo/2019/02/05/food_Z2yEPEY.png");

	}

	private void loadCustomer() {

		addCustomer(String.valueOf(takeDefaultIdIncreased()), "Chingis");
		addCustomer(String.valueOf(increaseIdincreased()), "Nazar");
		addCustomer(String.valueOf(increaseIdincreased()), "Arman");
		addCustomer(String.valueOf(increaseIdincreased()), "Leha");
		addCustomer(String.valueOf(increaseIdincreased()), "Nazarbaev");
		addCustomer(String.valueOf(increaseIdincreased()), "Putin");
		addCustomer(String.valueOf(increaseIdincreased()), "Sanzhar");
		addCustomer(String.valueOf(increaseIdincreased()), "Baktugul");

	}
}
