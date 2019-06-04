package kz.greetgo.diploma.controller.register.model;

public class CommentsLike {

	public Integer id;

	public Integer commentsId;

	public String personId;

	public Integer liked;

	public Integer disliked;

	@Override
	public String toString() {

		return "CommentsLike{" +
			"id=" + id +
			", commentsId=" + commentsId +
			", personId='" + personId + '\'' +
			", liked=" + liked +
			", disliked=" + disliked +
			'}';
	}
}
