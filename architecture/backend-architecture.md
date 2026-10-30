\# Backend Architecture



\## Overview



The independent reference backend uses a layered Spring Boot architecture with MySQL persistence.



\## Request Flow



```mermaid

flowchart TD

&#x20;   A\[Client / REST Request] --> B\[ContactInquiryController]

&#x20;   B --> C\[ContactInquiryRequest]

&#x20;   C --> D{Bean Validation}

&#x20;   D -->|Invalid| E\[GlobalExceptionHandler]

&#x20;   E --> F\[HTTP 400 Response]

&#x20;   D -->|Valid| G\[ContactInquiryService]

&#x20;   G --> H\[ContactInquiryRepository]

&#x20;   H --> I\[ContactInquiry Entity]

&#x20;   I --> J\[(MySQL Database)]

&#x20;   J --> K\[HTTP 201 Response]

```



\## Component Responsibilities



| Component | Responsibility |

|---|---|

| Controller | Receives HTTP requests and returns API responses |

| DTO | Defines request fields and validation constraints |

| Service | Applies business logic and prepares inquiry data |

| Repository | Performs database operations through Spring Data JPA |

| Entity | Maps Java objects to the MySQL table |

| Exception Handler | Produces consistent error responses |

| MySQL | Stores submitted inquiries |



\## Design Characteristics



\- Separation of concerns through application layers.

\- Server-side request validation.

\- Transactional inquiry persistence.

\- Centralized exception handling.

\- Database credentials supplied through environment variables.



\## Scope



This diagram describes only the independent reference implementation in this repository. It does not represent verified internal Cynbit architecture.

