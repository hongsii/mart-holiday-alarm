package com.hongsi.martholidayalarm.bot.kakao;

import com.hongsi.martholidayalarm.bot.kakao.dto.BotResponse;
import com.hongsi.martholidayalarm.bot.kakao.dto.Keyboard;
import com.hongsi.martholidayalarm.bot.kakao.dto.UserRequestDto;
import com.hongsi.martholidayalarm.bot.kakao.service.KakaoBotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/bot/kakao")
@Api(description = "플러스친구 자동응답 API", tags = {"카카오톡봇"})
public class KakaoBotController {

	private final KakaoBotService kakaoBotService;

	@ApiOperation(value = "대화 시작을 위한 버튼 요청")
	@GetMapping("/keyboard")
	public Keyboard makeDefaultKeyboard() {
		return new Keyboard(Keyboard.DEFAULT_KEYBOARD);
	}

	@ApiOperation(value = "사용자 요청에 해당하는 버튼 생성")
	@PostMapping(value = "/message", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public BotResponse makeResponse(
			@ApiParam(name = "사용자 요청 정보", value = "버튼 클릭시 요청하는 정보", required = true)
			@RequestBody @Valid UserRequestDto userRequestDto) {
		try {
			return kakaoBotService.parse(userRequestDto.toEntity());
		} catch (Exception e) {
			return kakaoBotService.reset(userRequestDto.getUser_key());
		}
	}

	@ApiOperation(value = "사용자 요청 이력 삭제")
	@DeleteMapping(value = {"/chat_room/{user_key}", "/friend/{user_key}"}
			, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> leaveChatRoom(
			@ApiParam(name = "사용자의 고유키", value = "사용자마다 카카오톡에서 부여된 고유키", required = true)
			@PathVariable("user_key") @Valid String userKey) {
		kakaoBotService.deleteUserRequest(userKey);
		return ResponseEntity.ok("SUCCESS");
	}
}
