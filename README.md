\# Cynbit Backend Technical Audit \& Reference Implementation



An independent backend engineering assessment of the publicly observable Cynbit contact page, accompanied by a Java Spring Boot and MySQL reference implementation.



\## 1. Project Overview



This project contains two deliverables:



1\. \*\*Technical Audit:\*\* Passive analysis of publicly accessible website behaviour, frontend technology indicators, contact form fields, and submission flow.

2\. \*\*Independent Backend:\*\* A working Spring Boot REST API demonstrating inquiry validation, database persistence, and structured error handling.



\- Website: https://www.cynbit.in/

\- Inspected page: https://www.cynbit.in/contact

\- Assessment date: October 30, 2026.



\## 2. Scope and Limitations



The audit is based on publicly observable website resources and browser behaviour.



The actual Cynbit backend source code, database, internal infrastructure, and server-side implementation were not inspected.



The Spring Boot application in this repository is an independent reference implementation. It is not Cynbit's original backend and does not claim to reproduce its internal architecture.



No intrusive production testing or unauthorized access was performed.



\## 3. Key Website Observations



The public contact page inspection identified:



\- Next.js frontend indicators.

\- React Server Components response (`text/x-component`).

\- Vercel hosting response headers.

\- HTTPS and HTTP Strict Transport Security.

\- A contact form containing five fields.

\- A Next.js Server Action named `submitInquiry`.

\- Client-side loading, success, and error states.



A potential client-side resilience improvement was identified: the inspected submission handler did not visibly use `try/catch/finally` to guarantee loading-state cleanup after a rejected action.



See \[the detailed technical audit](audit-report/01-website-analysis.md).



\## 4. Technology Stack



| Technology | Purpose |

|---|---|

| Java 25 | Backend language |

| Spring Boot 4.1.2 | Application framework |

| Spring WebMVC | REST API |

| Spring Data JPA | Data persistence |

| Hibernate | ORM |

| MySQL 8.0 | Relational database |

| Jakarta Bean Validation | Request validation |

| Spring Boot Actuator | Application health |

| Maven Wrapper | Build and dependency management |



\## 5. Backend Architecture



The application follows a layered architecture:



```text

Client

&#x20; |

&#x20; v

ContactInquiryController

&#x20; |

&#x20; v

ContactInquiryRequest (Validation)

&#x20; |

&#x20; v

ContactInquiryService

&#x20; |

&#x20; v

ContactInquiryRepository

&#x20; |

&#x20; v

ContactInquiry Entity

&#x20; |

&#x20; v

MySQL Database

```



`GlobalExceptionHandler` provides centralized validation and unexpected-error responses.



\### Source structure



```text

backend/backend/

├── src/

│   ├── main/

│   │   ├── java/in/cynbit/audit/backend/

│   │   │   ├── BackendApplication.java

│   │   │   ├── controller/

│   │   │   ├── dto/

│   │   │   ├── entity/

│   │   │   ├── exception/

│   │   │   ├── repository/

│   │   │   └── service/

│   │   └── resources/

│   │       └── application.properties

│   └── test/

│       └── java/in/cynbit/audit/backend/

├── pom.xml

├── mvnw

└── mvnw.cmd

```



\## 6. REST API Documentation



\### Submit an inquiry



`POST /api/v1/inquiries`



Content-Type: `application/json`



Request:



```json

{

&#x20; "fullName": "Test User",

&#x20; "email": "test@example.com",

&#x20; "company": "Example Ltd",

&#x20; "industry": "Technology",

&#x20; "message": "I would like to know more about your services."

}

```



Successful response: HTTP 201 Created



```json

{

&#x20; "success": true,

&#x20; "message": "Inquiry received successfully",

&#x20; "inquiryId": 1

}

```



Invalid requests return HTTP 400 with field-level validation errors.



Unexpected server errors return HTTP 500 with a generic error message.



\### Health endpoint



`GET /actuator/health`



Returns application health status without exposing detailed component information.



\## 7. Database Configuration



Create a MySQL database and dedicated application user:



```sql

CREATE DATABASE cynbit\_audit;



CREATE USER 'cynbit\_app'@'localhost'

IDENTIFIED BY 'REPLACE\_WITH\_YOUR\_OWN\_PASSWORD';



GRANT ALL PRIVILEGES ON cynbit\_audit.\*

TO 'cynbit\_app'@'localhost';



FLUSH PRIVILEGES;

```



The application reads its database password from the `DB\_PASSWORD` environment variable. Never commit database credentials to Git.



\## 8. Run Locally



Requirements:



\- JDK 25

\- MySQL 8.0

\- Windows PowerShell or a compatible terminal



Navigate to the backend project:



```powershell

cd backend\\backend

```



Set the database password securely in PowerShell:



```powershell

$secure = Read-Host "Enter MySQL password" -AsSecureString

$ptr = \[Runtime.InteropServices.Marshal]::SecureStringToBSTR($secure)

try {

&#x20;   $env:DB\_PASSWORD = \[Runtime.InteropServices.Marshal]::PtrToStringBSTR($ptr)

}

finally {

&#x20;   \[Runtime.InteropServices.Marshal]::ZeroFreeBSTR($ptr)

}

```



Run the application:



```powershell

.\\mvnw.cmd spring-boot:run

```



Application URL: `http://localhost:8080`



\## 9. Run Tests



From the backend project directory:



```powershell

.\\mvnw.cmd test

```



Verified automated test results:



\- Spring application context test: Passed.

\- Invalid inquiry validation test: Passed.

\- Valid inquiry validation test: Passed.



\*\*Latest recorded result: 3 tests passed, 0 failures, 0 errors.\*\*



Manual verification also confirmed successful API submission, MySQL persistence, and rejection of invalid requests.



\## 10. Security and Engineering Considerations



Implemented:



\- Server-side request validation.

\- Required-field and email validation.

\- Maximum field lengths.

\- Email normalization.

\- Persistence through Spring Data JPA.

\- Centralized exception handling.

\- Database credentials supplied through environment variables.

\- Restricted Actuator health details.

\- Hibernate schema validation.

\- SQL logging disabled.



Further production improvements:



\- Rate limiting and spam prevention.

\- Database migration management.

\- Structured logging and monitoring.

\- Request-size restrictions.

\- Data retention and privacy controls.

\- Authentication and authorization for future administrative inquiry-management endpoints.



These are recommendations, not claims that Cynbit currently lacks these controls.



\## 11. Repository Contents



| Directory | Description |

|---|---|

| `audit-report/` | Website technical analysis |

| `backend/` | Independent Spring Boot implementation |

| `database/` | Database documentation and scripts |

| `architecture/` | Architecture documentation |

| `screenshots/` | Assessment evidence |



\## 12. Final Outcome



This project demonstrates the ability to inspect public web application behaviour, document evidence and limitations, design a Java REST backend, implement validation and persistence, and verify the implementation through automated and manual testing.



The audit findings and reference implementation are intentionally kept separate to maintain technical accuracy.



\---



\*\*Assessment:\*\* Cynbit Backend Developer Intern  

\*\*Project type:\*\* Technical Audit + Independent Backend Reference Implementation

