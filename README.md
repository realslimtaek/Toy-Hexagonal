# Hexagonal Architecture Project

이 프로젝트는 헥사고날 아키텍처(Hexagonal Architecture)를 기반으로 작성된 예제 프로젝트입니다.

## 아키텍처 구조 및 레이어

이 프로젝트는 헥사고날 아키텍처의 원칙에 따라 다음과 같이 패키지와 레이어를 구성하였습니다.

### 패키지 구조

```
com.study.hexagonal
├── adapter
│   ├── in
│   │   └── user
│   │       ├── dto
│   │       └── UserController.kt
│   └── out
│       └── user
│           ├── UserEntity.kt
│           ├── UserMapper.kt
│           ├── UserRepository.kt
│           └── UserRepositoryAdaptor.kt
├── application
│   ├── port
│   │   ├── in
│   │   │   └── user
│   │   │       ├── GetUserUseCase.kt
│   │   │       └── SaveUserUseCase.kt
│   │   └── out
│   │       └── user
│   │           ├── GetUserPort.kt
│   │           └── SaveUserPort.kt
│   └── service
│       └── user
│           ├── ResponseMapper.kt
│           └── UserService.kt
├── common
│   └── enums
│       ├── Gender.kt
│       ├── Status.kt
│       └── exception
├── domain
│   └── user
│       └── UserDomain.kt
└── HexagonalApplication.kt
```

- **adapter**: 외부 세계와의 상호작용을 담당합니다.
    - **in**: 외부에서 들어오는 요청을 처리합니다 (예: Controller).
    - **out**: 외부로 나가는 요청이나 영속성 처리를 담당합니다 (예: Repository, External API).
- **application**: 비즈니스 로직을 담당하는 핵심 영역입니다.
    - **port**: 애플리케이션 코어와 어댑터 간의 인터페이스를 정의합니다.
        - **in**: 외부에서 애플리케이션 코어를 호출하기 위한 인터페이스 (UseCase).
        - **out**: 애플리케이션 코어가 외부 리소스를 사용하기 위한 인터페이스 (Port).
    - **service**: `in` 포트(UseCase)를 구현하여 실제 비즈니스 로직을 수행합니다.
- **domain**: 핵심 도메인 모델을 정의합니다.
- **common**: 공통으로 사용되는 유틸리티나 열거형 등을 포함합니다.

## 핵심 도메인 모델 (Rich Domain Model)

이 프로젝트의 핵심 도메인 모델은 `domain` 패키지에 정의되어 있으며, 외부 프레임워크나 라이브러리에 의존하지 않는 순수한 POJO(Plain Old Java Object) 또는 Kotlin Data Class로 작성됩니다.

특히, 데이터만 담고 있는 '빈약한 도메인 모델(Anemic Domain Model)'이 아닌, **데이터와 관련된 비즈니스 행위를 함께 포함하는 '풍부한 도메인 모델(Rich Domain Model)'** 을 지향합니다.

### UserDomain

사용자 정보를 나타내는 도메인 모델입니다.

- **속성**:
    - `id`: 사용자 식별자
    - `name`: 이름
    - `age`: 나이
    - `gender`: 성별 (`Gender` Enum)
    - `status`: 상태 (`Status` Enum)
    - `createdAt`: 생성 일시
    - `updatedAt`: 수정 일시

- **검증 로직 (Invariants)**:
    - 객체 생성 시(`init`) 이름이 비어있거나 나이가 음수인 경우를 원천적으로 차단하여 무결성을 보장합니다.

- **비즈니스 행위**:
    - `rename(newName)`: 이름 변경 로직. 탈퇴한 회원은 이름을 변경할 수 없다는 규칙을 포함합니다.
    - `resign()`: 회원 탈퇴 로직. 이미 탈퇴한 회원은 중복 탈퇴할 수 없다는 규칙을 포함합니다.
    - `isAdult()`: 성인 여부 판단.

### 도메인 로직 vs 서비스 로직 구분 기준

이 프로젝트에서는 로직의 성격에 따라 구현 위치를 명확히 구분합니다.

| 구분 | 설명 | 담당 위치 | 예시 |
| :--- | :--- | :--- | :--- |
| **상태 변경 (State Mutation)** | 데이터의 무결성을 해치지 않으면서 상태를 변경하는 핵심 비즈니스 규칙 | **Domain** (`UserDomain`) | "탈퇴한 회원은 이름을 변경할 수 없다." |
| **표현 변경 (Presentation)** | 데이터 원본은 유지하되, 클라이언트에게 보여주는 형식을 가공하는 로직 | **Service / Mapper** | "이름의 가운데 글자를 마스킹(*) 처리해서 보여준다." |

