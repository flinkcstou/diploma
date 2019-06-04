package kz.greetgo.diploma.register.test.beans.develop;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.diploma.register.beans.all.IdGenerator;
import kz.greetgo.diploma.register.test.dao.AuthTestDao;
import kz.greetgo.diploma.register.test.dao.TelegramMenuTestDao;
import kz.greetgo.security.password.PasswordEncoder;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Bean
public class DbTelegramMenuLoader {

	final Logger logger = Logger.getLogger(getClass());


	public BeanGetter<AuthTestDao> authTestDao;

	public BeanGetter<IdGenerator> idGenerator;

	public BeanGetter<PasswordEncoder> passwordEncoder;

	public BeanGetter<TelegramMenuTestDao> telegramMenuTestDao;

	private Integer idIncreased = 0;

	public void loadTestData() throws Exception {

		loadTelegramMenu();
		logger.info("FINISH");

	}

	private void loadTelegramMenu() throws Exception {

		logger.info("Start loading telegram menu...");
		loadFoodType();
		loadFoodList();
		loadFoodSchedule();
	}

	private void addFoodType(String id, String name) {

		name = name.toLowerCase();
		String description = "undefined";

		telegramMenuTestDao.get().insertFoodType(id, name, description);
	}

	private void addFoodType(String id, String name, String description) {

		name = name.toLowerCase();
		description = description.toLowerCase();

		telegramMenuTestDao.get().insertFoodType(id, name, description);
	}

	private void addFoodList(String id, String name, String type, String price) {

		name = name.toLowerCase();
		telegramMenuTestDao.get().insertFoodList(id, name, type,price);
	}

	private void addFoodSchedule(String data, String foodId) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(data);
		String id = idGenerator.get().newId();
		telegramMenuTestDao.get().insertFoodSchedule(id, new Timestamp(date.getTime()), foodId);
	}


	private String increaseIdincreased() {

		++idIncreased;
		return Integer.toString(idIncreased);
	}

	private String setidIncreased(Integer id) {

		if(id < 2)
			{
				id = 1;
			}
		idIncreased = id;
		return Integer.toString(idIncreased);
	}

	private String takeDefaultIdIncreased() {

		idIncreased = 1;
		return Integer.toString(idIncreased);
	}

	private void loadFoodType() {

		addFoodType(takeDefaultIdIncreased(), "Меню-1", "первое меню"); // id 1
		addFoodType(increaseIdincreased(), "Меню-2", "второе меню"); // id 2
		addFoodType(increaseIdincreased(), "Десерты", "список десертов"); // // id 3
		addFoodType(increaseIdincreased(), "Чаи", "списов чаев"); // id 4
		addFoodType(increaseIdincreased(), "Кальяны", "список кальянов"); // id 5
	}

	private void loadFoodList() {

		addFoodList(takeDefaultIdIncreased(), "Борщ", "1","500 tenge");
		addFoodList(increaseIdincreased(), "Ботамоти", "1","2000 tenge");
		addFoodList(increaseIdincreased(), "Ботвинья", "1","500 tenge");
		addFoodList(increaseIdincreased(), "Боттарга", "1","2050 tenge");
		addFoodList(increaseIdincreased(), "Боярышниковые хлопья", "1","500 tenge");
		addFoodList(increaseIdincreased(), "Аммицу", "1","500 tenge");
		addFoodList(increaseIdincreased(), "Зельц", "1","950 tenge");
		addFoodList(increaseIdincreased(), "Гренки", "1","500 tenge");
		addFoodList(increaseIdincreased(), "Свинина с овощами, тушеная в сливках", "2","500 tenge");
		addFoodList(increaseIdincreased(), "Картофель, запеченный в мундире", "2","750 tenge");
		addFoodList(increaseIdincreased(), "Макароны, запеченные с помидорами, под сыром", "2","500 tenge");
		addFoodList(increaseIdincreased(), "Азу по‑татарски", "2","500 tenge");
		addFoodList(increaseIdincreased(), "Крылышки чили", "2","500 tenge");
		addFoodList(increaseIdincreased(), "Грудка куриная гриль на шпажках", "2","500 tenge");
		addFoodList(increaseIdincreased(), "Фрикадельки в сметанном соусе", "2","500 tenge");
		addFoodList(increaseIdincreased(), "Брокколи отварная", "2","500 tenge");
		addFoodList(increaseIdincreased(), "Индийские сладости", "3","500 tenge");
		addFoodList(increaseIdincreased(), "Рождественский пудинг", "3","1500 tenge");
		addFoodList(increaseIdincreased(), "Дульсе-де-лече", "3","500 tenge");
		addFoodList(increaseIdincreased(), "Болу рей (Португалия)", "3","500 tenge");
		addFoodList(increaseIdincreased(), "Болу рей (Португалия)", "4","500 tenge");
		addFoodList(increaseIdincreased(), "Айва Манго", "4","500 tenge");
		addFoodList(increaseIdincreased(), "Красный чай", "4","500 tenge");
		addFoodList(increaseIdincreased(), "Черный чай", "4","500 tenge");
		addFoodList(increaseIdincreased(), "Чай с молоком", "4","500 tenge");
		addFoodList(increaseIdincreased(), "Клюква мята", "4","2000 tenge");
		addFoodList(increaseIdincreased(), "Медовый чай", "4","500 tenge");
		addFoodList(increaseIdincreased(), "Облепиховый чай", "4","500 tenge");
		addFoodList(increaseIdincreased(), "Массиль или обычный кальянный табак", "5","5500 tenge");
		addFoodList(increaseIdincreased(), "Томбак", "5","2000 tenge");
		addFoodList(increaseIdincreased(), "Доха", "5","4500 tenge");
		addFoodList(increaseIdincreased(), "Layalina", "5","2000 tenge");
		addFoodList(increaseIdincreased(), "Al Fakher", "5","3500 tenge");
		addFoodList(increaseIdincreased(), "Nakhla", "5","2500 tenge");
		addFoodList(increaseIdincreased(), "Starbuzz", "5","2600 tenge");
	}

	private void loadFoodSchedule() throws ParseException {

		addFoodSchedule("2019-04-01", takeDefaultIdIncreased());


		for(int loopDay = 0; loopDay < 15; loopDay++)
			{
				for(int i = 0; i < 15; i++)
					{

						try
							{
								String day = "";
								int twoLength = Integer.toString(loopDay + 2).length();
								if(twoLength < 2)
									{
										day = "0" + (loopDay + 2);
									} else
									{
										day = "" + (loopDay + 2);
									}
								String fullDate = "2019-06-" + day;

								Integer rand = (int) (1 + Math.random() * 33);
								String randFoodId = Integer.toString(rand);

								addFoodSchedule(fullDate, randFoodId);
							} catch(Exception e)
							{
								//Нужно для того чтоб если рандомное число повторилась, он не добавит в бд, и выведет ошибку,
								// и тогда мы заново цикл начинаем чтоб рандомное число не повторилась так как  ключ базы data и food_id есть primary key
								i--;
							}
					}
			}


	}

}
