package kz.greetgo.diploma.controller.register.model;

public class Table {
	public Integer id;
	public String name;
	public Integer status;
	public Integer personNumber;


	@Override
	public String toString() {

		return "Table{" +
			"id=" + id +
			", name='" + name + '\'' +
			", status=" + status +
			", personNumber=" + personNumber +
			'}';
	}
}
