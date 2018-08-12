package com.hongsi.martholidayalarm.bot.kakao;

import com.hongsi.martholidayalarm.bot.kakao.dto.BotResponse;
import com.hongsi.martholidayalarm.bot.kakao.dto.Keyboard;
import com.hongsi.martholidayalarm.bot.kakao.dto.UserRequestDto;
import com.hongsi.martholidayalarm.bot.kakao.service.KakaoBotService;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class KakaoBotController {

	private final KakaoBotService kakaoBotService;

	@GetMapping("/keyboard")
	public Keyboard makeDefaultKeyboard() {
		return new Keyboard(Keyboard.DEFAULT_KEYBOARD);
	}

	@PostMapping(value = "/message", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public BotResponse makeResponse(@RequestBody @Valid UserRequestDto userRequestDto) {
		try {
			return kakaoBotService.parse(userRequestDto.toEntity());
		} catch (Exception e) {
			return kakaoBotService.reset(userRequestDto.getUser_key());
		}
	}

	@DeleteMapping(value = {"/chat_room/{user_key}", "/friend/{user_key}"}
			, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> leaveChatRoom(@PathVariable("user_key") String userKey) {
		kakaoBotService.deleteUserRequest(userKey);
		return ResponseEntity.ok("SUCCESS");
	}
}
