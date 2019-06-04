package kz.greetgo.diploma.register.test.beans.develop;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.diploma.controller.model.UserCan;
import kz.greetgo.diploma.register.beans.all.IdGenerator;
import kz.greetgo.diploma.register.test.dao.AuthTestDao;
import kz.greetgo.security.password.PasswordEncoder;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Bean
public class DbLoader {
  final Logger logger = Logger.getLogger(getClass());


  public BeanGetter<AuthTestDao> authTestDao;
  public BeanGetter<IdGenerator> idGenerator;
  public BeanGetter<PasswordEncoder> passwordEncoder;
  public BeanGetter<DbTelegramMenuLoader> dbTelegramMenu;
  public BeanGetter<DbRestaurantOrderLoader> dbRestaurantOrder;
  public BeanGetter<DbBookingLoader> dbBookingLoader;

  public void loadTestData() throws Exception {

    loadPersons();

    logger.info("FINISH");
    dbTelegramMenu.get().loadTestData();
    dbRestaurantOrder.get().loadTestData();
    dbBookingLoader.get().loadTestData();
  }

  @SuppressWarnings("SpellCheckingInspection")
  private void loadPersons() throws Exception {
    logger.info("Start loading persons...");

    user("Пушкин Александр Сергеевич", "1799-06-06", "87077622986");
    user("Сталин Иосиф Виссарионович", "1878-12-18", "87077622980");
    user("Берия Лаврентий Павлович", "1899-03-17", "87077622981");
    user("Есенин Сергей Александрович", "1895-09-21", "87077622982");
    user("Путин Владимир Владимирович", "1952-10-07", "87077622983");
    user("Назарбаев Нурсултан Абишевич", "1940-07-06", "87077622984");
    user("Менделеев Дмитрий Иванович", "1834-02-08", "87077622985");
    user("Ломоносов Михаил Васильевич", "1711-11-19", "87077622987");
    user("Бутлеров Александр Михайлович", "1828-09-15", "87077622988");
    user("Акмарал Мелдехан ...", "1998-06-14", "87077622989");
    user("Улжалгас Калтурсун Мараткызы", "1998-06-14", "8707762210");

    add_can("8707762210", UserCan.VIEW_USERS);
    add_can("87077622989", UserCan.VIEW_USERS);
    add_can("87077622981", UserCan.VIEW_STAFF);
    add_can("87077622988", UserCan.VIEW_ABOUT);
    add_can("87077622986", UserCan.VIEW_ABOUT);
    add_can("87077622986", UserCan.VIEW_ADMIN);

    logger.info("Finish loading persons");
  }

  private void user(String fioStr, String birthDateStr, String accountName) throws Exception {
    String id = idGenerator.get().newId();
    String[] fio = fioStr.split("\\s+");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date birthDate = sdf.parse(birthDateStr);
    String encryptPassword = passwordEncoder.get().encode("111");

    authTestDao.get().insertPerson(id, accountName, encryptPassword);
    authTestDao.get().updatePersonField(id, "birth_date", new Timestamp(birthDate.getTime()));
    authTestDao.get().updatePersonField(id, "surname", fio[0]);
    authTestDao.get().updatePersonField(id, "name", fio[1]);
    authTestDao.get().updatePersonField(id, "patronymic", fio[2]);
  }

  private void add_can(String username, UserCan... cans) {
    for (UserCan can : cans) {
      authTestDao.get().upsert(can.name());
      authTestDao.get().personCan(username, can.name());
    }
  }
}
