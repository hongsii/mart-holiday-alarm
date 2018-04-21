package com.hongsi.martholidayalarm.bot.kakao;

import com.hongsi.martholidayalarm.bot.kakao.domain.BotResponse;
import com.hongsi.martholidayalarm.bot.kakao.domain.Keyboard;
import com.hongsi.martholidayalarm.bot.kakao.domain.UserRequestDTO;
import com.hongsi.martholidayalarm.bot.kakao.service.KakaoBotService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class KakaoBotController {

	KakaoBotService kakaoBotService;

	@GetMapping("/keyboard")
	public Keyboard makeDefaultKeyboard() {
		Keyboard keyboard = Keyboard
				.builder()
				.buttons(Keyboard.DEFAULT_KEYBOARD)
				.build();
		return keyboard;
	}

	@PostMapping(value = "/message", produces = MediaType.APPLICATION_JSON_VALUE)
	public BotResponse makeResponse(@RequestBody UserRequestDTO userRequestDTO) {
		BotResponse botResponse = kakaoBotService.parse(userRequestDTO.toEntity());
		return botResponse;
	}
}
