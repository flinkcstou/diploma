package kz.greetgo.diploma.register.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

public interface NotificationDao {

  @Insert("insert into device_notification(person, registration_id)\n " +
    " values (#{personId}, #{registrationId})" +
    " on conflict (registration_id) do update set" +
    " person = excluded.person\n")
  Long registerDevice(@Param("personId") String personId, @Param("registrationId") String registrationId);

  @Delete("delete from device_notification where registration_id=#{registrationId};")
  void unregisterDevice(@Param("registrationId") String registrationId);


  @Select("select registration_id from device_notification where person=#{personId}")
  List<String> getFcmRegIdByPersonId(@Param("personId") String personId);

  @Select("select registration_id from device_notification where person=#{personId}")
  Set<String> getRegisterTokensByParentId(@Param("personId") String personId);
}
