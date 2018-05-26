package com.hongsi.martholidayalarm.bot.kakao;

import com.hongsi.martholidayalarm.bot.kakao.domain.BotResponse;
import com.hongsi.martholidayalarm.bot.kakao.domain.Keyboard;
import com.hongsi.martholidayalarm.bot.kakao.dto.UserRequestDTO;
import com.hongsi.martholidayalarm.bot.kakao.service.KakaoBotService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class KakaoBotController {

	KakaoBotService kakaoBotService;

	@GetMapping("/keyboard")
	public Keyboard makeDefaultKeyboard() {
		return new Keyboard(Keyboard.DEFAULT_KEYBOARD);
	}

	@PostMapping(value = "/message", produces = MediaType.APPLICATION_JSON_VALUE)
	public BotResponse makeResponse(@RequestBody UserRequestDTO userRequestDTO) {
		return kakaoBotService.parse(userRequestDTO.toEntity());
	}

	@DeleteMapping(value = "/chat_room/{user_key}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> leaveChatRoom(@PathVariable("user_key") String userKey) {
		kakaoBotService.deleteUserRequest(userKey);
		return ResponseEntity.ok("SUCCESS");
	}
}