## 영속성 처리 및 계층 분리 전략

이 프로젝트에서는 도메인 로직과 영속성 기술(JPA)을 철저히 분리하여, 비즈니스 로직이 특정 프레임워크에 종속되지 않도록 설계하였습니다.

### 1. 도메인 모델과 엔티티의 분리

- **도메인 모델 (`UserDomain`)**:
    - `domain` 패키지에 위치하며, JPA 어노테이션이나 데이터베이스 관련 의존성이 전혀 없는 순수한 Kotlin 클래스입니다.
    - 비즈니스 로직의 핵심이 되며, 애플리케이션 내부에서 데이터를 주고받는 기본 단위입니다.

- **영속성 엔티티 (`UserEntity`)**:
    - `adapter.out` 패키지에 위치하며, JPA(`@Entity`, `@Table` 등)를 사용하여 데이터베이스 테이블과 매핑됩니다.
    - 오직 데이터베이스와의 상호작용을 위해서만 사용되며, 비즈니스 로직에는 직접 노출되지 않습니다.

### 2. 의존성 역전 원칙 (DIP) 적용

- **Port (Interface)**:
    - `application.port.out` 패키지에 `GetUserPort`, `SaveUserPort`와 같은 인터페이스를 정의합니다.
    - 서비스 계층(`UserService`)은 이 인터페이스에만 의존하며, 구체적인 구현체(JPA Repository 등)에 대해서는 알지 못합니다.

- **Adapter (Implementation)**:
    - `adapter.out` 패키지의 `UserRepositoryAdaptor`가 위 Port 인터페이스를 구현합니다.
    - 이 어댑터 내부에서 `UserRepository`(JPA Repository)를 주입받아 실제 데이터베이스 작업을 수행합니다.

### 3. 데이터 매핑 (Mapper)

- **UserMapper**:
    - `adapter.out` 패키지에 위치하며, 도메인 모델(`UserDomain`)과 영속성 엔티티(`UserEntity`) 간의 변환을 담당합니다.
    - **toEntity**: 도메인 객체나 DTO를 엔티티로 변환하여 저장 시 사용합니다.
    - **toDomain**: 데이터베이스에서 조회한 엔티티를 도메인 객체로 변환하여 서비스 계층으로 반환합니다.
    - 이를 통해 서비스 계층은 엔티티의 존재를 모르고 도메인 객체만으로 비즈니스 로직을 수행할 수 있습니다.

## API 요청 처리 흐름

API 요청이 들어왔을 때 처리되는 흐름은 다음과 같습니다.

1. **Adapter (In)**:
   - 클라이언트의 요청이 `adapter.in` 패키지의 컨트롤러(예: `UserController`)로 들어옵니다.
   - 컨트롤러는 요청 DTO를 받아 `application.port.in`에 정의된 UseCase 인터페이스를 호출합니다.

2. **Application (Service)**:
   - `application.service` 패키지의 서비스(예: `UserService`)는 UseCase 인터페이스를 구현합니다.
   - 서비스는 비즈니스 로직을 수행하며, 데이터 조회가 필요하거나 외부 시스템과의 통신이 필요한 경우 `application.port.out`에 정의된 Port 인터페이스를 호출합니다.

3. **Adapter (Out)**:
   - `adapter.out` 패키지의 어댑터(예: `UserRepositoryAdaptor`)는 Port 인터페이스를 구현합니다.
   - 이 어댑터는 실제 데이터베이스(Repository)나 외부 시스템과 상호작용하여 데이터를 처리하고, 그 결과를 도메인 객체 등으로 변환하여 반환합니다.

### 예시: 사용자 조회 (`GET /user`)

1. **Request**: `UserController`가 `GET /user` 요청을 받습니다.
2. **UseCase Call**: `UserController`는 `GetUserUseCase.getUsers(status)`를 호출합니다.
3. **Service Logic**: `UserService`는 `GetUserUseCase`를 구현하고 있으며, 내부적으로 `GetUserPort.getUsers(status)`를 호출합니다.
4. **Port Implementation**: `UserRepositoryAdaptor`는 `GetUserPort`를 구현하고 있으며, `UserRepository`를 통해 DB에서 데이터를 조회합니다.
5. **Response**: 조회된 데이터는 도메인 객체로 변환되어 서비스로 반환되고, 서비스는 이를 다시 응답 DTO로 변환하여 컨트롤러를 통해 클라이언트에게 전달됩니다.
