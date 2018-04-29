package com.hongsi.martholidayalarm.bot.kakao.domain;

import com.hongsi.martholidayalarm.mart.domain.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
@Entity
public class UserRequest extends BaseEntity {

	public static final String DEFAULT_SEPARATOR = "_";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String userKey;

	@Column
	private String type;

	@Column
	private String path;

	@Column
	@Enumerated(EnumType.STRING)
	private Button button;

	@Column
	private String content;

	@Builder
	public UserRequest(String userKey, String type, String path, String content, Button button) {
		this.userKey = userKey;
		this.type = type;
		this.path = path;
		this.content = content;
		this.button = button;
		if (button == null) {
			this.button = Button.DEFAULT;
		}
	}

	public void readyToUpdate(UserRequest beforeRequest) {
		path = "";
		if (beforeRequest != null) {
			id = beforeRequest.getId();
			button = beforeRequest.getButton().getNextButton();
			path = beforeRequest.getPath();
		}
		if (button == Button.DEFAULT) {
			path = "";
		}
		path += DEFAULT_SEPARATOR + content;
	}

	public String[] getSplitedPath() {
		return path.split(DEFAULT_SEPARATOR);
	}

	public boolean isSame(UserRequest beforeRequest) {
		if (beforeRequest == null) {
			return false;
		}
		return content.equals(beforeRequest.getContent());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof UserRequest)) {
			return false;
		}

		UserRequest that = (UserRequest) o;

		if (!userKey.equals(that.userKey)) {
			return false;
		}
		if (type != null ? !type.equals(that.type) : that.type != null) {
			return false;
		}
		return content != null ? content.equals(that.content) : that.content == null;
	}

	@Override
	public int hashCode() {
		int result = userKey.hashCode();
		result = 31 * result + (type != null ? type.hashCode() : 0);
		result = 31 * result + (button != null ? button.hashCode() : 0);
		result = 31 * result + (content != null ? content.hashCode() : 0);
		return result;
	}
}
