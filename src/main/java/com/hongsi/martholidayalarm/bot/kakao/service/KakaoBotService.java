package com.hongsi.martholidayalarm.bot.kakao.service;

import com.google.common.collect.Lists;
import com.hongsi.martholidayalarm.bot.kakao.domain.BotResponse;
import com.hongsi.martholidayalarm.bot.kakao.domain.Button;
import com.hongsi.martholidayalarm.bot.kakao.domain.Keyboard;
import com.hongsi.martholidayalarm.bot.kakao.domain.Message;
import com.hongsi.martholidayalarm.bot.kakao.domain.MessageButton;
import com.hongsi.martholidayalarm.bot.kakao.domain.UserRequest;
import com.hongsi.martholidayalarm.bot.kakao.repository.KakaoBotRepository;
import com.hongsi.martholidayalarm.mart.domain.Mart;
import com.hongsi.martholidayalarm.mart.domain.MartType;
import com.hongsi.martholidayalarm.mart.service.MartService;
import java.util.List;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class KakaoBotService {

	private MartService martService;
	private KakaoBotRepository kakaoBotRepository;

	@Transactional
	public BotResponse parse(UserRequest userRequest) {
		try {
			UserRequest beforeRequest = kakaoBotRepository.findByUserKey(userRequest.getUserKey());
			if (userRequest.isSame(beforeRequest)) {
				throw new IllegalStateException("중복된 요청입니다.");
			}
			userRequest.readyToUpdate(beforeRequest);
			kakaoBotRepository.save(userRequest);
		} catch (Exception e) {
			return reset(userRequest.getUserKey());
		}
		return new BotResponse(getMessageForResponse(userRequest),
				getKeyboardForResponse(userRequest));
	}

	private Message getMessageForResponse(UserRequest userRequest) {
		Button selectedButton = userRequest.getButton();
		if (selectedButton == Button.BRANCH) {
			String branchName = userRequest.getSplitedPath()[selectedButton.getOrder()];
			Mart mart = martService.getMart(branchName);
			MessageButton messageButton = new MessageButton(mart);
			return new Message(Message.makeBranchInfo(mart), messageButton);
		}
		return new Message(selectedButton.getMessage());
	}

	private Keyboard getKeyboardForResponse(UserRequest userRequest) {
		return new Keyboard(getButtons(userRequest));
	}

	private List<String> getButtons(UserRequest userRequest) {
		try {
			String[] path = userRequest.getSplitedPath();
			int buttonOrder = Button.MARTTYPE.getOrder();
			switch (userRequest.getButton()) {
				case MARTTYPE:
					return martService.getRegions(MartType.of(path[buttonOrder]));
				case REGION:
					return martService.getBranches(MartType.of(path[buttonOrder]),
							path[Button.MARTTYPE.REGION.getOrder()]);
				case BRANCH:
					return Keyboard.getDefaultKeyboardToList();
			}
		} catch (NoSuchFieldException e) {
			log.error("Can not create response : " + e.getMessage());
		}
		return Lists.transform(martService.getMartTypes(), MartType::getName);
	}

	private BotResponse reset(String userKey) {
		kakaoBotRepository.deleteByUserKey(userKey);
		Message wrongMessage = new Message("잘못된 요청입니다 다시 선택해주세요");
		Keyboard defaultKeyboard = new Keyboard(Keyboard.DEFAULT_KEYBOARD);
		return new BotResponse(wrongMessage, defaultKeyboard);
	}

	public void deleteUserRequest(String userKey) {
		kakaoBotRepository.deleteByUserKey(userKey);
	}
}
