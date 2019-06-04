package kz.greetgo.diploma.register.dao;

import kz.greetgo.diploma.controller.model.UserCan;
import kz.greetgo.diploma.controller.register.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AdminDao {

	@Insert("insert into item(name, price, category, description) " +
		"values (#{item.name}, #{item.price}, #{item.image}, #{item.description})")
	void insertItem(@Param("item") Item item);

	@Select("select item_id as itemId, name as name, price as price from item where name = #{item.name}")
	List<Item> checkItem(@Param("item") Item item);

	@Insert("insert into food_schedule(id, data, food_id) " +
		"values (#{foodSchedule.id}, #{foodSchedule.data}, #{foodSchedule.foodId})")
	void insertFoodSchedule(@Param("foodSchedule") FoodSchedule foodSchedule);

	@Select("select id, data , food_id as foodId from food_schedule where food_id = #{foodSchedule.foodId}||'' and data = #{foodSchedule.data}")
	List<FoodSchedule> checkFoodSchedule(@Param("foodSchedule") FoodSchedule foodSchedule);

	@Select("select * from food_type")
	List<FoodType> selectFoodItem();

	@Select("select * from food_list where type::integer = #{id}")
	List<FoodList> selectFoodList(Integer id);

	@Select("select * from restaurant_table")
	List<Table> getTableList();


	@Select("select * from restaurant_table where id= #{table.id} and  name =#{table.name}")
	List<Table> selectRestaurantTable(@Param("table") Table table);

	@Insert("insert into restaurant_table(name, status) " +
		"values (#{table.name}, #{table.status})")
	void insertRestaurantTable(@Param("table") Table table);

	@Update("update restaurant_table set status = #{table.status} where id = #{table.id} and name = #{table.name}")
	void updateRestaurantTable(@Param("table") Table table);

	@Select("select * from user_can")
	List<UserCans> loadCans();

	@Select("select user_can from person_cans where person_id = #{personId}")
	List<UserCan> loadCansInId(String personId);


	@Select("select surname||' '||name||' '||patronymic as fio, username, id" +
		" from person")
	List<PersonDisplays> loadDisplayPerson();


	@Insert("insert into Person (id, username, surname, name, patronymic, encoded_password, blocked) " +
		"values (#{id}, #{username}, #{surname}, #{name},  #{patronymic},#{encodedPassword}, 0)")
	void insertPerson(@Param("id") String id,
										@Param("username") String username,
										@Param("surname") String surname,
										@Param("name") String name,
										@Param("patronymic") String patronymic,
										@Param("encodedPassword") String encodedPassword
	);

	@Update("update Person set ${fieldName} = #{fieldValue} where id = #{id}")
	void updatePersonField(@Param("id") String id,
												 @Param("fieldName") String fieldName,
												 @Param("fieldValue") Object fieldValue);

	@Insert("insert into person_cans (person_id, user_can) values (#{id}, #{userCan})")
	void inserPersonCans(@Param("id") String id,
											 @Param("userCan") UserCan userCan);

	@Delete("delete from person_cans where person_id = #{id}")
	void deletePersonCanas(@Param("id") String id);


}
