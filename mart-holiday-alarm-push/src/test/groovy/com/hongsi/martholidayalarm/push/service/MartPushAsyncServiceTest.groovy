package com.hongsi.martholidayalarm.push.service

import com.hongsi.martholidayalarm.clients.firebase.message.FirebaseMessageSender
import com.hongsi.martholidayalarm.core.holiday.Holiday
import com.hongsi.martholidayalarm.core.mart.MartType
import com.hongsi.martholidayalarm.push.model.PushMart
import com.hongsi.martholidayalarm.push.model.PushUser
import com.hongsi.martholidayalarm.push.repository.PushMartRepository
import com.hongsi.martholidayalarm.push.repository.PushUserRepository
import spock.lang.Specification
import spock.lang.Unroll

class MartPushAsyncServiceTest extends Specification {

    private PushMartRepository pushMartRepository
    private PushUserRepository pushUserRepository
    private FirebaseMessageSender sender

    private MartPushAsyncService martPushService

    void setup() {
        pushMartRepository = Mock()
        pushUserRepository = Mock()
        sender = Mock()
        martPushService = new MartPushAsyncService(pushMartRepository, pushUserRepository, sender)
    }

    @Unroll
    def "사용자가 즐겨찾기한 마트에게 푸시를 보낼 수 있다"() {
        given:
        def pushUser = new PushUser()
        pushUser.setDeviceToken("deviceToken")
        pushUser.setFavorites(favorites)
        1 * pushMartRepository.findAllByIdInAndHolidayDate(pushUser.getFavorites(), _) >> pushMarts

        when:
        martPushService.push(pushUser)

        then:
        pushMarts.size() * sender.send(_)

        where:
        favorites || pushMarts
        [1, 2, 3] || [new PushMart(1, MartType.EMART, "성수점", Holiday.of(2020, 5, 31)), new PushMart(2, MartType.LOTTEMART, "구로점", Holiday.of(2020, 5, 31))]
        [1, 2, 3] || []
        []        || []
    }
}
