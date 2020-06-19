# 마트휴일알리미 ![build](https://github.com/hongsii/mart-holiday-alarm/workflows/build/badge.svg?branch=master)

* 마트쉬는날 - 휴무일 알리미 ([App Store](https://itunes.apple.com/kr/app/%EB%A7%88%ED%8A%B8%EC%89%AC%EB%8A%94%EB%82%A0-%ED%9C%B4%EB%AC%B4%EC%9D%BC-%EC%95%8C%EB%A6%AC%EB%AF%B8/id1438702208?mt=8))

-------------------------

## 개발 환경

* 언어 : Java 8 (openjdk 8)
* 프레임워크 : Spring Boot 2.1.1.RELEASE, JPA, Querydsl 4.2.1
* 데이터베이스 : AWS RDS (MariaDB), Firebase Realtime DB
* 빌드 환경 : JUnit(AssertJ), Lombok, Gradle, Github Actions, AWS CodeDeploy 
* 라이브러리 : Spring Rest Docs, Jsoup 1.10.2, Firebase Messaging

-------------------------

## 소개

마트 정보 및 휴일을 조회할 수 있는 서비스

* 조회 가능한 마트
  * 이마트
  * 이마트 트레이더스
  * 노브랜드
  * 롯데마트
  * 홈플러스
  * 홈플러스 익스프레스
  * 코스트코
  
-------------------------

## 사용법

### 마트쉬는날 - 휴무일 알리미 (iOS앱)

* App Store에서 '마트쉬는날' 검색
* [App Store](https://itunes.apple.com/kr/app/%EB%A7%88%ED%8A%B8%EC%89%AC%EB%8A%94%EB%82%A0-%ED%9C%B4%EB%AC%B4%EC%9D%BC-%EC%95%8C%EB%A6%AC%EB%AF%B8/id1438702208?mt=8) 링크를 통해 다운로드

#### 기능

* 마트를 즐겨찾기해 간편하게 휴일 조회
* 즐겨찾기된 마트를 위젯으로 조회 가능
* 즐겨찾기한 마트의 휴일 전날 오전에 푸시 알림
