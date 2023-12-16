# 프로젝트
사장님이 매장과 정산, 지갑 금액을 관리하는 사장님 사이트

# 준비사항
## 로컬 MySQL 설치
- delivery_store 스키마 생성(덤프 파일 활용)
    - [dump-delivery_store-202312161449.sql](docs%2Fdatabase%2Fdump-delivery_store-202312161449.sql) 

## 결제 메세지 처리
    - 로컬 카프카 설치(결제 메세지용)
    - 카프카 리스터 주석 제거 ([DeliveryPaymentMessageConsumer.kt](src%2Fmain%2Fkotlin%2Fcom%2Ffastcampus%2Fdeliverystoreadmin%2Fconsumer%2Fpayment%2FDeliveryPaymentMessageConsumer.kt))

## 온보딩 기능용
    - 로컬 Redis 설치

# 시스템 구성도
- 사장님 사이트와 상점 데이터베이스
```mermaid
flowchart LR
    A[사장님 사이트] -- 요청 --> B[상점 데이터베이스]
```

# 사용한 기술
- Spring Boot 3.1.5
- Spring Security
- Spring Data JPA
- Thymeleaf
- MySQL 8.0
- Bootstrap 5
- jQuery 3.6.0
- AdminLTE 3.2.0
- Redis
- Apache Kafka

# 접속 화면

## 홈 화면
![home.png](docs%2Fimages%2Fhome.png)

## 대시보드
![dashboard.png](docs%2Fimages%2Fdashboard.png)